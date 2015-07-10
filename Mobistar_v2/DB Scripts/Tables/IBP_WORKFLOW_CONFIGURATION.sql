DROP TABLE IBP_WORKFLOW_CONFIGURATION CASCADE CONSTRAINTS;

CREATE TABLE IBP_WORKFLOW_CONFIGURATION
(
  ACTION_ID         NUMBER,
  PRODUCT_TYPE_ID   NUMBER,
  MBC_WORKFLOW_ID   NUMBER,
  MZ_WORKFLOW_ID    NUMBER,
  PCRF_WORKFLOW_ID  NUMBER
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


ALTER TABLE IBP_WORKFLOW_CONFIGURATION ADD (
  FOREIGN KEY (PRODUCT_TYPE_ID) 
 REFERENCES IBP_PRODUCT_TYPE_DEFINITION (PRODUCT_TYPE_ID)
    ON DELETE CASCADE);
