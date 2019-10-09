DROP VIEW application;
DROP VIEW codetyp;
DROP VIEW codevalue;

CREATE VIEW codetype (id, name, creator, created) AS
SELECT lauf_nr,
       RTRIM(name),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_codetyp;

CREATE VIEW codevalue (id, codetype_id, name, creator, created) AS
SELECT wert,
       codetyp_nr,
       RTRIM(name),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_codewert;

CREATE VIEW application (id, name, package, creator, created) AS
SELECT lauf_nr,
       RTRIM(name),
       RTRIM(appackage),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_applikation;
