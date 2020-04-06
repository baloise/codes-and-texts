package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeValue;
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
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A form for editing a single codeValue.
 */
public class CodeValueForm extends Div {

    private final VerticalLayout content;
    private final TextField id;
    private final TextField name;
    private final TextField value;
    private final TextField creator;

    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final CodeValueViewLogic viewLogic;
    private final Binder<CodeValue> binder;
    private CodeValue currentCodeValue;

    private static class CodeValueValueConverter extends StringToLongConverter {

        public CodeValueValueConverter() {
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
    public CodeValueForm(CodeValueViewLogic sampleCrudLogic) {
        setClassName("codeValue-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("codeValue-form-content");
        add(content);

        viewLogic = sampleCrudLogic;
        id = new TextField("id");
        id.setWidth("100%");
        id.setReadOnly(true);
        id.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(id);

        name = new TextField("name");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(name);

        value = new TextField("value");
        value.setReadOnly(true);
        value.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(value);

        creator = new TextField("creator");
        creator.setReadOnly(true);
        creator.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        content.add(creator);

        binder = new BeanValidationBinder<>(CodeValue.class);
        binder.forField(value).withConverter(new CodeValueValueConverter() )
                .bind("value");
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
            if (currentCodeValue != null
                    && binder.writeBeanIfValid(currentCodeValue)) {
                viewLogic.saveCodeValue(currentCodeValue);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editCodeValue(currentCodeValue));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelCodeValue());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelCodeValue())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentCodeValue != null) {
                viewLogic.deleteCodeValue(currentCodeValue);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editCodeValue(CodeValue codeValue) {
        if (codeValue == null) {
            codeValue = new CodeValue();
        }
        delete.setVisible(!codeValue.isNewCodeValue());
        currentCodeValue = codeValue;
        binder.readBean(codeValue);
    }
}
