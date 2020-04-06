package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeType;
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
 * A view for performing create-read-update-delete operations on codeTypes.
 * <p>
 * See also {@link CodeTypeViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "CodeType", layout = MainLayout.class)
public class CodeTypeView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "CodeType";
    private final CodeTypeGrid grid;
    private final CodeTypeForm form;
    //    private TextField filter;
    private Select<Responsible> responsibleSelect;

    private final CodeTypeViewLogic viewLogic = new CodeTypeViewLogic(this);
    private Button newCodeType;

    private final CodeTypeDataProvider dataProvider = new CodeTypeDataProvider();

    public CodeTypeView() {
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        grid = new CodeTypeGrid();
        grid.setDataProvider(dataProvider);

        // Allows user to select a single row in the grid.
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        form = new CodeTypeForm(viewLogic);

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        add(form);

        viewLogic.init();
    }

    private HorizontalLayout createTopBar() {
        responsibleSelect = new Select<>();
        responsibleSelect.setPlaceholder("Select responsible");
        responsibleSelect.setItems(new ResponsibleDataProvider().getItems());
        responsibleSelect.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                newCodeType.setEnabled(true);
                dataProvider.setFilter(changeEvent.getValue());
            }

        });


        newCodeType = new Button("New codeType");
        // Setting theme variant of new codeTypeion button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newCodeType.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newCodeType.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newCodeType.addClickListener(click -> viewLogic.newCodeType());
        newCodeType.setEnabled(false);
        // A shortcut to click the new codeType button by pressing ALT + N
        newCodeType.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
//        topLayout.add(filter);
        topLayout.add(responsibleSelect);
        topLayout.add(newCodeType);
        topLayout.setVerticalComponentAlignment(Alignment.START, responsibleSelect);
        topLayout.expand(responsibleSelect);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Shows a temporary popup notification to the user.
     *
     * @param msg message
     * @see Notification#show(String)
     */
    public void showNotification(String msg) {
        Notification.show(msg);
    }

    /**
     * Enables/Disables the new codeType button.
     *
     * @param enabled flag
     */
    public void setNewCodeTypeEnabled(boolean enabled) {
        newCodeType.setEnabled(enabled);
    }

    /**
     * Deselects the selected row in the grid.
     */
    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    /**
     * Selects a row
     *
     * @param row
     */
    public void selectRow(CodeType row) {
        grid.getSelectionModel().select(row);
    }

    /**
     * Updates a codeType in the list of codeTypes.
     *
     * @param codeType
     */
    public void updateCodeType(CodeType codeType) {
        dataProvider.save(codeType);
    }

    /**
     * Removes a codeType from the list of codeTypes.
     *
     * @param codeType
     */
    public void removeCodeType(CodeType codeType) {
        dataProvider.delete(codeType);
    }

    /**
     * Displays user a form to edit a CodeType.
     *
     * @param codeType
     */
    public void editCodeType(CodeType codeType) {
        showForm(codeType != null);
        if (codeType.isNewCodeType()) {
            codeType.setResponsible(responsibleSelect.getValue());
        }
        form.editCodeType(codeType);
    }

    /**
     * Shows and hides the new codeType form
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
