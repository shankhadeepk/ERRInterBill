DROP TABLE IBP_PROCESSING_QUEUE CASCADE CONSTRAINTS;

CREATE TABLE IBP_PROCESSING_QUEUE
(
  ID                        NUMBER              NOT NULL,
  CUSTOMER_ID               NUMBER              NOT NULL,
  BILLCYCLE                 VARCHAR2(2 BYTE),
  CH_REASON                 CHAR(1 BYTE),
  MSISDN                    VARCHAR2(20 BYTE),
  CO_ID                     NUMBER,
  TMCODE                    NUMBER,
  SNCODE                    NUMBER,
  SERVICEPARAMETER          VARCHAR2(255 BYTE),
  OLD_VAL                   VARCHAR2(2000 BYTE),
  NEW_VAL                   VARCHAR2(2000 BYTE),
  ACTION_ID                 NUMBER              NOT NULL,
  PROCESS_STATUS_TIMESTAMP  DATE,
  PROCESS_STATUS            NUMBER,
  REMARK                    VARCHAR2(255 BYTE),
  SCCODE                    NUMBER,
  REQ_ID                    NUMBER              NOT NULL
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

