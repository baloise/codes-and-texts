package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeType;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;

/**
 * Grid of codeTypes, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class CodeTypeGrid extends Grid<CodeType> {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PREFIX = "prefix";
    public static final String CREATOR = "creator";

    public CodeTypeGrid() {

        setSizeFull();
        addColumn(CodeType::getId).setHeader("Id")
                .setFlexGrow(4).setSortable(true).setKey(ID);
        addColumn(CodeType::getName).setHeader("Name")
                .setFlexGrow(20).setSortable(true).setKey(NAME);
        addColumn(CodeType::getPrefix).setHeader("Prefix")
                .setFlexGrow(5).setSortable(true).setKey(PREFIX);
        addColumn(CodeType::getCreator).setHeader("Creator")
                .setFlexGrow(20).setSortable(true).setKey(CREATOR);


        // If the browser window size changes, check if all columns fit on
        // screen
        // (e.g. switching from portrait to landscape mode)
        UI.getCurrent().getPage().addBrowserWindowResizeListener(
                e -> setColumnVisibility(e.getWidth()));
    }

    private void setColumnVisibility(int width) {
        if (width > 800) {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(NAME).setVisible(true);
            getColumnByKey(PREFIX).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else if (width > 550) {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(NAME).setVisible(true);
            getColumnByKey(PREFIX).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(NAME).setVisible(true);
            getColumnByKey(PREFIX).setVisible(true);
            getColumnByKey(CREATOR).setVisible(false);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // fetch browser width
        UI.getCurrent().getInternals().setExtendedClientDetails(null);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> {
            setColumnVisibility(e.getBodyClientWidth());
        });
    }

    public CodeType getSelectedRow() {
        Notification.show("asdasd");
        return asSingleSelect().getValue();
    }

    public void refresh(CodeType  codeType) {
        getDataCommunicator().refresh(codeType);
    }

}
