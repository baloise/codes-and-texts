package ch.basler.cat.examples.codevalues;

import ch.basler.cat.api.service.CatService;
import ch.basler.cat.api.service.json.JsonFileService;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CodeValueFromFileExample {

    private static final Path KANTON_PATH = Paths.get( "src", "test", "resources", "codekanton.json");

    private CatService<CodeKanton> kantonService;

    @Before
    public void setUp() {
        System.out.println("Using basedir " + KANTON_PATH.toAbsolutePath());
        kantonService = new JsonFileService<>(KANTON_PATH, CodeKanton[].class);
    }

    @Test
    public void readCodeValues() {
        List<CodeKanton> kantonList = new ArrayList<>(kantonService.getData());

        assertEquals(29, kantonList.size());    // That seems odd - why 29? (see below)

        kantonList.remove(CodeKanton.FL);   // Liechtenstein
        kantonList.remove(CodeKanton.EB);   // Büsingen
        kantonList.remove(CodeKanton.EC);   // Campione

        assertEquals(26, kantonList.size());    // That's the official number of cantons
    }

    @Test
    public void checkCodeValues() {
        Collection<CodeKanton> kantonList = kantonService.getData();
        assertEquals(26 + 3, kantonList.size());

        assertNull("unexpected name", CodeKanton.BS.getName());
        assertEquals("BASEL_STADT", find(kantonList, CodeKanton.BS).getName());
        assertNull("unexpected name", CodeKanton.BL.getName());
        assertEquals("BASEL_LAND", find(kantonList, CodeKanton.BL).getName());
    }

    private CodeKanton find(Collection<CodeKanton> kantonList, CodeKanton kanton) {
        return kantonList.stream().filter(kanton::equals).findFirst().orElse(null);
    }
}
