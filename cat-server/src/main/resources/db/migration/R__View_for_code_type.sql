DROP VIEW IF EXISTS codetype;
CREATE VIEW codetype(id, name, domain_id, creator, created) AS
SELECT tbti_codetyp.lauf_nr           AS id,
       tbti_codetyp.name,
       tbti_codetyp.zustaendigkeit_nr AS domain_id,
       tbti_codetyp.erfasser          AS creator,
       tbti_codetyp.erfassungszeit    AS created
FROM tbti_codetyp;

ALTER TABLE codetype
    OWNER TO cat;


