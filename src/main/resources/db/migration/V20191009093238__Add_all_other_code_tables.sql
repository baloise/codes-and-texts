create table TBTI_APPHIERARCHIE
(
    APPL_NR     INTEGER not null,
    CHILDAPP_NR INTEGER not null
);

create table TBTI_APPLIKATION
(
    LAUF_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    NAME           CHAR(50)     default ''                not null,
    APPACKAGE      CHAR(50)     default ''                not null,
    CODEWERT_TYP   INTEGER      default 0                 not null
);

create table TBTI_ASSCODE
(
    TYP_NR          INTEGER              not null,
    CODE            SMALLINT             not null,
    HOSTCODE        CHAR(10)  default '' not null,
    WERT_D          CHAR(120) default '' not null,
    WERT_F          CHAR(120) default '' not null,
    WERT_I          CHAR(120) default '' not null,
    WERT_E          CHAR(120) default '' not null,
    BEMAUSLIEFERUNG CHAR(15)  default '' not null,
    TEXT_NR         INTEGER   default 0  not null,
    HOSTTEXT_NR     INTEGER   default 0  not null
);

create table TBTI_ASSCODETYP
(
    LAUF_NR          INTEGER             not null,
    NAME             CHAR(40) default '' not null,
    CODETYP_NR       INTEGER  default 0  not null,
    CODETEXTSTIL_NR  INTEGER  default 0  not null,
    HOSTCDTXTSTIL_NR INTEGER  default 0  not null
);

create table TBTI_CODEELEMENT
(
    APPL_NR          INTEGER                                not null,
    ELEM_NR          INTEGER                                not null,
    TYP              INTEGER      default 0                 not null,
    ERFASSUNGSZEIT   TIMESTAMP(6) default now()             not null,
    ERFASSER         CHAR(7)      default ''                not null,
    CODETYP_NR       INTEGER      default 0                 not null,
    MITCODENULL      CHAR(1)      default ''                not null,
    CODENULLTEXT_NR  INTEGER      default 0                 not null,
    ALPHABSORTIERUNG CHAR(1)      default ''                not null
);

create table TBTI_CODEREF
(
    APPL_NR        INTEGER                                not null,
    ELEM_NR        INTEGER                                not null,
    LAUF_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    TEXT_NR        INTEGER      default 0                 not null,
    CODETYP_NR     INTEGER      default 0                 not null
);

create table TBTI_CODETEXT
(
    TEXT_NR        INTEGER                                not null,
    CODETYP_NR     INTEGER      default 0                 not null,
    CODEWERT       INTEGER      default 0                 not null,
    STIL_NR        INTEGER      default 0                 not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null
);

create table TBTI_CODETXTSTIL
(
    CODETYP_NR     INTEGER                                not null,
    STIL_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    NAME           CHAR(50)     default ''                not null
);

create table TBTI_CODEWERT
(
    CODETYP_NR     INTEGER                                not null,
    WERT           INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    NAME           CHAR(50)     default ''                not null
);

create table TBTI_ELEMENT
(
    APPL_NR        INTEGER                                not null,
    ELEM_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    NAME           CHAR(50)     default ''                not null,
    TYP            INTEGER      default 0                 not null,
    TEXT_NR        INTEGER      default 0                 not null,
    MNEMONIC_NR    INTEGER      default 0                 not null
);

create table TBTI_MELDUNGSREF
(
    APPL_NR        INTEGER      default 0                 not null,
    MELDUNGS_NR    INTEGER      default 0                 not null,
    STIL_NR        INTEGER      default 0                 not null,
    TEXT_NR        INTEGER      default 0                 not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null
);

create table TBTI_MELDUNGSSTIL
(
    APPL_NR        INTEGER                                not null,
    STIL_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    NAME           CHAR(50)     default ''                not null
);

create table TBTI_MNEMONIC
(
    LAUF_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    WERT_D         CHAR(1)      default ''                not null,
    WERT_F         CHAR(1)      default ''                not null,
    WERT_I         CHAR(1)      default ''                not null,
    WERT_E         CHAR(1)      default ''                not null
);

create table TBTI_TABSYSCODES
(
    TABSYSCODETYP  INTEGER                                not null,
    CUTCODETYP     INTEGER                                not null,
    CUTCODEWERT    INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    TABSYSCODEWERT VARCHAR(50)  default ''                not null
);

create table TBTI_TABSYSTABELLE
(
    TABSYSCODETYP     INTEGER                                not null,
    CUTCODETYP        INTEGER                                not null,
    ERFASSUNGSZEIT    TIMESTAMP(6) default now()             not null,
    ERFASSER          CHAR(7)      default ''                not null,
    TABSYSCODETYPNAME CHAR(100)    default ''                not null,
    VERTEILUNG        TIMESTAMP(6) default now()             not null
);

create table TBTI_TEXT
(
    LAUF_NR        INTEGER                                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    ERFASSER       CHAR(7)      default ''                not null,
    TYP            INTEGER      default 0                 not null,
    WERT_D         CHAR(255)    default ''                not null,
    WERT_F         CHAR(255)    default ''                not null,
    WERT_I         CHAR(255)    default ''                not null,
    WERT_E         CHAR(255)    default ''                not null
);

create table TBTI_TSY_CODETXT
(
    CODETYP        INTEGER                                not null,
    CODEWERT       INTEGER                                not null,
    STIL           INTEGER                                not null,
    TEXT_D         CHAR(254)    default ''                not null,
    TEXT_F         CHAR(254)    default ''                not null,
    TEXT_I         CHAR(254)    default ''                not null,
    TEXT_E         CHAR(254)    default ''                not null,
    ERFASSER       CHAR(7)      default ''                not null,
    ERFASSUNGSZEIT TIMESTAMP(6) default now()             not null,
    primary key (CODETYP, CODEWERT, STIL)
);

create table TBTI_TSY_CODETYP
(
    CODETYP INTEGER             not null
        primary key,
    PRAEFIX CHAR(40) default '' not null,
    NAME    CHAR(50) default '' not null
);

create table TBTI_ZUSTAENDIG
(
    ZUSTAENDIGKEIT_NR INTEGER                                not null,
    PROJEKTNAME       CHAR(50)                               not null,
    ERFASSUNGSZEIT    TIMESTAMP(6) default now()             not null,
    ERFASSER          CHAR(7)      default ''                not null,
    PACKAGE           CHAR(255)    default ''                not null,
    CODETYPPRAEFIX    CHAR(40)     default ''                not null,
    ZUSTAENDIGKEIT    CHAR(255)    default ''                not null,
    primary key (ZUSTAENDIGKEIT_NR, PROJEKTNAME)
);