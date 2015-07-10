ALTER TABLE IBP_PROVIDER_CONSUMER_LINK
 DROP PRIMARY KEY CASCADE;

DROP TABLE IBP_PROVIDER_CONSUMER_LINK CASCADE CONSTRAINTS;

CREATE TABLE IBP_PROVIDER_CONSUMER_LINK
(
  IBP_PROVIDER_CONSUMER_ID  NUMBER,
  CUSTOMER_ID               NUMBER              NOT NULL,
  COID                      NUMBER              NOT NULL,
  PROVIDER_OFFER_ID         NUMBER,
  PROVIDER_MSISDN           NUMBER,
  CONSUMER_OFFER_ID         NUMBER,
  CONSUMER_MSISDN           NUMBER
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


CREATE INDEX CUSTOMER_ID_IND ON IBP_PROVIDER_CONSUMER_LINK
(CUSTOMER_ID)
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


ALTER TABLE IBP_PROVIDER_CONSUMER_LINK ADD (
  PRIMARY KEY
 (IBP_PROVIDER_CONSUMER_ID)
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