

----------------------------------------------------------------------------------------
--     1. Create DB_LINK
----------------------------------------------------------------------------------------

Prompt drop DB Link &&TO_IBP_DB;
DROP DATABASE LINK &&TO_IBP_DB
/

Prompt Database Link &&TO_IBP_DB;


CREATE DATABASE LINK &&TO_IBP_DB
 CONNECT TO &&USER
 IDENTIFIED BY &&PWD
 USING '&&DBNAME'
/




----------------------------------------------------------------------------------------
--     2. Create TRIGGERS ON BSCS_TABLES
----------------------------------------------------------------------------------------

Prompt drop Trigger IBP_CONTR_DEVICES;
DROP TRIGGER IBP_CONTR_DEVICES
/

Prompt Trigger IBP_CONTR_DEVICES;
--
-- IBP_CONTR_DEVICES  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   CONTR_DEVICES (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER IBP_CONTR_DEVICES
AFTER INSERT OR UPDATE
OF CD_ACTIV_DATE
ON CONTR_DEVICES 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE 
    PRAGMA AUTONOMOUS_TRANSACTION;
    PT VARCHAR2(50);
BEGIN  
IF ( :NEW.CD_SEQNO > 1 AND :NEW.CD_ACTIV_DATE IS NOT NULL ) THEN
SELECT CD_PORT_NUM INTO PT FROM CONTR_DEVICES WHERE CO_ID=:NEW.CO_ID AND CD_SEQNO=:NEW.CD_SEQNO-1;
INSERT INTO IBP_STAGING@&&TO_IBP_DB ( REQ_ID, TIMESTAMP,      CO_ID,       SEQ,    OLD_VAL,  NEW_VAL,     ACTION_ID ) 
     VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CO_ID, :NEW.CD_SEQNO, PT, :NEW.CD_PORT_NUM, 8) ;
     COMMIT;
END IF;
EXCEPTION
    WHEN OTHERS THEN
    PT:=0; 
END;
/
SHOW ERRORS;



Prompt drop Trigger IBP_CUSTOMER_ALL;
DROP TRIGGER IBP_CUSTOMER_ALL
/

Prompt Trigger IBP_CUSTOMER_ALL;
--
-- IBP_CUSTOMER_ALL  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   CUSTOMER_ALL (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER IBP_CUSTOMER_ALL 
AFTER UPDATE OF BILLCYCLE ON CUSTOMER_ALL FOR EACH ROW
BEGIN 
INSERT INTO IBP_STAGING@&&TO_IBP_DB ( REQ_ID, TIMESTAMP, CUSTOMER_ID,OLD_VAL,NEW_VAL,ACTION_ID) 
VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CUSTOMER_ID,:OLD.BILLCYCLE, :NEW.BILLCYCLE, 10) ; 
EXCEPTION
    WHEN OTHERS THEN
    NULL;
END;
/
SHOW ERRORS;



Prompt drop Trigger IBP_CONTR_SERVICES;
DROP TRIGGER IBP_CONTR_SERVICES
/

Prompt Trigger IBP_CONTR_SERVICES;
--
-- IBP_CONTR_SERVICES  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   CONTRACT_HISTORY (Table)
--   CONTR_SERVICES (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER IBP_CONTR_SERVICES
AFTER UPDATE
OF CS_STAT_CHNG
  ,CS_SPARAM1
ON CONTR_SERVICES 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
FLG CHAR;
BEGIN
IF (LENGTH(NVL(:OLD.CS_STAT_CHNG, 0)) < LENGTH(NVL(:NEW.CS_STAT_CHNG, 0))) THEN
SELECT MAX('X') INTO FLG FROM CONTRACT_HISTORY WHERE CO_ID=:NEW.CO_ID AND (CH_PENDING='X' OR CH_STATUS='d');
IF ( NVL(FLG, 'O') <> 'X' AND SUBSTR(:NEW.CS_STAT_CHNG, -1) <> 's' AND nvl(SUBSTR(:OLD.CS_STAT_CHNG, -1),'i') <> 's') THEN
INSERT INTO IBP_STAGING@&&TO_IBP_DB ( REQ_ID, TIMESTAMP, CO_ID, SEQ, TMCODE ,SNCODE, OLD_VAL,NEW_VAL,ACTION_ID, CS_PARAM1)
VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CO_ID,:NEW.CS_SEQNO, :NEW.TMCODE,:NEW.SNCODE,:OLD.CS_STAT_CHNG,:NEW.CS_STAT_CHNG,
DECODE ( SUBSTR(:NEW.CS_STAT_CHNG,-1, 1 ), 'a', 5, 'd',6, 's', 6 ), :NEW.CS_SPARAM1) ;
END IF;
END IF;
IF (NVL(:OLD.CS_SPARAM1, 0) <> NVL(:NEW.CS_SPARAM1, 0) AND :OLD.CS_STAT_CHNG IS NOT NULL ) THEN
INSERT INTO IBP_STAGING@&&TO_IBP_DB ( REQ_ID, TIMESTAMP, CO_ID, SEQ, TMCODE ,SNCODE, OLD_VAL,NEW_VAL,ACTION_ID, CS_PARAM1)
VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CO_ID,:NEW.CS_SEQNO, :NEW.TMCODE,:NEW.SNCODE,:OLD.CS_SPARAM1,:NEW.CS_SPARAM1,7, :NEW.CS_SPARAM1) ;
END IF;
EXCEPTION
    WHEN OTHERS THEN
    NULL;
