create table TBTI_CODETYP
(
    LAUF_NR           INTEGER                                not null,
    ERFASSUNGSZEIT    TIMESTAMP(6) default now()             not null,
    ERFASSER          CHAR(7)      default ''                not null,
    NAME              CHAR(50)     default ''                not null,
    ZUSTAENDIGKEIT_NR INTEGER      default 0                 not null
);
