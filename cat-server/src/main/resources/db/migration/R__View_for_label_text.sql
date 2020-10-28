DROP VIEW IF EXISTS labeltext;
CREATE VIEW labeltext (id, application_id, name, text_nr, text_d, text_f, text_i, text_e, creator, created) AS
SELECT concat_ws(':'::text, a.lauf_nr, t.lauf_nr) AS id,
       a.lauf_nr                                  AS application_id,
       e.name,
       e.text_nr,
       t.wert_d                                   AS text_d,
       t.wert_f                                   AS text_f,
       t.wert_i                                   AS text_i,
       t.wert_e                                   AS text_e,
       e.erfasser                                 AS creator,
       e.erfassungszeit                           AS created
FROM tbti_element e
         JOIN tbti_applikation a ON e.appl_nr = a.lauf_nr AND e.typ = 1
         JOIN tbti_text t ON t.lauf_nr = e.text_nr
ORDER BY a.lauf_nr, t.lauf_nr;

ALTER TABLE labeltext
    OWNER TO cat;

