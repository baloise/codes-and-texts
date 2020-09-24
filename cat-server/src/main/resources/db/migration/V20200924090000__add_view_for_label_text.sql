CREATE VIEW LabelText (id, application_id, name, text_nr, text_d, text_f, text_i, text_e, creator, created) As
SELECT concat_ws(':', a.lauf_nr, t.lauf_nr), a.lauf_nr, e.name, e.text_nr, t.wert_d, t.wert_f, t.wert_i, t.wert_e, e.erfasser, e.erfassungszeit
FROM tbti_element AS e
         INNER JOIN tbti_applikation AS a
                   ON e.appl_nr = a.lauf_nr AND e.typ = 1
         INNER JOIN tbti_text AS t
                   ON t.lauf_nr = e.text_nr
ORDER BY a.lauf_nr, t.lauf_nr;