CREATE VIEW codevalue (id, value, codetype_id, name, creator, created) AS
-- SELECT DENSE_RANK() OVER (order by codetyp_nr, wert) as id, -- will enumerate - not good for deletes
SELECT concat_ws(':', codetyp_nr, wert),
       wert,
       codetyp_nr,
       RTRIM(name),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_codewert;

CREATE VIEW responsible (id, projectName, package, prefix, email, creator, created) AS
SELECT zustaendigkeit_nr,
       RTRIM(projektname),
       RTRIM(package),
       RTRIM(codetyppraefix),
       RTRIM(zustaendigkeit),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_zustaendig;
