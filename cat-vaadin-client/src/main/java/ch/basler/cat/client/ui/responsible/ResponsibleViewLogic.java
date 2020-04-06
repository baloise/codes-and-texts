package ch.basler.cat.client.ui.responsible;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Responsible;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the responsible editor form and the data source, including
 * fetching and saving responsibles.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class ResponsibleViewLogic implements Serializable {

    private final ResponsibleView view;

    public ResponsibleViewLogic(ResponsibleView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewResponsibleEnabled(false);
        }
    }

    public void cancelResponsible() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the responsibleId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual responsible selections.
     *
     */
    private void setFragmentParameter(String responsibleId) {
        String fragmentParameter;
        if (responsibleId == null || responsibleId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = responsibleId;
        }

        UI.getCurrent().navigate(ResponsibleView.class, fragmentParameter);
    }

    /**
     * Opens the responsible form and clears its fields to make it ready for
     * entering a new responsible if responsibleId is null, otherwise loads the responsible
     * with the given responsibleId and shows its data in the form fields so the
     * user can edit them.
     *
     * 
     * @param responsibleId
     */
    public void enter(String responsibleId) {
        if (responsibleId != null && !responsibleId.isEmpty()) {
            if (responsibleId.equals("new")) {
                newResponsible();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(responsibleId);
                    final Responsible responsible = findResponsible(pid);
                    view.selectRow(responsible);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private Responsible findResponsible(int responsibleId) {
        return DataService.get().getResponsibleById(responsibleId);
    }

    public void saveResponsible(Responsible responsible) {
        final boolean newResponsible = responsible.isNewResponsible();
        view.clearSelection();
        view.updateResponsible(responsible);
        setFragmentParameter("");
        view.showNotification(responsible.getProjectName()
                + (newResponsible ? " created" : " updated"));
    }

    public void deleteResponsible(Responsible responsible) {
        view.clearSelection();
        view.removeResponsible(responsible);
        setFragmentParameter("");
        view.showNotification(responsible.getProjectName() + " removed");
    }

    public void editResponsible(Responsible responsible) {
        if (responsible == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(responsible.getId() + "");
        }
        view.editResponsible(responsible);
    }

    public void newResponsible() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editResponsible(new Responsible());
    }

    public void rowSelected(Responsible responsible) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editResponsible(responsible);
        }
    }
}
