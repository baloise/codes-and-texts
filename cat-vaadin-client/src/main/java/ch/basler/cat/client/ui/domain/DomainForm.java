package ch.basler.cat.client.ui.domain;

import ch.basler.cat.client.backend.data.Domain;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A form for editing a single domain.
 */
public class DomainForm extends Div {

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

    private final DomainViewLogic viewLogic;
    private final Binder<Domain> binder;
    private Domain currentDomain;

    private static class DomainIdConverter extends StringToLongConverter {

        public DomainIdConverter() {
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
    public DomainForm(DomainViewLogic sampleCrudLogic) {
        setClassName("domain-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("domain-form-content");
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

        binder = new Binder<>(Domain.class);
        binder.forField(id).withConverter(new DomainIdConverter() )
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
            if (currentDomain != null
                    && binder.writeBeanIfValid(currentDomain)) {
                viewLogic.saveDomain(currentDomain);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editDomain(currentDomain));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelDomain());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelDomain())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentDomain != null) {
                viewLogic.deleteDomain(currentDomain);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editDomain(Domain Domain) {
        if (Domain == null) {
            Domain = new Domain();
        }
        delete.setVisible(!Domain.isNewDomain());
        currentDomain = Domain;
        binder.readBean(Domain);
    }
}
