package ch.basler.cat.api.examples;

import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;

public class SampleCodes {

    public static final CodeType CODE_TYPE_NATION = new CodeType((short) 1);

    public static final CodeValue NATION_SCHWEIZ = new CodeValue(CODE_TYPE_NATION, (short) 19);
    public static final CodeValue NATION_KANADA = new CodeValue(CODE_TYPE_NATION, (short) 18);

    public static final CodeType CODE_TYPE_BRANCHE = new CodeType((short) 186);

    public static final CodeValue BRANCHE_SACH = new CodeValue(CODE_TYPE_BRANCHE, (short) 1);
    public static final CodeValue BRANCHE_HAFT = new CodeValue(CODE_TYPE_BRANCHE, (short) 2);
    public static final CodeValue BRANCHE_LEBEN_PRIVAT = new CodeValue(CODE_TYPE_BRANCHE, (short) 3);

}
