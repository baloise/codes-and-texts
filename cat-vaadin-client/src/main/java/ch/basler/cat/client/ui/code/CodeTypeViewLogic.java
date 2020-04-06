package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeType;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the codeType editor form and the data source, including
 * fetching and saving codeTypes.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class CodeTypeViewLogic implements Serializable {

    private final CodeTypeView view;

    public CodeTypeViewLogic(CodeTypeView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewCodeTypeEnabled(false);
        }
    }

    public void cancelCodeType() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the codeTypeId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual codeType selections.
     *
     */
    private void setFragmentParameter(String codeTypeId) {
        String fragmentParameter;
        if (codeTypeId == null || codeTypeId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = codeTypeId;
        }

        UI.getCurrent().navigate(CodeTypeView.class, fragmentParameter);
    }

    /**
     * Opens the codeType form and clears its fields to make it ready for
     * entering a new codeType if codeTypeId is null, otherwise loads the codeType
     * with the given codeTypeId and shows its data in the form fields so the
     * user can edit them.
     *
     * 
     * @param codeTypeId
     */
    public void enter(String codeTypeId) {
        if (codeTypeId != null && !codeTypeId.isEmpty()) {
            if (codeTypeId.equals("new")) {
                newCodeType();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(codeTypeId);
                    final CodeType codeType = findCodeType(pid);
                    view.selectRow(codeType);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private CodeType findCodeType(int codeTypeId) {
        return DataService.get().getCodeTypeById(codeTypeId);
    }

    public void saveCodeType(CodeType codeType) {
        final boolean newCodeType = codeType.isNewCodeType();
        view.clearSelection();
        view.updateCodeType(codeType);
        setFragmentParameter("");
        view.showNotification(codeType.getName()
                + (newCodeType ? " created" : " updated"));
    }

    public void deleteCodeType(CodeType codeType) {
        view.clearSelection();
        view.removeCodeType(codeType);
        setFragmentParameter("");
        view.showNotification(codeType.getName() + " removed");
    }

    public void editCodeType(CodeType codeType) {
        if (codeType == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(codeType.getId() + "");
        }
        view.editCodeType(codeType);
    }

    public void newCodeType() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editCodeType(new CodeType());
    }

    public void rowSelected(CodeType codeType) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editCodeType(codeType);
        }
    }
}
