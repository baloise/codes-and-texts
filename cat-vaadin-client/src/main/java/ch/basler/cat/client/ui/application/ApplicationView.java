package ch.basler.cat.client.ui.application;

import ch.basler.cat.client.backend.data.Application;
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
import com.vaadin.flow.router.*;

/**
 * A view for performing create-read-update-delete operations on applications.
 *
 * See also {@link ApplicationViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Application", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ApplicationView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Application";
    private final ApplicationGrid grid;
    private final ApplicationForm form;
    private TextField filter;

    private final ApplicationViewLogic viewLogic = new ApplicationViewLogic(this);
    private Button newApplication;

    private final ApplicationDataProvider dataProvider = new ApplicationDataProvider();

    public ApplicationView() {
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        grid = new ApplicationGrid();
        grid.setDataProvider(dataProvider);
        // Allows user to select a single row in the grid.
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        form = new ApplicationForm(viewLogic);
        
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
        filter.setPlaceholder("Filter name, package-name or creator");
        // Apply the filter to grid's data provider. TextField value is never
        filter.addValueChangeListener(
                event -> dataProvider.setFilter(event.getValue()));
        // A shortcut to focus on the textField by pressing ctrl + F
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        newApplication = new Button("New application");
        // Setting theme variant of new applicationion button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newApplication.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newApplication.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newApplication.addClickListener(click -> viewLogic.newApplication());
        // A shortcut to click the new application button by pressing ALT + N
        newApplication.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newApplication);
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
     * Enables/Disables the new application button.
     * 
     * @param enabled
     */
    public void setNewApplicationEnabled(boolean enabled) {
        newApplication.setEnabled(enabled);
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
    public void selectRow(Application row) {
        grid.getSelectionModel().select(row);
    }

    /**
     * Updates a application in the list of applications.
     * 
     * @param application
     */
    public void updateApplication(Application application) {
        dataProvider.save(application);
    }

    /**
     * Removes a application from the list of applications.
     * 
     * @param application
     */
    public void removeApplication(Application application) {
        dataProvider.delete(application);
    }

    /**
     * Displays user a form to edit a Application.
     * 
     * @param application
     */
    public void editApplication(Application application) {
        showForm(application != null);
        form.editApplication(application);
    }

    /**
     * Shows and hides the new application form
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
