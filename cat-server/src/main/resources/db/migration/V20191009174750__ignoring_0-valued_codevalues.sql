DROP VIEW codevalue;

CREATE VIEW codevalue (id, value, codetype_id, name, creator, created) AS
-- SELECT DENSE_RANK() OVER (order by codetyp_nr, wert) as id, -- will enumerate - not good for deletes
SELECT concat_ws(':', codetyp_nr, wert),
       wert,
       codetyp_nr,
       RTRIM(name),
       RTRIM(erfasser),
       erfassungszeit
FROM tbti_codewert
WHERE codetyp_nr <> 0;
