package ch.basler.cat.generator.codes.export;

import ch.basler.cat.generator.codes.export.LegacyCodesGenerator;
import ch.basler.cat.generator.common.FileUtil;
import ch.basler.cat.generator.codes.model.CodeType;
import ch.basler.cat.generator.codes.model.CodeValue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LegacyCodesGeneratorTest {

    @Test
    public void generate() throws Exception {

        List<CodeType> codeTypes = new ArrayList<>();


        CodeType codeType = new CodeType("DOK_TYP", "ch.basler.kl.pablo", 1237);

        codeType.addCodeValue(new CodeValue("OFFERTE", 1));
        codeType.addCodeValue(new CodeValue("OFFERTE_PLANBESCHRIEB", 2));
        codeType.addCodeValue(new CodeValue("ORIENTIERUNGSBLATT", 3));
        codeType.addCodeValue(new CodeValue("BLD_VEREINBARUNG", 4));
        codeType.addCodeValue(new CodeValue("BEKANNTGABEKASSENVORSTAND", 5));
        codeType.addCodeValue(new CodeValue("ANMELDESCHEINE", 6));
        codeType.addCodeValue(new CodeValue("KASSENVORSTANDSBESCHLUSS", 7));
        codeType.addCodeValue(new CodeValue("ANSCHLUSSVERTRAG", 8));
        codeTypes.add(codeType);

        new LegacyCodesGenerator().generate(codeTypes);

        List<String> refLines = FileUtil.getLines("src/test/resources", "CodeDokTyp.java");
        List<String> genLines = FileUtil.getLines("gen", "CodeDokTyp.java");

        for (int lineIndex = 0;lineIndex < refLines.size();lineIndex++) {
            assertEquals("difference at line " + lineIndex ,
                    refLines.get(lineIndex).trim(), genLines.get(lineIndex).trim());
        }
    }
}