package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import ch.basler.cat.client.backend.data.Responsible;
import ch.basler.cat.client.ui.MainLayout;
import ch.basler.cat.client.ui.responsible.ResponsibleDataProvider;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

/**
 * A view for performing create-read-update-delete operations on codeValues.
 * <p>
 * See also {@link CodeValueViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "CodeValue", layout = MainLayout.class)
public class CodeValueView extends HorizontalLayout implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "CodeValue";
    private final CodeValueGrid codeValueGrid;
    private final CodeValueForm form;
    private Select<Responsible> responsibleSelect;
    private Select<CodeType> codeTypeSelect;
    private CodeTypeDataProvider codeTypeDataProvider;

    private final CodeValueViewLogic viewLogic = new CodeValueViewLogic(this);
    private Button newCodeValue;
    private CodeValueDataProvider codeValueDataProvider;

    public CodeValueView() {
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        codeValueDataProvider = new CodeValueDataProvider(this.codeTypeSelect.getValue());
        codeValueGrid = new CodeValueGrid();
        codeValueGrid.setDataProvider(codeValueDataProvider);
        // Allows user to select a single row in the grid.
        codeValueGrid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        form = new CodeValueForm(viewLogic);

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(codeValueGrid);
        barAndGridLayout.setFlexGrow(1, codeValueGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(codeValueGrid);

        add(barAndGridLayout);
        add(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        responsibleSelect = new Select<>();
        responsibleSelect.setPlaceholder("Select responsible");
        responsibleSelect.setItems(new ResponsibleDataProvider().getItems());
        responsibleSelect.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                codeTypeSelect.setEnabled(true);
                codeTypeDataProvider.setFilter(changeEvent.getValue().getId());

                codeTypeSelect.setDataProvider(codeTypeDataProvider);

            }
        });

        codeTypeSelect = new Select<>();
        codeTypeSelect.setEnabled(false);
        codeTypeSelect.setPlaceholder("Select codeType");
        codeTypeDataProvider = new CodeTypeDataProvider();
        codeTypeSelect.setItems(codeTypeDataProvider.getItems());
        codeTypeSelect.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                codeValueDataProvider = new CodeValueDataProvider(this.codeTypeSelect.getValue());
                codeValueGrid.setDataProvider(codeValueDataProvider);
                newCodeValue.setEnabled(true);
                codeValueDataProvider.setFilter(changeEvent.getValue());
            }
        });
        newCodeValue = new Button("New codeValue");
        // Setting theme variant of new codeValueion button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newCodeValue.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newCodeValue.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newCodeValue.addClickListener(click -> viewLogic.newCodeValue());
        // A shortcut to click the new codeValue button by pressing ALT + N
        newCodeValue.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        newCodeValue.setEnabled(false);

        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(responsibleSelect);
        topLayout.add(codeTypeSelect);
        topLayout.add(newCodeValue);
        topLayout.setVerticalComponentAlignment(Alignment.START, codeTypeSelect);
        topLayout.expand(codeTypeSelect);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Shows a temporary popup notification to the user.
     *
     * @param msg
     * @see Notification#show(String)
     */
    public void showNotification(String msg) {
        Notification.show(msg);
    }

    /**
     * Enables/Disables the new codeValue button.
     *
     * @param enabled
     */
    public void setNewCodeValueEnabled(boolean enabled) {
        newCodeValue.setEnabled(enabled);
    }

    /**
     * Deselects the selected row in the grid.
     */
    public void clearSelection() {
        codeValueGrid.getSelectionModel().deselectAll();
    }

    /**
     * Selects a row
     *
     * @param row
     */
    public void selectRow(CodeValue row) {
        codeValueGrid.getSelectionModel().select(row);
    }

    /**
     * Updates a codeValue in the list of codeValues.
     *
     * @param codeValue
     */
    public void updateCodeValue(CodeValue codeValue) {
        codeValueDataProvider.save(codeValue);
        if (codeValue.isNewCodeValue()) {
            reloadFromServer();
        }
    }

    /**
     * Removes a codeValue from the list of codeValues.
     *
     * @param codeValue
     */
    public void removeCodeValue(CodeValue codeValue) {
        codeValueDataProvider.delete(codeValue);
        reloadFromServer();
    }

    private void reloadFromServer() {
        this.codeValueDataProvider = new CodeValueDataProvider(this.codeTypeSelect.getValue());
        this.codeValueGrid.setDataProvider(codeValueDataProvider);
    }

    /**
     * Displays user a form to edit a CodeValue.
     *
     * @param codeValue
     */
    public void editCodeValue(CodeValue codeValue) {
        showForm(codeValue != null);
        if (codeValue != null && codeValue.isNewCodeValue() && codeTypeSelect != null && codeTypeSelect.getValue() != null) {
            codeValue.setTypeId(codeTypeSelect.getValue().getId());
        }
        form.editCodeValue(codeValue);
    }

    /**
     * Shows and hides the new codeValue form
     *
     * @param show
     */
    public void showForm(boolean show) {
        form.setVisible(show);
        form.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }
}
