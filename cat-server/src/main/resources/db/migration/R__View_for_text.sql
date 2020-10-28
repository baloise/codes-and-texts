DROP VIEW IF EXISTS text;
CREATE VIEW text(id, type, text_d, text_f, text_i, text_e, creator, created) AS
SELECT t.lauf_nr        AS id,
       t.typ            AS type,
       t.wert_d         AS text_d,
       t.wert_f         AS text_f,
       t.wert_i         AS text_i,
       t.wert_e         AS text_e,
       t.erfasser       AS creator,
       t.erfassungszeit AS created
FROM tbti_text t
ORDER BY t.lauf_nr;

ALTER TABLE text
    OWNER TO cat;

