package ch.basler.cat.client.backend.mock;

import ch.basler.cat.client.backend.data.*;

import java.math.BigDecimal;
import java.util.*;

public class MockDataGenerator {
    private static int nextCategoryId = 1;
    private static int nextProductId = 1;
    private static int nextCodeType = 1;
    private static final Random random = new Random(1);


    private static final String applicationNames[] = new String[]{
            "PARSYS", "PABLO", "BALSY", "BOLERO", "BIA"};

    private static final String responsibleNames[] = new String[]{
            "Sales", "KL Offerten", "EL Projekte"};

    private static String[] nations = new String[]{
            "OESTERREICH",
            "JEMEN_DEMOK_VOLKSREP_DEPRECATED",
            "AFGHANISTAN",
            "ALBANIEN",
            "ANDORRA",
            "AUSTRALIEN",
            "BELGIEN",
            "BANGLADESCH",
            "BARBADOS",
            "BULGARIEN",
            "BELIZE",
            "BRASILIEN",
            "BAHREIN",
            "BRUNEI_DARUSSALAM",
            "BAHAMAS",
            "BIRMA_DEPRECATED",
            "KUBA",
            "KANADA",
            "SCHWEIZ",
            "ELFENBEINKUESTE",
            "SRI_LANKA_CEYLON",
            "KOLUMBIEN",
            "COSTA_RICA",
            "TSCHECHOSLOWAKEI_DEPRECATED",
            "ZYPERN",
            "DEUTSCHLAND",
            "DAENEMARK",
            "DOMINIKANISCHE_REPUBLIK",
            "BENIN_DAHOMEY",
            "ALGERIEN",
            "SPANIEN",
            "KENIA",
            "TANSANIA",
            "UGANDA",
            "ECUADOR",
            "EL_SALVADOR",
            "AEGYPTEN",
            "AETHIOPIEN",
            "FRANKREICH",
            "FIDSCHI",
            "LIECHTENSTEIN",
            "FAEROER_INSELN",
            "GROSSBRITANNIEN",
            "ALDERNEY",
            "GUERNSEY",
            "JERSEY",
            "GIBRALTAR",
            "GUATEMALA",
            "GHANA",
            "GRIECHENLAND",
            "GUYANA",
            "UNGARN",
            "HONGKONG",
            "ITALIEN",
            "ISRAEL",
            "INDIEN",
            "IRAN",
            "IRLAND",
            "IRAK",
            "ISLAND",
            "JAPAN",
            "JAMAIKA",
            "JORDANIEN",
            "KAMBODSCHA",
            "KUWAIT",
            "LUXEMBURG",
            "LAOS",
            "LIBYEN",
            "LESOTHO",
            "MALTA",
            "MAROKKO",
            "MALAYSIA",
            "MONACO",
            "MEXIKO",
            "MAURITIUS",
            "MALAWI",
            "NORWEGEN",
            "NIEDERL_ANTILLEN",
            "NICARAGUA",
            "NIEDERLANDE",
            "NEUSEELAND",
            "PORTUGAL",
            "PANAMA",
            "PAKISTAN",
            "PERU",
            "POLEN",
            "PARAGUAY",
            "ARGENTINIEN",
            "BOTSWANA",
            "TAIWAN",
            "ZENTRALAFRIK_REPUBLIK"};

    private static String[] kantons = new String[]{
            "ZUERICH",
            "BERN",
            "LUZERN",
            "URI",
            "SCHWYZ",
            "OBWALDEN",
            "NIDWALDEN",
            "GLARUS",
            "ZUG",
            "FRIBOURG",
            "SOLOTHURN",
            "BASEL_STADT",
            "BASEL_LAND",
            "SCHAFFHAUSEN",
            "APPENZELL_AR",
            "APPENZELL_AI",
            "ST_GALLEN",
            "GRAUBUENDEN",
            "AARGAU",
            "THURGAU",
            "TESSIN",
            "WAADT",
            "WALLIS",
            "NEUENBURG",
            "GENF",
            "JURA"};


    private static String[] klVorsogeModelle = new String[]{ "VOLLVERSICHERUNG", "PERSPECTIVA_RELAX"};
    private static String[] elTarife = new String[]{ "G", "E", "TT", "TTD","R" ,"K"};



    public static List<Application> createApplications() {
        List<Application> applications = new ArrayList<Application>();
        for (int i = 0; i < applicationNames.length; i++) {
            Application a = new Application();
            a.setId(i);
            a.setName(applicationNames[i]);
            a.setCreator("B00000" + i);
            a.setPackageName("ch.basler." + applicationNames[i].toLowerCase());
            applications.add(a);
        }

        return applications;
    }

