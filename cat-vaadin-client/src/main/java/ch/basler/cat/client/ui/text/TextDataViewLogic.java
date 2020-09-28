package ch.basler.cat.client.ui.text;

import ch.basler.cat.client.authentication.AccessControl;
import ch.basler.cat.client.authentication.AccessControlFactory;
import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.TextData;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the Text editor form and the data source, including
 * fetching and saving Texts.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class TextDataViewLogic implements Serializable {

    private final TextDataView view;

    public TextDataViewLogic(TextDataView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewTextEnabled(false);
        }
    }

    public void cancelText() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the textId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual Text selections.
     *
     */
    private void setFragmentParameter(String textId) {
        String fragmentParameter;
        if (textId == null || textId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = textId;
        }

        UI.getCurrent().navigate(TextDataView.class, fragmentParameter);
    }

    /**
     * Opens the Text form and clears its fields to make it ready for
     * entering a new Text if textId is null, otherwise loads the Text
     * with the given textId and shows its data in the form fields so the
     * user can edit them.
     *
     * 
     * @param textId
     */
    public void enter(String textId) {
        if (textId != null && !textId.isEmpty()) {
            if (textId.equals("new")) {
                newText();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(textId);
                    final TextData TextData = findText(pid);
                    view.selectRow(TextData);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private TextData findText(int textId) {
        return DataService.get().getTextById(textId);
    }

    public void saveText(TextData textData) {
        final boolean newText = textData.isNewText();
        view.clearSelection();
        view.updateText(textData);
        setFragmentParameter("");
        view.showNotification(textData.toString()
                + (newText ? " created" : " updated"));
    }

    public void deleteText(TextData textData) {
        view.clearSelection();
        view.removeText(textData);
        setFragmentParameter("");
        view.showNotification(textData.toString() + " removed");
    }

    public void editText(TextData textData) {
        if (textData == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(textData.getId() + "");
        }
        view.editText(textData);
    }

    public void newText() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editText(new TextData());
    }

    public void rowSelected(TextData textData) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editText(textData);
        }
    }
}
