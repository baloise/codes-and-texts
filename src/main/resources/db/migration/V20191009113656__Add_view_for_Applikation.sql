CREATE VIEW Application (id , name, creator, created, package) As
Select lauf_nr AS id, erfassungszeit as created, RTRIM(erfasser) as creator, RTRIM(name), appackage as package
From tbti_applikation;