DROP VIEW IF EXISTS application;
CREATE VIEW application(id, name, package, creator, created) AS
SELECT lauf_nr        AS id,
       name,
       appackage      AS package,
       erfasser       AS creator,
       erfassungszeit AS created
FROM tbti_applikation;

ALTER TABLE application
    OWNER TO cat;

