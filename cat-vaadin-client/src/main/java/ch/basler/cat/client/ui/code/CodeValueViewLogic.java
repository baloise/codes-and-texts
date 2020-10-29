package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the codeValue editor form and the data source, including
 * fetching and saving codeValues.
 * <p>
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class CodeValueViewLogic implements Serializable {

    public static final String FRAGMENT_PREFIX = "CODE_VALUE_FORM";
    public static final String NEW = "new";
    private final DataService dataService;
    private final CodeValueView view;

    public CodeValueViewLogic(DataService dataService, CodeValueView simpleCrudView) {
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
            view.setNewCodeValueEnabled(false);
            view.setNewCodeTextEnabled(false);
        }
    }

    public void cancelCodeValue() {
        setFragmentParameter("");
            view.clearValueSelection();
            view.clearTextSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the codeValueId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual codeValue selections.
     */
    private void setFragmentParameter(String codeValueId) {
        String fragmentParameter;
        if (codeValueId == null || codeValueId.isEmpty()) {
            fragmentParameter = FRAGMENT_PREFIX;
        } else {
            fragmentParameter = FRAGMENT_PREFIX + codeValueId;
        }

        UI.getCurrent().navigate(CodeValueView.class, fragmentParameter);
    }

    /**
     * Opens the codeValue form and clears its fields to make it ready for
     * entering a new codeValue if codeValueId is null, otherwise loads the codeValue
     * with the given codeValueId and shows its data in the form fields so the
     * user can edit them.
     */
    public void enter(CodeType codeType, String valueString) {
        valueString = valueString.substring(FRAGMENT_PREFIX.length());
        if (!valueString.isEmpty()) {
            if (valueString.equals(NEW)) {
                newCodeValue();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final CodeValue codeValue = findCodeValue(codeType, Long.parseLong(valueString));
                    view.selectRow(codeValue);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showCodeValueForm(false);
        }
    }

    private CodeValue findCodeValue(CodeType codeType, long value) {
        return dataService.getCodeValue(codeType.getId(), value);
    }

    public void saveCodeValue(CodeValue codeValue) {
        final boolean newCodeValue = codeValue.isNewCodeValue();
        view.clearValueSelection();
        view.updateCodeValue(codeValue);
        setFragmentParameter("");
        view.showNotification(codeValue.getName()
                + (newCodeValue ? " created" : " updated"));
    }

    public void deleteCodeValue(CodeValue codeValue) {
        view.clearValueSelection();
        view.removeCodeValue(codeValue);
        setFragmentParameter("");
        view.showNotification(codeValue.getName() + " removed");
    }

    public void editCodeValue(CodeValue codeValue) {
        if (codeValue == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(codeValue.getType() + ":" + codeValue.getValue());
        }
        view.editCodeValue(codeValue);
    }

    public void newCodeValue() {
        view.clearValueSelection();
        setFragmentParameter(NEW);
        view.editCodeValue(new CodeValue());
    }

    public void rowSelected(CodeValue codeValue) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editCodeValue(codeValue);
        }
    }
}
