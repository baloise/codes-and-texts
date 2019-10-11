package ch.basler.cat.generator;

import ch.basler.cat.generator.common.FileUtil;
import ch.basler.cat.generator.model.CodeType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CodesParserTest {

    @Test
    public void exportCodes() throws Exception {
        String json  = FileUtil.read("src/test/resources", "test-codes.json");
        List<CodeType> codeTypes = new CodesParser().parse(json);
        assertEquals(24, codeTypes.size());
    }
}