END;
/
SHOW ERRORS;



Prompt drop Trigger IBP_RATEPLAN_SY;
DROP TRIGGER IBP_RATEPLAN_SY
/

Prompt Trigger IBP_RATEPLAN_SY;
--
-- IBP_RATEPLAN_SY  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   DBMS_STANDARD (Package)
--   RATEPLAN_HIST (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER IBP_RATEPLAN_SY
AFTER INSERT OR UPDATE
OF TMCODE
ON RATEPLAN_HIST 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE 
    PRAGMA AUTONOMOUS_TRANSACTION;
TM NUMBER;
BEGIN  
CASE 
WHEN INSERTING AND :NEW.SEQNO>1 THEN
SELECT TMCODE INTO TM FROM RATEPLAN_HIST WHERE CO_ID=:NEW.CO_ID AND SEQNO=:NEW.SEQNO-1;
INSERT INTO IBP_STAGING@&&TO_IBP_DB   ( REQ_ID, TIMESTAMP,      CO_ID,       SEQ,      TMCODE,    OLD_VAL,  NEW_VAL,     ACTION_ID ) 
     VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CO_ID, :NEW.SEQNO, :NEW.TMCODE, TM, :NEW.TMCODE, 12) ;
     COMMIT;
WHEN UPDATING THEN
INSERT INTO IBP_STAGING@&&TO_IBP_DB   ( REQ_ID, TIMESTAMP,      CO_ID,       SEQ,      TMCODE,    OLD_VAL,  NEW_VAL,     ACTION_ID ) 
     VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CO_ID, :NEW.SEQNO, :NEW.TMCODE, :OLD.TMCODE, :NEW.TMCODE, 12) ;
     COMMIT;
ELSE TM:=1;
END CASE;  
EXCEPTION
    WHEN OTHERS THEN
    TM:=0;  
END;
/
SHOW ERRORS;



Prompt drop Trigger IBP_CONTRACT_UPDATE;
DROP TRIGGER IBP_CONTRACT_UPDATE
/

