// generated
package ch.basler.kl.pablo.codes;

import ch.basler.text.java.code.Code;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CodeDokTyp extends Code  {

  private static final short CODETYP_DOK_TYP = 1237;

  private static final long serialVersionUID = 1L;

  private static final CodeDokTyp[] codeWertArray = new CodeDokTyp[9];
  private static final Map<String, Code> codeWertNameMap = new HashMap<String, Code>(8);

  public static final CodeDokTyp CODE_NULL;

  public static final CodeDokTyp OFFERTE;
  public static final CodeDokTyp OFFERTE_PLANBESCHRIEB;
  public static final CodeDokTyp ORIENTIERUNGSBLATT;
  public static final CodeDokTyp BLD_VEREINBARUNG;
  public static final CodeDokTyp BEKANNTGABEKASSENVORSTAND;
  public static final CodeDokTyp ANMELDESCHEINE;
  public static final CodeDokTyp KASSENVORSTANDSBESCHLUSS;
  public static final CodeDokTyp ANSCHLUSSVERTRAG;

  static {
    CODE_NULL = new CodeDokTyp(CODE_NULL_NAME, CODE_NULL_VALUE);
    OFFERTE = new CodeDokTyp("OFFERTE", (short)1);
    OFFERTE_PLANBESCHRIEB = new CodeDokTyp("OFFERTE_PLANBESCHRIEB", (short)2);
    ORIENTIERUNGSBLATT = new CodeDokTyp("ORIENTIERUNGSBLATT", (short)3);
    BLD_VEREINBARUNG = new CodeDokTyp("BLD_VEREINBARUNG", (short)4);
    BEKANNTGABEKASSENVORSTAND = new CodeDokTyp("BEKANNTGABEKASSENVORSTAND", (short)5);
    ANMELDESCHEINE = new CodeDokTyp("ANMELDESCHEINE", (short)6);
    KASSENVORSTANDSBESCHLUSS = new CodeDokTyp("KASSENVORSTANDSBESCHLUSS", (short)7);
    ANSCHLUSSVERTRAG = new CodeDokTyp("ANSCHLUSSVERTRAG", (short)8);

    codeWertNameMap.put(CODE_NULL.getCodewertName(), CODE_NULL);
    codeWertNameMap.put(OFFERTE.getCodewertName(), OFFERTE);
    codeWertNameMap.put(OFFERTE_PLANBESCHRIEB.getCodewertName(), OFFERTE_PLANBESCHRIEB);
    codeWertNameMap.put(ORIENTIERUNGSBLATT.getCodewertName(), ORIENTIERUNGSBLATT);
    codeWertNameMap.put(BLD_VEREINBARUNG.getCodewertName(), BLD_VEREINBARUNG);
    codeWertNameMap.put(BEKANNTGABEKASSENVORSTAND.getCodewertName(), BEKANNTGABEKASSENVORSTAND);
    codeWertNameMap.put(ANMELDESCHEINE.getCodewertName(), ANMELDESCHEINE);
    codeWertNameMap.put(KASSENVORSTANDSBESCHLUSS.getCodewertName(), KASSENVORSTANDSBESCHLUSS);
    codeWertNameMap.put(ANSCHLUSSVERTRAG.getCodewertName(), ANSCHLUSSVERTRAG);

    codeWertArray[CODE_NULL.getCodewert()] = CODE_NULL;
    codeWertArray[OFFERTE.getCodewert()] = OFFERTE;
    codeWertArray[OFFERTE_PLANBESCHRIEB.getCodewert()] = OFFERTE_PLANBESCHRIEB;
    codeWertArray[ORIENTIERUNGSBLATT.getCodewert()] = ORIENTIERUNGSBLATT;
    codeWertArray[BLD_VEREINBARUNG.getCodewert()] = BLD_VEREINBARUNG;
    codeWertArray[BEKANNTGABEKASSENVORSTAND.getCodewert()] = BEKANNTGABEKASSENVORSTAND;
    codeWertArray[ANMELDESCHEINE.getCodewert()] = ANMELDESCHEINE;
    codeWertArray[KASSENVORSTANDSBESCHLUSS.getCodewert()] = KASSENVORSTANDSBESCHLUSS;
    codeWertArray[ANSCHLUSSVERTRAG.getCodewert()] = ANSCHLUSSVERTRAG;
  }

  private CodeDokTyp(String aCodeWertName, short aCodeWert) {
    super(aCodeWertName, aCodeWert);
  }

  public short getCodetyp() {
    return CODETYP_DOK_TYP;
  }

  public static CodeDokTyp getCodeObject(short aCodeWert) {
    CodeDokTyp result = null;
    try {
      result = codeWertArray[aCodeWert];
    }
    catch(ArrayIndexOutOfBoundsException e) {}

    if(result == null) {
      result = new CodeDokTyp("CODEWERT_UNBEKANNT", aCodeWert);
    }
    return result;
  }

  public static CodeDokTyp getCodeObject(String aCodeWertName) {
    CodeDokTyp result = (CodeDokTyp)codeWertNameMap.get(aCodeWertName);
    return result;
  }

  public static boolean isUnknown(Code aCode) {
    boolean result = true;

    try {
      if(codeWertArray[aCode.getCodewert()].getCodewert() == aCode.getCodewert()) {
        result = false;
      }
    }
    catch(ArrayIndexOutOfBoundsException e) {}
    catch (NullPointerException e) {}

    return result;
  }

  public Iterator<Code> getCodewertIterator() {
    return codeWertNameMap.values().iterator();
  }
}
