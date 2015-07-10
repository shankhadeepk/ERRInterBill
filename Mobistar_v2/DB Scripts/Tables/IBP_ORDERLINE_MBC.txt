ALTER TABLE IBP_ORDERLINE_MBC
 DROP PRIMARY KEY CASCADE;

DROP TABLE IBP_ORDERLINE_MBC CASCADE CONSTRAINTS;

CREATE TABLE IBP_ORDERLINE_MBC
(
  REQUEST_ID                    NUMBER,
  ORDERLINE_OBJECT              CLOB,
  CUSTOMER_ID                   NUMBER,
  MSISDN                        NUMBER,
  MSISDN_SEQUENCE               NUMBER,
  ACTION_ID                     NUMBER          NOT NULL,
  ORDERLINE_CREATION_TIMESTAMP  DATE,
  PROCESS_TIME_STAMP            DATE,
  APPLICATION_RETURN_CODE       NUMBER,
  RETURN_CODE                   NUMBER,
  RETRY_NUMBER                  NUMBER
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
LOB (ORDERLINE_OBJECT) STORE AS 
      ( TABLESPACE  DATA 
        ENABLE      STORAGE IN ROW
        CHUNK       8192
        RETENTION
        NOCACHE
        INDEX       (
          TABLESPACE DATA
          STORAGE    (
                      INITIAL          64K
                      NEXT             1
                      MINEXTENTS       1
                      MAXEXTENTS       UNLIMITED
                      PCTINCREASE      0
                      BUFFER_POOL      DEFAULT
                     ))
        STORAGE    (
                    INITIAL          64K
                    NEXT             1M
                    MINEXTENTS       1
                    MAXEXTENTS       UNLIMITED
                    PCTINCREASE      0
                    BUFFER_POOL      DEFAULT
                   )
      )
NOCACHE
NOPARALLEL
NOMONITORING;


CREATE UNIQUE INDEX IBP_ORDERLINE_MBC_PK ON IBP_ORDERLINE_MBC
(REQUEST_ID)
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


ALTER TABLE IBP_ORDERLINE_MBC ADD (
  CONSTRAINT IBP_ORDERLINE_MBC_PK
 PRIMARY KEY
 (REQUEST_ID)
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
