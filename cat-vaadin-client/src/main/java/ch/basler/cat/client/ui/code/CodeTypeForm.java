package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.common.converter.UpperCaseNoSpacesConverter;
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
 * A form for editing a single codeType.
 */
public class CodeTypeForm extends Div {

    private final VerticalLayout content;
    private final TextField id;
    private final TextField name;
    private final TextField creator;

    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final CodeTypeViewLogic viewLogic;
    private final Binder<CodeType> binder;
    private CodeType currentCodeType;

    private static class CodeTypeIdConverter extends StringToLongConverter {

        public CodeTypeIdConverter() {
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

    public CodeTypeForm(CodeTypeViewLogic sampleCrudLogic) {
        setClassName("codeType-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("codeType-form-content");
        add(content);

        viewLogic = sampleCrudLogic;
        id = new TextField("id");
        id.setWidth("100%");
        id.setReadOnly(true);
        id.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(id);

        name = new TextField("CodeType name");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);

        // name.setClassName("uppercase");
        content.add(name);

        creator = new TextField("Creator");
        creator.setReadOnly(true);
        creator.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        content.add(creator);

        binder = new BeanValidationBinder<>(CodeType.class);

        binder.forField(name).withConverter(new UpperCaseNoSpacesConverter()).bind("name");
        binder.forField(id).withConverter(new CodeTypeIdConverter()).bind("id");
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
            if (currentCodeType != null
                    && binder.writeBeanIfValid(currentCodeType)) {
                viewLogic.saveCodeType(currentCodeType);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editCodeType(currentCodeType));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelCodeType());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelCodeType())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentCodeType != null) {
                viewLogic.deleteCodeType(currentCodeType);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editCodeType(CodeType codeType) {
        if (codeType == null) {
            codeType = new CodeType();
        }
        delete.setVisible(!codeType.isNewCodeType());
        currentCodeType = codeType;
        binder.readBean(codeType);
    }
}
