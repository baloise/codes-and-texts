package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeText;
import ch.basler.cat.client.common.converter.UpperCaseNoSpacesConverter;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A form for editing a single codeText.
 */
public class CodeTextForm extends Div {

    private final VerticalLayout content;
    private final TextField textId;

    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final CodeTextViewLogic viewLogic;
    private final Binder<CodeText> binder;
    private CodeText currentCodeText;

    private static class StringToLongValueConverter extends StringToLongConverter {

        public StringToLongValueConverter() {
            super(null, "Could not convert value to " + Long.class.getName()
                    + ".");
        }


        @Override
        public String convertToPresentation(Long value, ValueContext context) {
            if (value == null) {
                return "";
            }
            return super.convertToPresentation(value, context);
        }

        @Override
        protected NumberFormat getFormat(Locale locale) {
            // Do not use a thousands separator, as HTML5 input type
            // number expects a fixed wire/DOM number format regardless
            // of how the browser presents it to the user (which could
            // depend on the browser locale).
            final DecimalFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(0);
            format.setDecimalSeparatorAlwaysShown(false);
            format.setParseIntegerOnly(true);
            format.setGroupingUsed(false);
            return format;
        }
    }
    public CodeTextForm(CodeTextViewLogic sampleCrudLogic) {
        setClassName("codeText-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("codeText-form-content");
        add(content);

        viewLogic = sampleCrudLogic;

        textId = new TextField("assigned-text-id");
        textId.setRequired(true);
        textId.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(textId);


        binder = new BeanValidationBinder<>(CodeText.class);
        binder.forField(textId).withConverter(new StringToLongValueConverter()).bind("textId");
        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            final boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });
        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (currentCodeText != null
                    && binder.writeBeanIfValid(currentCodeText)) {
                viewLogic.saveCodeText(currentCodeText);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editCodeText(currentCodeText));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelCodeText());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelCodeText())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentCodeText != null) {
                viewLogic.deleteCodeText(currentCodeText);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editCodeText(CodeText codeText) {
        if (codeText == null) {
            codeText = new CodeText();
        }
        delete.setVisible(!codeText.isNewCodeText());
        currentCodeText = codeText;
        binder.readBean(codeText);
    }
}
