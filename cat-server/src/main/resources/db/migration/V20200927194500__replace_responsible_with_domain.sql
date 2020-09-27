DROP VIEW codetype;

CREATE VIEW codetype (id, name, domain_id, creator, created) AS
SELECT lauf_nr,
       RTRIM(name),
       zustaendigkeit_nr,
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_codetyp;

DROP VIEW responsible;

CREATE VIEW domain (id, projectName, package, prefix, email, creator, created) AS
SELECT zustaendigkeit_nr,
       RTRIM(projektname),
       RTRIM(package),
       RTRIM(codetyppraefix),
       RTRIM(zustaendigkeit),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_zustaendig;
