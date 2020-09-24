package ch.basler.cat.client.ui.responsible;

import ch.basler.cat.client.backend.data.Responsible;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;

/**
 * Grid of responsibles, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class ResponsibleGrid extends Grid<Responsible> {

    public static final String ID = "id";
    public static final String PROJECT_NAME = "project name";
    public static final String PREFIX = "prefix";
    public static final String EMAIL = "email";
    public static final String PACKAGENAME = "packagename";
    public static final String CREATOR = "creator";

    public ResponsibleGrid() {

        setSizeFull();
        addColumn(Responsible::getId).setHeader("Id")
                .setFlexGrow(20).setSortable(true).setKey(ID);
        addColumn(Responsible::getPrefix).setHeader("Prefix")
                .setFlexGrow(20).setSortable(true).setKey(PREFIX);
        addColumn(Responsible::getProjectName).setHeader("Project Name")
                .setFlexGrow(20).setSortable(true).setKey(PROJECT_NAME);
        addColumn(Responsible::getPackageName).setHeader("Package Name")
                .setFlexGrow(20).setSortable(true).setKey(PACKAGENAME);
        addColumn(Responsible::getEmail).setHeader("email")
                .setFlexGrow(20).setSortable(true).setKey(EMAIL);
        addColumn(Responsible::getCreator).setHeader("Creator")
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
            getColumnByKey(PROJECT_NAME).setVisible(true);
            getColumnByKey(PREFIX).setVisible(true);
            getColumnByKey(PACKAGENAME).setVisible(true);
            getColumnByKey(EMAIL).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else if (width > 550) {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(PROJECT_NAME).setVisible(true);
            getColumnByKey(PREFIX).setVisible(true);
            getColumnByKey(PACKAGENAME).setVisible(true);
            getColumnByKey(EMAIL).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(PROJECT_NAME).setVisible(true);
            getColumnByKey(PREFIX).setVisible(true);
            getColumnByKey(PACKAGENAME).setVisible(true);
            getColumnByKey(EMAIL).setVisible(false);
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

    public Responsible getSelectedRow() {
        Notification.show("asdasd");
        return asSingleSelect().getValue();
    }

    public void refresh(Responsible  responsible) {
        getDataCommunicator().refresh(responsible);
    }

}
