package ch.basler.cat.examples.codevalues;

import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;

public class CodeKanton extends CodeValue {

    private static final CodeType CODE_TYPE_KANTON = new CodeType((short) 2);

    public static final CodeKanton BS = new CodeKanton((short) 12); // Basel-Stadt
    public static final CodeKanton BL = new CodeKanton((short) 13); // Basel-Landschaft

    // special handling
    public static final CodeKanton FL = new CodeKanton((short) 27); // Liechtenstein
    public static final CodeKanton EB = new CodeKanton((short) 13); // Enklave Büsingen
    public static final CodeKanton EC = new CodeKanton((short) 12); // Enklave Campione

    public CodeKanton(short value) {
        super(CODE_TYPE_KANTON, value);
    }

}
