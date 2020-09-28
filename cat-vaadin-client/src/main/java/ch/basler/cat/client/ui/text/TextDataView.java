package ch.basler.cat.client.ui.text;

import ch.basler.cat.client.backend.data.TextData;
import ch.basler.cat.client.backend.data.TextType;
import ch.basler.cat.client.ui.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

/**
 * A view for performing create-read-update-delete operations on Texts.
 *
 * See also {@link TextDataViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Text", layout = MainLayout.class)
public class TextDataView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Text";
    private final TextDataGrid grid;
    private final TextDataForm form;
    private TextField filter;
    private Select<TextType> typeSelect;

    private final TextDataViewLogic viewLogic = new TextDataViewLogic(this);
    private Button newText;

    private TextDataProvider dataProvider = new TextDataProvider();

    public TextDataView() {
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        grid = new TextDataGrid();
        grid.setDataProvider(dataProvider);
        // Allows user to select a single row in the grid.
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        form = new TextDataForm(viewLogic);
        
        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        add(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        typeSelect = new Select<>();
        typeSelect.setPlaceholder("Select type");
        typeSelect.setItems(TextType.values());
        typeSelect.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                Integer selectedValue = changeEvent.getValue().getValue();
                dataProvider.setFilter(t -> (selectedValue == null) || Long.valueOf(selectedValue).equals(t.getType()));
            }
        });
        filter = new TextField();
        filter.setPlaceholder("Filter text or creator");
        // Apply the filter to grid's data provider. TextField value is never
        filter.addValueChangeListener(
                event -> dataProvider.setFilter(event.getValue()));
        // A shortcut to focus on the textField by pressing ctrl + F
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        newText = new Button("New Text");
        // Setting theme variant of new Textion button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newText.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newText.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newText.addClickListener(click -> viewLogic.newText());
        // A shortcut to click the new Text button by pressing ALT + N
        newText.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(typeSelect);
        topLayout.add(filter);
        topLayout.add(newText);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Shows a temporary popup notification to the user.
     * 
     * @see Notification#show(String)
     * @param msg
     */
    public void showNotification(String msg) {
        Notification.show(msg);
    }

    /**
     * Enables/Disables the new Text button.
     * 
     * @param enabled
     */
    public void setNewTextEnabled(boolean enabled) {
        newText.setEnabled(enabled);
    }

    /**
     * Deselects the selected row in the grid.
     */
    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    /**
     * Selects a row
     * 
     * @param row
     */
    public void selectRow(TextData row) {
        grid.getSelectionModel().select(row);
    }

    /**
     * Updates a text in the list of Texts.
     * 
     * @param textData
     */
    public void updateText(TextData textData) {
        dataProvider.save(textData);
        if (textData.isNewText()) {
            reloadFromServer();
        }
    }

    /**
     * Removes a text from the list of Texts.
     * 
     * @param textData
     */
    public void removeText(TextData textData) {
        dataProvider.delete(textData);
        reloadFromServer();
    }

    private void reloadFromServer() {
        this.dataProvider = new TextDataProvider();
        this.grid.setDataProvider(dataProvider);
    }


    /**
     * Displays user a form to edit a text.
     * 
     * @param textData
     */
    public void editText(TextData textData) {
        showForm(textData != null);
        form.editText(textData);
    }

    /**
     * Shows and hides the new Text form
     * 
     * @param show
     */
    public void showForm(boolean show) {
        form.setVisible(show);
        form.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
            @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }
}
