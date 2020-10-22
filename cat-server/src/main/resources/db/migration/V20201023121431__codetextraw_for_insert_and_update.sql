create view codetextraw (type_id, value, text_nr, stil_nr, creator, created) As
SELECT ct.codetyp_nr     AS type_id,
       ct.codewert       AS value,
       ct.text_nr        AS text_nr,
       ct.stil_nr        AS stil_nr,
       RTRIM(ct.erfasser)       AS creator,
       ct.erfassungszeit AS created
FROM tbti_codetext ct
WHERE stil_nr = 1
ORDER BY ct.codetyp_nr, ct.codewert;