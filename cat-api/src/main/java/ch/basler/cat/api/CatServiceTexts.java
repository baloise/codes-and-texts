package ch.basler.cat.api;

import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.base.Language;
import ch.basler.cat.api.service.CatService;
import ch.basler.cat.api.text.*;
import ch.basler.cat.api.base.TextItem;
import ch.basler.cat.api.base.TextItemProvider;

import java.util.Objects;

public class CatServiceTexts implements TextAPI {

    private final TextItemProvider<CodeText, CodeTextId> codeTextProvider;
    private final TextItemProvider<LabelText, String> labelTextProvider;
    private final TextItemProvider<MessageText, Number> messageTextProvider;

    public CatServiceTexts(CatService<CodeText> codeTextService, CatService<LabelText> labelTextService, CatService<MessageText> messageTextService) {
        codeTextProvider = (codeTextService == null) ? null : new TextItemProvider<>(codeTextService);
        labelTextProvider = (labelTextService == null) ? null : new TextItemProvider<>(labelTextService);
        messageTextProvider = (messageTextService == null) ? null : new TextItemProvider<>(messageTextService);
    }

    @Override
    public String getTextForCode(CodeValue codeValue, Language language) {
        Objects.requireNonNull(codeTextProvider, "codeTextProvider is not defined");
        return getText(codeTextProvider, CodeTextId.fromCodeValue(codeValue), language);
    }

    @Override
    public String getTextForLabel(String labelName, Language language) {
        Objects.requireNonNull(labelTextProvider, "labelTextProvider is not defined");
        return getText(labelTextProvider, labelName, language);
    }

    @Override
    public String getTextForMessage(Number messageId, Language language) {
        Objects.requireNonNull(messageTextProvider, "messageTextProvider is not defined");
        return getText(messageTextProvider, messageId, language);
    }

    private <T,I> String getText(TextItemProvider<? extends TextItem<I>, I> provider, I identifier, Language language) {
        return provider.getItem(identifier).map(t -> t.getText(language)).orElse(null);
    }


}
