DROP VIEW IF EXISTS codestyle;
CREATE VIEW codestyle(id, codetype_id, style_id, name, creator, created) AS
SELECT concat_ws(':'::text, codetyp_nr, stil_nr) AS id,
       codetyp_nr                                AS codetype_id,
       stil_nr                                   AS style_id,
       name,
       erfasser                                  AS creator,
       erfassungszeit                            AS created
FROM tbti_codetxtstil;

ALTER TABLE codestyle
    OWNER TO cat;

