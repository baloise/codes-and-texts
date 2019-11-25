CREATE VIEW CodeStyle (id, codetype_id, style_id, name, creator, created) As
Select concat_ws(':', codetyp_nr, stil_nr),  codetyp_nr as codetyp_id, stil_nr as style_id, name,  erfasser as creator, erfassungszeit as created
From tbti_codetxtstil;

