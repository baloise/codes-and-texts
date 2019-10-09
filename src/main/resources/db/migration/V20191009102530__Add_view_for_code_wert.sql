CREATE VIEW CodeValue (codetyp, value, name, creator, created) As
Select codetyp_nr as codetyp, erfassungszeit as created, RTRIM(erfasser) as creator, RTRIM(name), wert as value
From tbti_codewert;
