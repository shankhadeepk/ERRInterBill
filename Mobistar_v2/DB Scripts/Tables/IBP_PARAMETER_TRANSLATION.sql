DROP TABLEIBP_PARAMETER_TRANSLATION CASCADE CONSTRAINTS;

CREATE TABLEIBP_PARAMETER_TRANSLATION
(
  PARAMETER_ENCODING  VARCHAR2(40 CHAR),
  DECODED_VALUE       NUMBER,
  THRESHOLD_ID        NUMBER,
  EVENT_TYPE          VARCHAR2(4 CHAR),
  MONETARY_AMT        NUMBER,
  TMCODE              VARCHAR2(5 BYTE),
  SNCODE              NUMBER,
  IBP_ACTION          NUMBER
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

COMMENT ON TABLEIBP_PARAMETER_TRANSLATION IS '1 � Roaming Limit Change
2 � Profile Update
';

