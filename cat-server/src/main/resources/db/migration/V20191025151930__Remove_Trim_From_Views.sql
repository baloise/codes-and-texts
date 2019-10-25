DROP VIEW codevalue;
DROP VIEW codetype;
DROP VIEW responsible;
DROP VIEW application;

CREATE VIEW codevalue (id, value, codetype_id, name, creator, created) AS
SELECT concat_ws(':', codetyp_nr, wert),
       wert,
       codetyp_nr,
       name,
       erfasser,
       erfassungszeit
FROM tbti_codewert
WHERE codetyp_nr <> 0;

CREATE VIEW codetype (id, name, responsible_id, creator, created) AS
SELECT lauf_nr,
       name,
       zustaendigkeit_nr,
       erfasser,
       erfassungszeit
FROM tbti_codetyp;

CREATE VIEW responsible (id, projectName, package, prefix, email, creator, created) AS
SELECT zustaendigkeit_nr,
       projektname,
       package,
       codetyppraefix,
       zustaendigkeit,
       erfasser,
       erfassungszeit
FROM tbti_zustaendig;

CREATE VIEW application (id, name, package, creator, created) AS
SELECT lauf_nr,
       name,
       appackage,
       erfasser,
       erfassungszeit
FROM tbti_applikation;
