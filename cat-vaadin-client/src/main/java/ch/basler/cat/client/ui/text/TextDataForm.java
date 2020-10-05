package ch.basler.cat.client.ui.text;

import ch.basler.cat.client.backend.data.TextData;
import ch.basler.cat.client.backend.data.TextType;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A form for editing a single Text.
 */
public class TextDataForm extends Div {

    private final VerticalLayout content;
    private final TextField id;
    private final TextField type;
    private final TextField textD;
    private final TextField textF;
    private final TextField textI;
    private final TextField textE;
    private final TextField creator;

    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final TextDataViewLogic viewLogic;
    private final Binder<TextData> binder;
    private TextData currentTextData;

    private static class TextIdConverter extends StringToLongConverter {

        public TextIdConverter() {
            super(Long.valueOf(0), "Could not convert value to " + Long.class.getName()
                    + ".");
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

    private static class TextTypeConverter implements Converter<String, Long> {
        @Override
        public Result<Long> convertToModel(String textTypeName, ValueContext context) {
            if (textTypeName.isEmpty()) {
                return Result.ok(null);
            }
            return Result.ok(TextType.valueOf(textTypeName).getValue());
        }

        @Override
        public String convertToPresentation(Long value, ValueContext context) {
            return TextType.of(value).name();
        }
    }

    public TextDataForm(TextDataViewLogic sampleCrudLogic) {
        setClassName("text-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("text-form-content");
        add(content);

        viewLogic = sampleCrudLogic;
        id = new TextField("id");
        id.setWidth("100%");
        id.setReadOnly(true);
        id.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(id);

        type = new TextField("type");
        type.setRequired(true);
        type.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(type);

        textD = new TextField("DE");
        textD.setRequired(true);
        textD.setValueChangeMode(ValueChangeMode.EAGER);
        textD.setWidth("100%");
        content.add(textD);

        textF = new TextField("FR");
        textF.setRequired(true);
        textF.setValueChangeMode(ValueChangeMode.EAGER);
        textF.setWidth("100%");
        content.add(textF);

        textI = new TextField("IT");
        textI.setRequired(true);
        textI.setValueChangeMode(ValueChangeMode.EAGER);
        textI.setWidth("100%");
        content.add(textI);

        textE = new TextField("EN");
        textE.setRequired(true);
        textE.setValueChangeMode(ValueChangeMode.EAGER);
        textE.setWidth("100%");
        content.add(textE);

        creator = new TextField("Creator");
        creator.setReadOnly(true);
        creator.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        content.add(creator);

        binder = new BeanValidationBinder<>(TextData.class);
        binder.forField(id).withConverter(new TextIdConverter() )
                .bind("id");
        binder.forField(type).withConverter(new TextTypeConverter())
                .bind("type");
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
            if (currentTextData != null
                    && binder.writeBeanIfValid(currentTextData)) {
                viewLogic.saveText(currentTextData);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editText(currentTextData));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelText());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelText())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentTextData != null) {
                viewLogic.deleteText(currentTextData);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editText(TextData TextData) {
        if (TextData == null) {
            TextData = new TextData();
        }
        delete.setVisible(!TextData.isNewText());
        currentTextData = TextData;
        binder.readBean(TextData);
    }
}
