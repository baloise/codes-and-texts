package ch.basler.cat.client.ui.responsible;

import ch.basler.cat.client.backend.data.Responsible;
import ch.basler.cat.client.ui.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

/**
 * A view for performing create-read-update-delete operations on responsibles.
 *
 * See also {@link ResponsibleViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Responsible", layout = MainLayout.class)
public class ResponsibleView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Responsible";
    private final ResponsibleGrid grid;
    private final ResponsibleForm form;
    private TextField filter;

    private final ResponsibleViewLogic viewLogic = new ResponsibleViewLogic(this);
    private Button newResponsible;

    private final ResponsibleDataProvider dataProvider = new ResponsibleDataProvider();

    public ResponsibleView() {
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        grid = new ResponsibleGrid();
        grid.setDataProvider(dataProvider);
        // Allows user to select a single row in the grid.
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        form = new ResponsibleForm(viewLogic);
        
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

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter projekt-name, package-name or creator");
        // Apply the filter to grid's data provider. TextField value is never
        filter.addValueChangeListener(
                event -> dataProvider.setFilter(event.getValue()));
        // A shortcut to focus on the textField by pressing ctrl + F
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        newResponsible = new Button("New responsible");
        // Setting theme variant of new responsibleion button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newResponsible.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newResponsible.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newResponsible.addClickListener(click -> viewLogic.newResponsible());
        // A shortcut to click the new responsible button by pressing ALT + N
        newResponsible.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newResponsible);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Shows a temporary popup notification to the user.
     * 
     * @see Notification#show(String)
     * @param msg
     */
    public void showNotification(String msg) {
        Notification.show(msg);
    }

    /**
     * Enables/Disables the new responsible button.
     * 
     * @param enabled
     */
    public void setNewResponsibleEnabled(boolean enabled) {
        newResponsible.setEnabled(enabled);
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
    public void selectRow(Responsible row) {
        grid.getSelectionModel().select(row);
    }

    /**
     * Updates a responsible in the list of responsibles.
     * 
     * @param responsible
     */
    public void updateResponsible(Responsible responsible) {
        dataProvider.save(responsible);
    }

    /**
     * Removes a responsible from the list of responsibles.
     * 
     * @param responsible
     */
    public void removeResponsible(Responsible responsible) {
        dataProvider.delete(responsible);
    }

    /**
     * Displays user a form to edit a Responsible.
     * 
     * @param responsible
     */
    public void editResponsible(Responsible responsible) {
        showForm(responsible != null);
        form.editResponsible(responsible);
    }

    /**
     * Shows and hides the new responsible form
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
