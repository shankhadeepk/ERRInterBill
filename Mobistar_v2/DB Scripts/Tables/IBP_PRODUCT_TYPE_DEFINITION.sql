ALTER TABLE IBP_PRODUCT_TYPE_DEFINITION
 DROP PRIMARY KEY CASCADE;

DROP TABLE IBP_PRODUCT_TYPE_DEFINITION CASCADE CONSTRAINTS;

CREATE TABLE IBP_PRODUCT_TYPE_DEFINITION
(
  PRODUCT_TYPE_ID  NUMBER                       NOT NULL,
  PRODUCT_TYPE     VARCHAR2(50 BYTE)            NOT NULL
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


ALTER TABLE IBP_PRODUCT_TYPE_DEFINITION ADD (
  PRIMARY KEY
 (PRODUCT_TYPE_ID)
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
