DROP VIEW IF EXISTS domain;
CREATE VIEW domain(id, projectname, package, prefix, email, creator, created) AS
SELECT tbti_zustaendig.zustaendigkeit_nr AS id,
       tbti_zustaendig.projektname       AS projectname,
       tbti_zustaendig.package           AS package,
       tbti_zustaendig.codetyppraefix    AS prefix,
       tbti_zustaendig.zustaendigkeit    AS email,
       tbti_zustaendig.erfasser          AS creator,
       tbti_zustaendig.erfassungszeit    AS created
FROM tbti_zustaendig;

ALTER TABLE domain
    OWNER TO cat;
