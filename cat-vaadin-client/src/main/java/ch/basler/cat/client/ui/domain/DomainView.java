package ch.basler.cat.client.ui.domain;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Domain;
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A view for performing create-read-update-delete operations on domains.
 *
 * See also {@link DomainViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Domain", layout = MainLayout.class)
public class DomainView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Domain";
    private final DomainGrid grid;
    private final DomainForm form;
    private final DataService dataService;
    private TextField filter;

    private final DomainViewLogic viewLogic;
    private Button newDomain;

    private DomainDataProvider dataProvider;

    public DomainView(@Autowired DataService dataService) {
        this.dataService = dataService;
        viewLogic = new DomainViewLogic(dataService, this);
        dataProvider = new DomainDataProvider(dataService);
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        grid = new DomainGrid();
        grid.setDataProvider(dataProvider);
        // Allows user to select a single row in the grid.
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        form = new DomainForm(viewLogic);

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

        newDomain = new Button("New Domain");
        // Setting theme variant of new domain button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newDomain.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newDomain.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newDomain.addClickListener(click -> viewLogic.newDomain());
        // A shortcut to click the new Domain button by pressing ALT + N
        newDomain.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newDomain);
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
     * Enables/Disables the new romain button.
     *
     * @param enabled
     */
    public void setNewDomainEnabled(boolean enabled) {
        newDomain.setEnabled(enabled);
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
    public void selectRow(Domain row) {
        grid.getSelectionModel().select(row);
    }

    /**
     * Updates a domain in the list of domains.
     *
     * @param domain
     */
    public void updateDomain(Domain domain) {
        dataProvider.save(domain);
        if (domain.isNewDomain()) {
            reloadFromServer();
        }
    }

    /**
     * Removes a domain from the list of domains.
     *
     * @param domain
     */
    public void removeDomain(Domain domain) {
        dataProvider.delete(domain);
        reloadFromServer();
    }

    private void reloadFromServer() {
        this.dataProvider = new DomainDataProvider(dataService);
        this.grid.setDataProvider(dataProvider);
    }


    /**
     * Displays user a form to edit a domain.
     *
     * @param domain
     */
    public void editDomain(Domain domain) {
        showForm(domain != null);
        form.editDomain(domain);
    }

    /**
     * Shows and hides the new domain form
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
