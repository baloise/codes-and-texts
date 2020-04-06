package ch.basler.cat.client.ui.application;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Application;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the application editor form and the data source, including
 * fetching and saving applications.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class ApplicationViewLogic implements Serializable {

    private final ApplicationView view;

    public ApplicationViewLogic(ApplicationView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewApplicationEnabled(false);
        }
    }

    public void cancelApplication() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the applicationId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual application selections.
     *
     */
    private void setFragmentParameter(String applicationId) {
        String fragmentParameter;
        if (applicationId == null || applicationId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = applicationId;
        }

        UI.getCurrent().navigate(ApplicationView.class, fragmentParameter);
    }

    /**
     * Opens the application form and clears its fields to make it ready for
     * entering a new application if applicationId is null, otherwise loads the application
     * with the given applicationId and shows its data in the form fields so the
     * user can edit them.
     *
     * 
     * @param applicationId
     */
    public void enter(String applicationId) {
        if (applicationId != null && !applicationId.isEmpty()) {
            if (applicationId.equals("new")) {
                newApplication();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(applicationId);
                    final Application application = findApplication(pid);
                    view.selectRow(application);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private Application findApplication(int applicationId) {
        return DataService.get().getApplicationById(applicationId);
    }

    public void saveApplication(Application application) {
        final boolean newApplication = application.isNewApplication();
        view.clearSelection();
        view.updateApplication(application);
        setFragmentParameter("");
        view.showNotification(application.getName()
                + (newApplication ? " created" : " updated"));
    }

    public void deleteApplication(Application application) {
        view.clearSelection();
        view.removeApplication(application);
        setFragmentParameter("");
        view.showNotification(application.getName() + " removed");
    }

    public void editApplication(Application application) {
        if (application == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(application.getId() + "");
        }
        view.editApplication(application);
    }

    public void newApplication() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editApplication(new Application());
    }

    public void rowSelected(Application application) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editApplication(application);
        }
    }
}
