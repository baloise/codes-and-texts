CREATE VIEW Text (id, type, text_d, text_f, text_i, text_e, creator, created) As
SELECT t.lauf_nr, t.typ, t.wert_d, t.wert_f, t.wert_i, t.wert_e, t.erfasser, t.erfassungszeit
FROM tbti_text AS t
ORDER BY t.lauf_nr;