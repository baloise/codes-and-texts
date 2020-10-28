DROP VIEW IF EXISTS CodeText;
CREATE VIEW CodeText (id, type_id, value, name, text_nr, text_d, text_f, text_i, text_e, creator, created, domain) As
SELECT concat_ws(':', cw.codetyp_nr, cw.wert), cw.codetyp_nr, cw.wert, cw.name, ct.text_nr, t.wert_d, t.wert_f, t.wert_i, t.wert_e, ct.erfasser, ct.erfassungszeit, cy.zustaendigkeit_nr
FROM tbti_codetext AS ct
         INNER JOIN tbti_codewert AS cw
                   ON ct.codetyp_nr = cw.codetyp_nr AND ct.codewert = cw.wert
         INNER JOIN tbti_codetyp as cy
                   ON ct.codetyp_nr = cy.lauf_nr
         INNER JOIN tbti_text AS t
                   ON t.lauf_nr = ct.text_nr AND ct.stil_nr = 1 AND t.typ = 3
ORDER BY cw.codetyp_nr, cw.wert;
