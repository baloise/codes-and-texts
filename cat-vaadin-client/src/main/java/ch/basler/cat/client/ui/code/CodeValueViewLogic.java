package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeValue;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the codeValue editor form and the data source, including
 * fetching and saving codeValues.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class CodeValueViewLogic implements Serializable {

    private final CodeValueView view;

    public CodeValueViewLogic(CodeValueView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewCodeValueEnabled(false);
        }
    }

    public void cancelCodeValue() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the codeValueId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual codeValue selections.
     *
     */
    private void setFragmentParameter(String codeValueId) {
        String fragmentParameter;
        if (codeValueId == null || codeValueId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = codeValueId;
        }

        UI.getCurrent().navigate(CodeValueView.class, fragmentParameter);
    }

    /**
     * Opens the codeValue form and clears its fields to make it ready for
     * entering a new codeValue if codeValueId is null, otherwise loads the codeValue
     * with the given codeValueId and shows its data in the form fields so the
     * user can edit them.
     *
     * 
     * @param codeValueId
     */
    public void enter(String codeValueId) {
        if (codeValueId != null && !codeValueId.isEmpty()) {
            if (codeValueId.equals("new")) {
                newCodeValue();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {

                    final CodeValue codeValue = findCodeValue(codeValueId);
                    view.selectRow(codeValue);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private CodeValue findCodeValue(String codeValueId) {
        return DataService.get().getCodeValueById(codeValueId);
    }

    public void saveCodeValue(CodeValue codeValue) {
        final boolean newCodeValue = codeValue.isNewCodeValue();
        view.clearSelection();
        view.updateCodeValue(codeValue);
        setFragmentParameter("");
        view.showNotification(codeValue.getName()
                + (newCodeValue ? " created" : " updated"));
    }

    public void deleteCodeValue(CodeValue codeValue) {
        view.clearSelection();
        view.removeCodeValue(codeValue);
        setFragmentParameter("");
        view.showNotification(codeValue.getName() + " removed");
    }

    public void editCodeValue(CodeValue codeValue) {
        if (codeValue == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(codeValue.getId() + "");
        }
        view.editCodeValue(codeValue);
    }

    public void newCodeValue() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editCodeValue(new CodeValue());
    }

    public void rowSelected(CodeValue codeValue) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editCodeValue(codeValue);
        }
    }
}
