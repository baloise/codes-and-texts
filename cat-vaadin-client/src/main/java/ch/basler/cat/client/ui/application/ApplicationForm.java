package ch.basler.cat.client.ui.application;

import ch.basler.cat.client.backend.data.Application;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A form for editing a single application.
 */
public class ApplicationForm extends Div {

    private final VerticalLayout content;
    private final TextField id;
    private final TextField name;
    private final TextField creator;
    private final TextField packageName;

    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final ApplicationViewLogic viewLogic;
    private final Binder<Application> binder;
    private Application currentApplication;

    private static class ApplicationIdConverter extends StringToLongConverter {

        public ApplicationIdConverter() {
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
    public ApplicationForm(ApplicationViewLogic sampleCrudLogic) {
        setClassName("application-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("application-form-content");
        add(content);

        viewLogic = sampleCrudLogic;
        id = new TextField("id");
        id.setWidth("100%");
        id.setReadOnly(true);
        id.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(id);

        name = new TextField("Application name");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(name);

        packageName = new TextField("package.name");
        packageName.setRequired(true);
        packageName.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(packageName);

        creator = new TextField("Creator");
        creator.setReadOnly(true);
        creator.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(creator);

        binder = new BeanValidationBinder<>(Application.class);
        binder.forField(id).withConverter(new ApplicationIdConverter() )
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
            if (currentApplication != null
                    && binder.writeBeanIfValid(currentApplication)) {
                viewLogic.saveApplication(currentApplication);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editApplication(currentApplication));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelApplication());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelApplication())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentApplication != null) {
                viewLogic.deleteApplication(currentApplication);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editApplication(Application application) {
        if (application == null) {
            application = new Application();
        }
        delete.setVisible(!application.isNewApplication());
        currentApplication = application;
        binder.readBean(application);
    }
}