Prompt Trigger IBP_CONTRACT_UPDATE;
--
-- IBP_CONTRACT_UPDATE  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   DBMS_STANDARD (Package)
--   CONTRACT_HISTORY (Table)
--   CONTR_SERVICES_CAP (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER IBP_CONTRACT_UPDATE
AFTER DELETE OR INSERT OR UPDATE
OF CH_PENDING
ON CONTRACT_HISTORY 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
   CO_COUNT   NUMBER := 0;                 
BEGIN
   IF ((:NEW.CH_SEQNO > 1) OR (NVL(:NEW.CH_SEQNO, 0) = 0))
   THEN
      IF UPDATING AND (:OLD.CH_PENDING = 'X' AND :NEW.CH_PENDING IS NULL)
      THEN                   
         INSERT INTO IBP_STAGING@&&TO_IBP_DB (
                                     REQ_ID,
                                     TIMESTAMP,
                                     CO_ID,
                                     SEQ,   
                                     CH_REASON,
                                     ACTION_ID                                     
                    )
           VALUES   (
                        IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB,
                        SYSDATE,
                        :NEW.CO_ID,
                        :NEW.CH_SEQNO, 
                        :NEW.CH_REASON,
                        CASE
                           WHEN :new.ch_status = 's'
                           THEN
                              3
                           WHEN :new.ch_status = 'd'
                           THEN
                              2
                           WHEN :new.ch_status = 'a' AND :new.ch_seqno > 3
                           THEN
                              4
                           WHEN :new.ch_status = 'a' AND :new.ch_seqno <= 3
                           THEN
                              1
                        END
                    );
      ELSIF INSERTING AND (:NEW.CH_PENDING IS NULL)
      THEN
         IF (:new.CH_STATUS = 'a' OR :new.CH_STATUS = 's')
         THEN
                 
            SELECT   COUNT ( * )
              INTO   CO_COUNT
              FROM   contr_services_cap
             WHERE   CO_ID = :new.CO_ID;
           IF (CO_COUNT = 1)
            THEN
               INSERT INTO IBP_STAGING@&&TO_IBP_DB (
                                           REQ_ID,
                                           TIMESTAMP,
                                           CO_ID,  
                                           SEQ,  
                                           CH_REASON,
                                           ACTION_ID
                          )
                 VALUES   (
                              IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB,
                              SYSDATE,
                              :NEW.CO_ID, 
                              :NEW.CH_SEQNO,
                              :NEW.CH_REASON,
                              CASE
                                 WHEN :new.ch_status = 's'
                                 THEN
                                    3
                                 WHEN :new.ch_status = 'd'
                                 THEN
                                    2
                                 WHEN :new.ch_status = 'a'
                                      AND:new.ch_seqno > 3
                                 THEN
                                    4
                                 WHEN :new.ch_status = 'a'
                                      AND:new.ch_seqno <= 3
                                 THEN
                                    1
                              END
                          );
            END IF;
         ELSIF (:new.CH_STATUS = 'd')
         THEN                                                       
              SELECT   COUNT ( * ) INTO   CO_COUNT
              FROM   contr_services_cap
              WHERE DN_ID =  ( SELECT DN_ID FROM CONTR_SERVICES_CAP WHERE CO_ID = :NEW.CO_ID 
                               AND
                               SEQNO = ( SELECT MAX(SEQNO) FROM CONTR_SERVICES_CAP WHERE CO_ID = :NEW.CO_ID ) )
                               AND  CS_DEACTIV_DATE IS NULL; 
            
            IF ( CO_COUNT = 0 )
            THEN
               INSERT INTO IBP_STAGING@&&TO_IBP_DB (
                                           REQ_ID,
                                           TIMESTAMP,
                                           CO_ID,
                                           SEQ,    
                                           CH_REASON,
                                           ACTION_ID
                          )
                 VALUES   (
                              IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB,
                              SYSDATE,
                              :NEW.CO_ID,   
                              :NEW.CH_SEQNO,      
                              :NEW.CH_REASON,
                              CASE
                                 WHEN :new.ch_status = 's'
                                 THEN
                                    3
                                 WHEN :new.ch_status = 'd'
                                 THEN
                                    2
                                 WHEN :new.ch_status = 'a'
                                      AND:new.ch_seqno > 3
                                 THEN
                                    4
                                 WHEN :new.ch_status = 'a'
                                      AND:new.ch_seqno <= 3
                                 THEN
                                    1
                              END
                          );
            END IF;
         END IF;
      ELSIF DELETING AND :OLD.CH_PENDING IS NULL AND :OLD.CH_STATUS = 'd'
      THEN  
         INSERT INTO IBP_STAGING@&&TO_IBP_DB (REQ_ID,
                                  TIMESTAMP,
                                  CO_ID, 
                                  SEQ,     
                                  CH_REASON,
                                  ACTION_ID)
           VALUES   (IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB,
                     SYSDATE,
                     :OLD.CO_ID, 
                     :OLD.CH_SEQNO-1,  
                     99,
                     1);
      END IF;
   END IF;
EXCEPTION
    WHEN OTHERS THEN
    NULL;
END;
/
SHOW ERRORS;



Prompt drop Trigger IBP_CONTR_SERV_CAP;
DROP TRIGGER IBP_CONTR_SERV_CAP
/

Prompt Trigger IBP_CONTR_SERV_CAP;
--
-- IBP_CONTR_SERV_CAP  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   CONTR_SERVICES_CAP (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER IBP_CONTR_SERV_CAP
AFTER INSERT OR UPDATE
OF CS_ACTIV_DATE
ON CONTR_SERVICES_CAP 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE 
    PRAGMA AUTONOMOUS_TRANSACTION;
    DN NUMBER;
BEGIN  
IF ( :NEW.SEQNO > 1  AND :NEW.CS_ACTIV_DATE IS NOT NULL ) THEN
SELECT DN_ID INTO DN FROM CONTR_SERVICES_CAP WHERE CO_ID=:NEW.CO_ID AND SEQNO=:NEW.SEQNO-1;
INSERT INTO IBP_STAGING@&&TO_IBP_DB   ( REQ_ID, TIMESTAMP,      CO_ID,       SEQ,    OLD_VAL,  NEW_VAL,     ACTION_ID) 
     VALUES ( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, SYSDATE, :NEW.CO_ID, :NEW.SEQNO, DN, :NEW.DN_ID, 9) ;
     COMMIT;
END IF;    
EXCEPTION
    WHEN OTHERS THEN
    DN:=0;
END;
/
SHOW ERRORS;


Prompt drop Trigger INSERT_TAKE_OVER;
DROP TRIGGER INSERT_TAKE_OVER
/

Prompt Trigger INSERT_TAKE_OVER;
--
-- INSERT_TAKE_OVER  (Trigger) 
--
--  Dependencies: 
--   STANDARD (Package)
--   MPDPLTAB (Table)
--   MPDSCTAB (Table)
--   MAXVALUE (Table)
--   MDSRRTAB (Table)
--   PORT (Table)
--   DIRECTORY_NUMBER (Table)
--   COSTCENTER (Table)
--   CUSTOMER_ALL (Table)
--   CONTRACT_ALL (Table)
--   CONTR_DEVICES (Table)
--   CONTR_SERVICES_CAP (Table)
--   FF_DOMAIN (Table)
--   CGTF_BCCHANGE_QUEUE (Table)
--   CGTF_CURR_CO_STATUS (Table)
--   BSCS_INFRANET_SYNCHRO_SEQ (Sequence)
--   TICKLER_RECORDS (Table)
--   IBP_STAGING (Table)
--   IBP_STAGING_SEQ (Sequence)
--
CREATE OR REPLACE TRIGGER INSERT_TAKE_OVER
AFTER INSERT
ON TICKLER_RECORDS 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
    trig1s1 varchar2(500);
    trig1s2 varchar2(500);
    num number(22);
    util number(22);
    valuemax    NUMBER;
    co_id_in_char varchar2(50);
    co_id_out_char varchar2(50);
    co_id_in TICKLER_RECORDS.co_id%type;
    co_id_out TICKLER_RECORDS.co_id%type;
    costc_source CUSTOMER_ALL.COSTCENTER_ID%TYPE;
    costc_cible CUSTOMER_ALL.COSTCENTER_ID%TYPE;
    busource COSTCENTER.COST_CODE%TYPE;
    bucible COSTCENTER.COST_CODE%TYPE;
    custcode_source CUSTOMER_ALL.CUSTCODE%TYPE;
    custcode_cible CUSTOMER_ALL.CUSTCODE%TYPE;
    IMSI PORT.PORT_NUM%TYPE;
    numero DIRECTORY_NUMBER.DN_NUM%TYPE;
    numero1 varchar2(10);
    length_cust_in number(22);
    length_cust_out number(22);
    type_contrat CONTRACT_ALL.SCCODE%TYPE;
    cmd_ins varchar2(2000);
	new_cust_id CUSTOMER_ALL.customer_id%TYPE;
	new_dmn_id 	FF_DOMAIN.domain_id%TYPE;
	nb_domains 	NUMBER;
-- WTR0771 variable declarations BEGIN
    to_old_co_id NUMBER;
    to_new_co_id NUMBER;
    trig1s3 VARCHAR2(500);
    trig1s4 VARCHAR2(500);
    old_co_id_char VARCHAR2(50);
    new_co_id_char VARCHAR2(50);
    status_co	   VARCHAR2(1);
    new_co_bc	   VARCHAR2(2);
    count_coid NUMBER :=0; -- SC_2839
-- WTR0771 variable declaration END
BEGIN
--WTR0771 BEGIN
	IF :new.SHORT_DESCRIPTION like 'CO TAKEOVER TO' THEN -- short description check
		-- old co_id
		trig1s3 := substr(
                      substr(:new.long_description,
                             instr(:new.long_description,
                                   CHR(13),
                                   2 )-2,
                            (instr(:new.long_description,
                                   CHR(13),
                                   instr(:new.long_description,CHR(13),2 ) + 1)
                           - instr(:new.long_description,
                                   CHR(13),
                                   2 )-2)),
                      instr(
                             translate(
                                       substr(:new.long_description,
                                              instr(:new.long_description,
                                                    CHR(13),
                                                    2 )+0,
                                              (instr(:new.long_description,
                                                     CHR(13),
                                                     instr(:new.long_description,
                                                           CHR(13),
                                                           2 ) + 1)
                                               - instr(:new.long_description,
                                                       CHR(13),
                                                       2 )-2)),
                                       '0123456789',
                                       '9999999999'),
                             CHR(57),
                             1));
		--new_co_id
		trig1s4 := substr(
                   substr(:new.long_description,
                          instr(:new.long_description,
                                CHR(13),
                                instr(:new.long_description,CHR(13),2 ) + 1)),
                   instr(
                         translate(
                                   substr(:new.long_description,
                                          instr(:new.long_description,
                                                CHR(13),
                                                instr(:new.long_description,CHR(13),2 ) + 1)),
                                   '0123456789',
                                   '9999999999'),
                         CHR(57),
                         1));
		--Conversion from varchar2 to number type
	    num:=1;
	    util:=0;
	    WHILE (num < 11) AND (util = 0)
   		LOOP
      		IF    (ASCII (SUBSTR (trig1s3, num, 1)) BETWEEN 48 AND 57)OR (ASCII (SUBSTR (trig1s3, num, 1)) = 32) THEN
	                old_co_id_char:=substr(trig1s3,0,num);
	                num:=num + 1 ;
	        ELSE
	            util:=1;
	            num:=0;
	        END IF;
	      END LOOP;
	    to_old_co_id :=to_number(old_co_id_char);
	    num:=1;
	    util:=0;
	    WHILE (num<9) AND (util=0)
	    	LOOP
	 	IF ascii(substr(trig1s4,num,1)) BETWEEN 48 AND 57 THEN
	                new_co_id_char:=substr(trig1s4,0,num);
	                num:=num + 1 ;
	        ELSE
	            util:=1;
	            num:=0;
	        END IF;
	      END LOOP;
	    to_new_co_id:=to_number(new_co_id_char);
	  SELECT CH_STATUS INTO status_co FROM CGTF_CURR_CO_STATUS where co_id = to_new_co_id;
	  IF ( upper(status_co) ='A' OR upper(status_co) ='S')
	  THEN
		SELECT billcycle
		  INTO new_co_bc
		  FROM customer_all
		 WHERE customer_id IN ( SELECT customer_id
					  FROM contract_all
					 WHERE co_id = to_new_co_id);
		-- SC_2839  BEGIN
		SELECT count(*)
		INTO count_coid
		FROM contract_all
		WHERE sccode in (SELECT sccode
				   FROM mpdsctab
				  WHERE des LIKE ('GSM%'))
		AND CO_ID = to_new_co_id;
		IF (count_coid > 0) THEN
			INSERT INTO cgtf_bcchange_queue (seq_id,new_co_id,old_co_id,old_billcycle,new_billcycle,entry_date,extraction_date,extracted_flg)
			VALUES (BSCS_Infranet_synchro_SEQ.nextval,to_new_co_id,to_old_co_id,NULL,new_co_bc,SYSDATE,NULL,NULL);
		END IF;
		 -- SC_2839 END
	  END IF;


      INSERT into IBP_STAGING@&&TO_IBP_DB( REQ_ID, TIMESTAMP, CO_ID, CUSTOMER_ID, OLD_VAL, NEW_VAL, ACTION_ID )
      VALUES( IBP_STAGING_SEQ.NEXTVAL@&&TO_IBP_DB, sysdate, to_new_co_id, :NEW.CUSTOMER_ID,to_old_co_id, to_new_co_id, 11 );
	END IF;-- short description check
     --WTR0771 END
    IF :new.SHORT_DESCRIPTION = 'CO TAKEOVER TO' and :new.LONG_DESCRIPTION like 'Sub%' THEN
    trig1s1:=substr(
                      substr(:new.long_description,
                             instr(:new.long_description,
                                   CHR(13),
                                   2 )+2,
                            (instr(:new.long_description,
                                   CHR(13),
                                   instr(:new.long_description,CHR(13),2 ) + 1)
                           - instr(:new.long_description,
                                   CHR(13),
                                   2 )-2)),
                      instr(
                             translate(
                                       substr(:new.long_description,
                                              instr(:new.long_description,
                                                    CHR(13),
                                                    2 )+2,
                                              (instr(:new.long_description,
                                                     CHR(13),
                                                     instr(:new.long_description,
                                                           CHR(13),
                                                           2 ) + 1)
                                               - instr(:new.long_description,
                                                       CHR(13),
                                                       2 )-2)),
                                       '0123456789',
                                       '9999999999'),
                             CHR(57),
                             1));
    trig1s2:=substr(
                   substr(:new.long_description,
                          instr(:new.long_description,
                                CHR(13),
                                instr(:new.long_description,CHR(13),2 ) + 1)),
                   instr(
                         translate(
                                   substr(:new.long_description,
                                          instr(:new.long_description,
                                                CHR(13),
                                                instr(:new.long_description,CHR(13),2 ) + 1)),
                                   '0123456789',
                                   '9999999999'),
                         CHR(57),
                         1));
    num:=1;
    util:=0;
    while (num<9) and (util=0) loop
--    dbms_output.put_line('numero: '||num);
     if ascii(substr(trig1s1,num,1)) between 48 and 57 then
                co_id_in_char:=substr(trig1s1,0,num);
                num:=num + 1 ;
        Else
            util:=1;
            num:=0;
        end if;
      end loop;
    co_id_in:=to_number(co_id_in_char);
-- SC_1031 init de la variable num, sinon si co_id_in_char a 7 caract.
-- la condition suivante est realisee avant meme le traitement du
-- second numero de contrat --> erreur KV
    num:=1;
    util:=0;
    while (num<9) and (util=0) loop
 --   dbms_output.put_line('numero: '||num);
     if ascii(substr(trig1s2,num,1)) between 48 and 57 then
                co_id_out_char:=substr(trig1s2,0,num);
                num:=num + 1 ;
        Else
            util:=1;
            num:=0;
        end if;
      end loop;
    co_id_out:=to_number(co_id_out_char);
    select cu.COSTCENTER_ID
    into costc_source
    from CUSTOMER_ALL cu, CONTRACT_ALL ca
    where ca.co_id=co_id_in
    and ca.customer_id=cu.customer_id;
    select cos.COST_CODE
    into busource
    from COSTCENTER cos
    where COST_ID=costc_source;
    select cu.COSTCENTER_ID
    into costc_cible
    from CUSTOMER_ALL cu, CONTRACT_ALL ca
    where ca.co_id=co_id_out
    and ca.customer_id=cu.customer_id;
    select cos.COST_CODE
    into bucible
    from COSTCENTER cos
    where COST_ID=costc_cible;
    IF  costc_source<>costc_cible then
      SELECT value
      INTO   valuemax
      FROM   maxvalue
      WHERE  value_id= 'MAX_REQUEST'
      FOR UPDATE;
      UPDATE maxvalue
      SET    value= valuemax + 1,
             rec_version = rec_version + 1
      WHERE  value_id='MAX_REQUEST';
      select instr(cu.custcode,'.',3)
      into length_cust_in
      from CUSTOMER_ALL cu, CONTRACT_ALL ca
      where ca.co_id=co_id_in
      and ca.customer_id=cu.customer_id;
--Modification JEK change select substr(cu.custcode,0,instr(cu.custcode,'.',3))->select substr(cu.custcode,0,instr(cu.custcode,'.',3)-1) au niveau du else
      IF length_cust_in=0 then
         select substr(cu.custcode,0,length(cu.custcode))
         into custcode_source
         from CUSTOMER_ALL cu, CONTRACT_ALL ca
         where ca.co_id=co_id_in
         and ca.customer_id=cu.customer_id;
      else
         select substr(cu.custcode,0,instr(cu.custcode,'.',3)-1)
         into custcode_source
         from CUSTOMER_ALL cu, CONTRACT_ALL ca
         where ca.co_id=co_id_in
         and ca.customer_id=cu.customer_id;
      end if;
      select instr(cu.custcode,'.',3)
      into length_cust_out
      from CUSTOMER_ALL cu, CONTRACT_ALL ca
      where ca.co_id=co_id_out
      and ca.customer_id=cu.customer_id;
--Modification JEK change select substr(cu.custcode,0,instr(cu.custcode,'.',3))->select substr(cu.custcode,0,instr(cu.custcode,'.',3)-1)) au niveau du else
      IF Length_cust_out=0 then
         select substr(cu.custcode,0,length(cu.custcode))
         into custcode_cible
         from CUSTOMER_ALL cu, CONTRACT_ALL ca
         where ca.co_id=co_id_out
         and ca.customer_id=cu.customer_id;
      else
         select substr(cu.custcode,0,instr(cu.custcode,'.',3)-1)
         into custcode_cible
         from CUSTOMER_ALL cu, CONTRACT_ALL ca
         where ca.co_id=co_id_out
         and ca.customer_id=cu.customer_id;
      end if;
      select dn.DN_NUM
      into numero
      from contr_services_cap csc,directory_number dn
      where csc.co_id=co_id_in
      and csc.dn_id=dn.dn_id
      and csc.main_dirnum='X'
      and csc.seqno=(select max(css2.seqno)
                    from contr_services_cap css2
                    where  css2.co_id=csc.co_id and css2.sncode= csc.sncode);
      select contract_all.sccode
      into type_contrat
      from contract_all
      where co_id_in=co_id;
      IF type_contrat<>1 THEN
            IMSI:='';
      ELSE
         select p.port_num
         into IMSI
         from PORT p, CONTR_DEVICES cd
         where cd.cd_seqno=
               (select max(contr_devices.cd_seqno)
               from  contr_devices
               where contr_devices.co_id=cd.co_id)
         and cd.co_id=co_id_in
         and p.port_id=cd.port_id;
      end if;
--    dbms_output.put_line('co_id initial: '||co_id_in||' COSTCENTER_ID :'||costc_source||' CUSTCODE :'||custcode_source||' NUMERO :'||numero||' IMSI :'||IMSI);
--   dbms_output.put_line('co_id final: '||co_id_out||' COSTCENTER_ID :'||costc_cible||' CUSTCODE :'||custcode_cible);
--    cmd_ins:='CBUTAK'||'@'||costc_source||'@'||costc_cible||'@'||custcode_source||'@'||custcode_cible||'@'||IMSI||'@'||numero;
    numero1:=lpad(numero,10,0);
    cmd_ins:='TAKCBU'||'@'||busource||'@'||bucible||'@'||custcode_source||'@'||custcode_cible||'@'||IMSI||'@'||numero1;
 --   dbms_output.put_line('cmd '||cmd_ins);
    INSERT INTO MDSRRTAB (REQUEST, PLCODE, STATUS, CMD, RETURNCODE,
                          TS, USERID, REF_TEXT, CUSTOMER_ID, CO_ID,
                          INSERT_DATE, REQUEST_UPDATE, PRIORITY,
                          ACTION_DATE, SCCODE, GMD_MARKET_ID,
                          RES,VMD_RETRY,ERROR_RETRY,REC_VERSION)
    select valuemax,
           cd.cd_plcode,
           2,
           cmd_ins,
           0,
           SYSDATE,
           USER,
           'BU change of IMSI: '||IMSI,
           ca.customer_id,
           co_id_out,
           sysdate,
           'F',
           2,
           sysdate,
           sc.sccode,
           sc.scslprefix,
           ' ',
           0,
           0,
           0
   	  FROM        contr_devices cd,
                	mpdsctab sc,
                	mpdpltab pl,
                	contract_all ca
      WHERE     	cd.co_id = co_id_in
      AND     	cd.cd_seqno =
                    (
                    SELECT MAX(cd_seqno)
                    FROM   contr_devices
                    WHERE  co_id=co_id_in
                    )
      AND       	ca.co_id = co_id_in
      AND       	pl.plcode = cd.cd_plcode
      AND       	sc.sccode = pl.sccode;
    END if;
-- SGA ER4946 BEGIN : Removed
--/* BEGIN FT_CGTF/CMS/MCBS/JS/19990901 <ER-1306> */
/*	SELECT 	count(domain_id)
	INTO 	nb_domains
	FROM 	ff_domain_hist
	WHERE 	co_id = co_id_in;
	IF nb_domains > 0 THEN
	BEGIN
		SELECT 	value
		INTO 	new_dmn_id
		FROM 	maxvalue
		WHERE 	value_id = 'MAX_DOMAIN_ID'
		FOR UPDATE;
		UPDATE 	maxvalue
		SET 	value = new_dmn_id + 1,
				rec_version = rec_version + 1
		WHERE 	value_id = 'MAX_DOMAIN_ID';
		SELECT 	customer_id
		INTO 	new_cust_id
		FROM 	contract_all
		WHERE 	co_id = co_id_out;
		INSERT INTO ff_domain_hist (customer_id, co_id, domain_id, type, validfrom)
		SELECT 	new_cust_id,
				co_id_out,
				new_dmn_id,
				type,
				validfrom
		FROM	ff_domain_hist
		WHERE	co_id = co_id_in
		AND		NVL(validfrom,TO_DATE('01-JAN-70')) <= SYSDATE;
		INSERT INTO ff_domain (domain_id, domain, type)
		SELECT	new_dmn_id,
				ff.domain,
				ff.type
		FROM	ff_domain ff, ff_domain_hist ffh
		WHERE	ffh.domain_id = ff.domain_id
		AND		ffh.co_id = co_id_in
		AND		NVL(ffh.validfrom,TO_DATE('01-JAN-70')) <= SYSDATE;
		UPDATE	ff_domain_hist
		SET		customer_id = new_cust_id,
				co_id = co_id_out
		WHERE	co_id = co_id_in
		AND		validfrom > SYSDATE;
	END;
	END IF;*/
--/* END FT_CGTF/CMS/MCBS/JS/19990901 <ER-1306> */
-- SGA ER4946 END
   
    END IF;
END;
/
SHOW ERRORS;

----------------------------------------------------------------------------------------
--     3. Create IBP_VIEW
----------------------------------------------------------------------------------------


/* Formatted on 10-06-2013 13:11:56 (QP5 v5.115.810.9015) */
Prompt View IBP_VIEW;
--
-- IBP_VIEW  (View)
--
--  Dependencies:
--   DIRECTORY_NUMBER (Table)
--   CUSTOMER_ALL (Table)
--   CONTRACT_ALL (Table)
--   CONTRACT_HISTORY (Table)
--   CONTR_DEVICES (Table)
--   CONTR_SERVICES_CAP (Table)
--   RATEPLAN_HIST (Table)
--

CREATE OR REPLACE FORCE VIEW IBP_VIEW
(
   CUSTOMER_ID,
   CO_ID,
   DN_NUM,
   BILLCYCLE,
   TMCODE,
   SCCODE,
   CH_STATUS,
   CD_PORT_NUM
)
AS
   SELECT   c.customer_id,
            d.co_id,
            b.dn_num,
            c.billcycle,
            f.tmcode,
            d.sccode,
            e.ch_status,
            g.cd_port_num
     FROM   contr_services_cap a,
            directory_number b,
            customer_all c,
            contract_all d,
            contract_history e,
            rateplan_hist f,
            contr_devices g
    WHERE       a.dn_id = b.dn_id
            AND d.customer_id = c.customer_id
            AND d.co_id = a.co_id
            AND e.co_id = a.co_id
            AND f.co_id = a.co_id
            AND g.co_id = a.co_id
            AND e.ch_seqno =
                  (SELECT   MAX (ch_seqno)
                     FROM   contract_history
                    WHERE   co_id = a.co_id AND ch_pending IS NULL)
            AND f.seqno = (SELECT   MAX (seqno)
                             FROM   rateplan_hist
                            WHERE   co_id = a.co_id)
            AND a.seqno =
                  (SELECT   MAX (seqno)
                     FROM   contr_services_cap
                    WHERE   co_id = a.co_id AND sncode IN (1, 64, 1585))
            AND g.cd_seqno = (SELECT   MAX (cd_seqno)
                                FROM   contr_devices
                               WHERE   co_id = a.co_id)
            AND a.sncode IN (1, 64, 1585)
/



