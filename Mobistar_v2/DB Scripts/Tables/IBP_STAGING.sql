ALTER TABLE IBP_STAGING
 DROP PRIMARY KEY CASCADE;

DROP TABLE IBP_STAGING CASCADE CONSTRAINTS;

CREATE TABLE IBP_STAGING
(
  TIMESTAMP       DATE,
  REQ_ID          INTEGER                       NOT NULL,
  CO_ID           NUMBER,
  CUSTOMER_ID     NUMBER,
  SEQ             NUMBER,
  TMCODE          NUMBER,
  SNCODE          NUMBER,
  OLD_VAL         VARCHAR2(2000 CHAR),
  NEW_VAL         VARCHAR2(2000 CHAR),
  ACTION_ID       NUMBER,
  REQUEST_STATUS  CHAR(10 CHAR),
  BILLCYCLE       NUMBER,
  CS_PARAM1       VARCHAR2(255 CHAR),
  CH_REASON       CHAR(1 CHAR)
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


CREATE UNIQUE INDEX IBP_STAGING_PK ON IBP_STAGING
(REQ_ID)
LOGGING
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
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


ALTER TABLE IBP_STAGING ADD (
  CONSTRAINT IBP_STAGING_PK
 PRIMARY KEY
 (REQ_ID)
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
