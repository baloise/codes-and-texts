package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.CodeValue;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;

/**
 * Grid of codeValues, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class CodeValueGrid extends Grid<CodeValue> {


    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String CREATOR = "creator";

    public CodeValueGrid() {

        setSizeFull();

        addColumn(CodeValue::getType).setHeader("Type")
                .setFlexGrow(4).setSortable(true).setKey(ID);
        addColumn(CodeValue::getValue).setHeader("Value")
                .setFlexGrow(5).setSortable(true).setKey(VALUE);
        addColumn(CodeValue::getName).setHeader("Name")
                .setFlexGrow(20).setSortable(true).setKey(NAME);
        addColumn(CodeValue::getCreator).setHeader("Creator")
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
            getColumnByKey(VALUE).setVisible(true);
            getColumnByKey(CREATOR).setVisible(true);
        } else if (width > 550) {

            getColumnByKey(ID).setVisible(false);
            getColumnByKey(NAME).setVisible(true);
            getColumnByKey(VALUE).setVisible(true);
            getColumnByKey(CREATOR).setVisible(false);
        } else {

            getColumnByKey(ID).setVisible(false);
            getColumnByKey(NAME).setVisible(true);
            getColumnByKey(VALUE).setVisible(true);
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

    public CodeValue getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(CodeValue  codeValue) {
        getDataCommunicator().refresh(codeValue);
    }

}
