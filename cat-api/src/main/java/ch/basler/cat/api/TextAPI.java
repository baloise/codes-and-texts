package ch.basler.cat.api;

import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.base.Language;

public interface TextAPI {

    String getTextForCode(CodeValue codeValue, Language language);

    String getTextForLabel(String labelName, Language language);

    String getTextForMessage(Number messageId, Language language);

}
