ALTER TABLE IBP_BSCS_ACTIONS
 DROP PRIMARY KEY CASCADE;

DROP TABLE IBP_BSCS_ACTIONS CASCADE CONSTRAINTS;

CREATE TABLE IBP_BSCS_ACTIONS
(
  ACTION_ID         NUMBER                      NOT NULL,
  BSCS_ACTION_NAME  VARCHAR2(50 CHAR)
)
TABLESPACE DATA
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;


ALTER TABLE IBP_BSCS_ACTIONS ADD (
  PRIMARY KEY
 (ACTION_ID)
    USING INDEX 
    TABLESPACE DATA
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                PCTINCREASE      0
               ));