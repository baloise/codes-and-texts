DROP VIEW codetype;

CREATE VIEW codetype (id, name, responsible_id, creator, created) AS
SELECT lauf_nr,
       RTRIM(name),
       zustaendigkeit_nr,
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_codetyp;
