package ch.basler.cat.client.ui.responsible;

import ch.basler.cat.client.backend.data.Responsible;
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
 * A form for editing a single responsible.
 */
public class ResponsibleForm extends Div {

    private final VerticalLayout content;
    private final TextField id;
    private final TextField projectName;
    private final TextField prefix;
    private final TextField email;
    private final TextField packageName;
    private final TextField creator;

    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final ResponsibleViewLogic viewLogic;
    private final Binder<Responsible> binder;
    private Responsible currentResponsible;

    private static class ResponsibleIdConverter extends StringToLongConverter {

        public ResponsibleIdConverter() {
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
    public ResponsibleForm(ResponsibleViewLogic sampleCrudLogic) {
        setClassName("responsible-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("responsible-form-content");
        add(content);

        viewLogic = sampleCrudLogic;
        id = new TextField("id");
        id.setWidth("100%");
        id.setReadOnly(true);
        id.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(id);

        projectName = new TextField("Project name");
        projectName.setRequired(true);
        projectName.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(projectName);

        prefix = new TextField("Prefix (for codes)");
        prefix.setRequired(true);
        prefix.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(prefix);

        packageName = new TextField("package.name");
        packageName.setRequired(true);
        prefix.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(packageName);

        email = new TextField("e-mail");
        email.setRequired(true);
        email.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(email);

        creator = new TextField("Creator");
        creator.setReadOnly(true);
        creator.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        content.add(creator);

        binder = new BeanValidationBinder<>(Responsible.class);
        binder.forField(id).withConverter(new ResponsibleIdConverter() )
                .bind("id");
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
            if (currentResponsible != null
                    && binder.writeBeanIfValid(currentResponsible)) {
                viewLogic.saveResponsible(currentResponsible);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editResponsible(currentResponsible));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelResponsible());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelResponsible())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentResponsible != null) {
                viewLogic.deleteResponsible(currentResponsible);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editResponsible(Responsible responsible) {
        if (responsible == null) {
            responsible = new Responsible();
        }
        delete.setVisible(!responsible.isNewResponsible());
        currentResponsible = responsible;
        binder.readBean(responsible);
    }
}
