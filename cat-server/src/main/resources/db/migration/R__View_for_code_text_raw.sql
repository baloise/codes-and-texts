DROP VIEW IF EXISTS codetextraw;
CREATE VIEW codetextraw(type_id, value, text_nr, stil_nr, creator, created) AS
SELECT ct.codetyp_nr     AS type_id,
       ct.codewert       AS value,
       ct.text_nr,
       ct.stil_nr,
       ct.erfasser       AS creator,
       ct.erfassungszeit AS created
FROM tbti_codetext ct
WHERE ct.stil_nr = 1
ORDER BY ct.codetyp_nr, ct.codewert;

ALTER TABLE codetextraw
    OWNER TO cat;
