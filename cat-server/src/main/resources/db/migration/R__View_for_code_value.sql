DROP VIEW IF EXISTS codevalue;
CREATE VIEW codevalue(id, value, codetype_id, name, creator, created) AS
SELECT concat_ws(':'::text, tbti_codewert.codetyp_nr, tbti_codewert.wert) AS id,
       tbti_codewert.wert                                                 AS value,
       tbti_codewert.codetyp_nr                                           AS codetype_id,
       tbti_codewert.name,
       tbti_codewert.erfasser                                             AS creator,
       tbti_codewert.erfassungszeit                                       AS created
FROM tbti_codewert
WHERE tbti_codewert.codetyp_nr <> 0;

ALTER TABLE codevalue
    OWNER TO cat;
