package ch.basler.cat.client.ui.domain;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Domain;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the Domain editor form and the data source, including
 * fetching and saving Domains.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class DomainViewLogic implements Serializable {

    private final DataService dataService;
    private final DomainView view;

    public DomainViewLogic(DataService dataService, DomainView simpleCrudView) {
        this.dataService = dataService;
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewDomainEnabled(false);
        }
    }

    public void cancelDomain() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the DomainId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual Domain selections.
     *
     */
    private void setFragmentParameter(String DomainId) {
        String fragmentParameter;
        if (DomainId == null || DomainId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = DomainId;
        }

        UI.getCurrent().navigate(DomainView.class, fragmentParameter);
    }

    /**
     * Opens the Domain form and clears its fields to make it ready for
     * entering a new Domain if DomainId is null, otherwise loads the Domain
     * with the given DomainId and shows its data in the form fields so the
     * user can edit them.
     *
     *
     * @param DomainId
     */
    public void enter(String DomainId) {
        if (DomainId != null && !DomainId.isEmpty()) {
            if (DomainId.equals("new")) {
                newDomain();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(DomainId);
                    final Domain Domain = findDomain(pid);
                    view.selectRow(Domain);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private Domain findDomain(int DomainId) {
        return dataService.getDomainById(DomainId);
    }

    public void saveDomain(Domain Domain) {
        final boolean newDomain = Domain.isNewDomain();
        view.clearSelection();
        view.updateDomain(Domain);
        setFragmentParameter("");
        view.showNotification(Domain.getProjectName()
                + (newDomain ? " created" : " updated"));
    }

    public void deleteDomain(Domain Domain) {
        view.clearSelection();
        view.removeDomain(Domain);
        setFragmentParameter("");
        view.showNotification(Domain.getProjectName() + " removed");
    }

    public void editDomain(Domain Domain) {
        if (Domain == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(Domain.getId() + "");
        }
        view.editDomain(Domain);
    }

    public void newDomain() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editDomain(new Domain());
    }

    public void rowSelected(Domain Domain) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editDomain(Domain);
        }
    }
}
