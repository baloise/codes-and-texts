CREATE VIEW CodeTyp (id, name, creator, created) As
Select lauf_nr as id, erfassungszeit as created, RTRIM(erfasser) as creator, RTRIM(name)
From tbti_codetyp;

