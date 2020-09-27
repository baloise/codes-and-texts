package ch.basler.cat.client.ui.domain;

import ch.basler.cat.client.backend.data.Domain;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;

/**
 * Grid of Domains, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class DomainGrid extends Grid<Domain> {

    public static final String ID = "id";
    public static final String PROJECT_NAME = "project name";
    public static final String PREFIX = "prefix";
    public static final String EMAIL = "email";
    public static final String PACKAGENAME = "packagename";
    public static final String CREATOR = "creator";

    public DomainGrid() {

        setSizeFull();
        addColumn(Domain::getId).setHeader("Id")
                .setFlexGrow(20).setSortable(true).setKey(ID);
        addColumn(Domain::getPrefix).setHeader("Prefix")
                .setFlexGrow(20).setSortable(true).setKey(PREFIX);
        addColumn(Domain::getProjectName).setHeader("Project Name")
                .setFlexGrow(20).setSortable(true).setKey(PROJECT_NAME);
        addColumn(Domain::getPackageName).setHeader("Package Name")
                .setFlexGrow(20).setSortable(true).setKey(PACKAGENAME);
        addColumn(Domain::getEmail).setHeader("email")
                .setFlexGrow(20).setSortable(true).setKey(EMAIL);
        addColumn(Domain::getCreator).setHeader("Creator")
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

    public Domain getSelectedRow() {
        Notification.show("asdasd");
        return asSingleSelect().getValue();
    }

    public void refresh(Domain  Domain) {
        getDataCommunicator().refresh(Domain);
    }

}
