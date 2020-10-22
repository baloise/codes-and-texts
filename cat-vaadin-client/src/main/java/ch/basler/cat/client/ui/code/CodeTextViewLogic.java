package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeText;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.TextData;
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
public class CodeTextViewLogic implements Serializable {

    public static final String NEW = "new";
    private final CodeValueView view;
    public static final String  FRAGMENT_PREFIX = "CODE_TEXT_FORM";

    public CodeTextViewLogic(CodeValueView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewCodeTextEnabled(false);
        }
    }

    public void cancelCodeText() {
        setFragmentParameter("");
        view.clearTextSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the codeTextId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual codeValue selections.
     *
     */
    private void setFragmentParameter(String codeTextId) {
        String fragmentParameter;
        if (codeTextId == null || codeTextId.isEmpty()) {
            fragmentParameter = FRAGMENT_PREFIX;
        } else {
            fragmentParameter = FRAGMENT_PREFIX + codeTextId;
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
        if (valueString != null && !valueString.isEmpty()) {
            if (valueString.equals("new")) {
                newCodeText();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final TextData textData = findText(codeType, Long.parseLong(valueString));
                    view.selectRow(textData);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showCodeTextForm(false);
        }
    }

    private TextData findText(CodeType codeType, long value) {
        CodeText codeTextByIds = DataService.get().getCodeTextByIds(codeType.getId(), value);
        return DataService.get().getTextById(codeTextByIds.getTextId());
    }

    public void saveCodeText(CodeText codeText) {
        final boolean newCodeText = codeText.isNewCodeText();
        view.clearTextSelection();
        view.updateCodeText(codeText);
        setFragmentParameter("");
        view.showNotification(codeText.getName()
                + (newCodeText ? " created" : " updated"));
    }

    public void deleteCodeText(CodeText codeText) {
        view.clearTextSelection();
        view.removeCodeText(codeText);
        setFragmentParameter("");
        view.showNotification(codeText.getName() + " removed");
    }

    public void editCodeText(CodeText codeText) {
        if (codeText == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(codeText.getType() + ":" + codeText.getValue());
        }
            view.editCodeText(codeText);
    }

    public void newCodeText() {
        view.clearTextSelection();
        setFragmentParameter(NEW);
        CodeText codeText = new CodeText();
        codeText.setNewCodeText(true);
        view.editCodeText(codeText);
    }

    public void rowSelected(CodeText codeText) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editCodeText(codeText);
        }
    }
}
