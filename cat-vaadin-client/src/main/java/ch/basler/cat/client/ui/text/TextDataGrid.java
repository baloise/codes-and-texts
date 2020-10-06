package ch.basler.cat.client.ui.text;

import ch.basler.cat.client.backend.data.TextData;
import ch.basler.cat.client.backend.data.TextType;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;

/**
 * Grid of Texts, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class TextDataGrid extends Grid<TextData> {

    public static final String ID = "id";
    public static final String TEXTTYPE = "textType";
    public static final String DE = "de";
    public static final String FR = "fr";
    public static final String IT = "it";
    public static final String EN = "en";
    public static final String CREATOR = "creator";

    public TextDataGrid() {

        setSizeFull();
        addColumn(TextData::getId).setHeader("Id")
                .setFlexGrow(10).setSortable(true).setKey(ID);
        addColumn(TextData::getTextType).setHeader("text-type")
                .setFlexGrow(10).setSortable(true).setKey(TEXTTYPE);
        addColumn(TextData::getTextD).setHeader("DE")
                .setFlexGrow(20).setSortable(true).setKey(DE);
        addColumn(TextData::getTextF).setHeader("FR")
                .setFlexGrow(20).setSortable(true).setKey(FR);
        addColumn(TextData::getTextI).setHeader("IT")
                .setFlexGrow(20).setSortable(true).setKey(IT);
        addColumn(TextData::getTextE).setHeader("EN")
                .setFlexGrow(20).setSortable(true).setKey(EN);
        addColumn(TextData::getCreator).setHeader("Creator")
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
            getColumnByKey(TEXTTYPE).setVisible(true);
            getColumnByKey(DE).setVisible(true);
            getColumnByKey(FR).setVisible(true);
            getColumnByKey(IT).setVisible(true);
            getColumnByKey(EN).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else if (width > 550) {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(TEXTTYPE).setVisible(true);
            getColumnByKey(DE).setVisible(true);
            getColumnByKey(FR).setVisible(true);
            getColumnByKey(IT).setVisible(true);
            getColumnByKey(EN).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else {
            getColumnByKey(ID).setVisible(true);
            getColumnByKey(TEXTTYPE).setVisible(true);
            getColumnByKey(DE).setVisible(true);
            getColumnByKey(FR).setVisible(true);
            getColumnByKey(IT).setVisible(true);
            getColumnByKey(EN).setVisible(true);
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

    public TextData getSelectedRow() {
        Notification.show("asdasd");
        return asSingleSelect().getValue();
    }

    public void refresh(TextData TextData) {
        getDataCommunicator().refresh(TextData);
    }

}