    public static List<Responsible> createResponsibles() {
        List<Responsible> responsibles = new ArrayList<Responsible>();
        for (int i = 0; i < responsibleNames.length; i++) {
            Responsible r = new Responsible();
            r.setId(i);
            r.setProjectName(responsibleNames[i]);
            r.setPrefix(responsibleNames[i].split(" ")[0].toUpperCase());
            String creator = "B00000" + i;
            r.setCreator(creator);
            r.setPackageName("ch.basler." + responsibleNames[i].split(" ")[0].toLowerCase());
            r.setEmail(creator + "@baloise.com");
            responsibles.add(r);
        }

        return responsibles;
    }

    public static List<CodeType> createCodeTypes() {
        List<CodeType> result = new ArrayList<>();
        result.add(getCodeTypeNation());
        result.add(getCodeTypeKanton());
        result.add(getCodeTypeVorsorgeModell());
        result.add(getCodeTypeTarif());
        return result;
    }

    private static CodeType getCodeTypeTarif() {
        CodeType codeTypeElTarif = new CodeType(createResponsibles().get(2));
        codeTypeElTarif.setId(nextCodeType++);
        codeTypeElTarif.setCreator("B006969");
        codeTypeElTarif.setName("EL_TARIF");
        codeTypeElTarif.setCodeStyles(createCodeStyles(codeTypeElTarif, new String[]{"STANDARD", "SHORT"}));
        codeTypeElTarif.setCodeValues(createCodeValuesFor(codeTypeElTarif, elTarife));
        return codeTypeElTarif;
    }

    private static CodeType getCodeTypeVorsorgeModell() {
        CodeType codeTypeVorsorgeModell = new CodeType(createResponsibles().get(1));
        codeTypeVorsorgeModell.setId(nextCodeType++);
        codeTypeVorsorgeModell.setCreator("B006969");
        codeTypeVorsorgeModell.setName("VORSORGEMODELL");
        codeTypeVorsorgeModell.setCodeStyles(createCodeStyles(codeTypeVorsorgeModell, new String[]{"STANDARD", "SHORT"}));
        codeTypeVorsorgeModell.setCodeValues(createCodeValuesFor(codeTypeVorsorgeModell, klVorsogeModelle));
        return codeTypeVorsorgeModell;
    }

    private static CodeType getCodeTypeKanton() {
        CodeType codeTypeKanton = new CodeType(createResponsibles().get(0));
        codeTypeKanton.setId(nextCodeType++);
        codeTypeKanton.setCreator("B006969");
        codeTypeKanton.setName("KANTON");
        codeTypeKanton.setCodeStyles(createCodeStyles(codeTypeKanton, new String[]{"STANDARD", "SHORT"}));
        codeTypeKanton.setCodeValues(createCodeValuesFor(codeTypeKanton, kantons));
        return codeTypeKanton;
    }

    private static CodeType getCodeTypeNation() {
        CodeType codeTypeNation = new CodeType(createResponsibles().get(0));
        codeTypeNation.setId(nextCodeType++);
        codeTypeNation.setCreator("B006969");
        codeTypeNation.setName("NATION");
        codeTypeNation.setCodeStyles(createCodeStyles(codeTypeNation, new String[]{"STANDARD"}));
        codeTypeNation.setCodeValues(createCodeValuesFor(codeTypeNation, nations));
        return codeTypeNation;
    }

    public static List<CodeStyle> createCodeStyles(CodeType ct, String[] names) {
        List<CodeStyle> result = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            CodeStyle codeStyle = new CodeStyle();
            codeStyle.setId(ct.getId() + ":" + i);
            codeStyle.setStyleId(i);
            codeStyle.setName(names[i]);
            codeStyle.setCodeType(ct);
            codeStyle.setCreator("B006969");
            result.add(codeStyle);
        }

        return result;
    }

    private static List<CodeValue> createCodeValuesFor(CodeType ct, String[] names) {
        List<CodeValue> result = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            CodeValue codeValue = new CodeValue();
            codeValue.setId(ct.getId() + ":" + i);
            codeValue.setValue(i);
            codeValue.setName(names[i]);
            codeValue.setCodeType(ct);
            codeValue.setCreator("B006969");
            result.add(codeValue);
        }

        return result;
    }
}
