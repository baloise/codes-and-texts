package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.data.*;
import ch.basler.cat.client.ui.MainLayout;
import ch.basler.cat.client.ui.domain.DomainDataProvider;
import ch.basler.cat.client.ui.text.TextDataGrid;
import ch.basler.cat.client.ui.text.TextDataProvider;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

/**
 * A view for performing create-read-update-delete operations on codeValues.
 * <p>
 * See also {@link CodeValueViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "CodeValue", layout = MainLayout.class)
public class CodeValueView extends HorizontalLayout implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "CodeValue";
    private final CodeValueGrid codeValueGrid;
    private final TextDataGrid textDataGrid;
    private final CodeValueForm codeValueForm;
    private final CodeTextForm codeTextForm;
    private Select<Domain> domainSelect;
    private Select<CodeType> codeTypeSelect;
    private CodeTypeDataProvider codeTypeDataProvider;

    private final CodeValueViewLogic codeValueViewLogic = new CodeValueViewLogic(this);
    private final CodeTextViewLogic codeTextViewLogic = new CodeTextViewLogic(this);
    private Button newCodeValue;
    private Button newCodeText;
    private CodeValueDataProvider codeValueDataProvider;
    private TextDataProvider textDataProvider = new TextDataProvider();
    private CodeTextDataProvider codeTextDataProvider;

    public CodeValueView() {
        // Sets the width and the height of InventoryView to "100%".
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        codeValueDataProvider = new CodeValueDataProvider(this.codeTypeSelect.getValue());
        codeValueGrid = new CodeValueGrid();
        codeValueGrid.setVisible(false);
        codeValueGrid.setDataProvider(codeValueDataProvider);


        textDataGrid = new TextDataGrid();
        textDataGrid.setVisible(false);
        textDataGrid.setDataProvider(textDataProvider);


        codeValueForm = new CodeValueForm(codeValueViewLogic);
        codeValueForm.setVisible(false);
        codeTextForm = new CodeTextForm(codeTextViewLogic);
        codeTextForm.setVisible(false);


        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(codeValueGrid);
        barAndGridLayout.add(textDataGrid);
        barAndGridLayout.add(newCodeText);
        barAndGridLayout.setFlexGrow(1, codeValueGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setFlexGrow(1, textDataGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(codeValueGrid);
        barAndGridLayout.expand(textDataGrid);

        // Allows user to select a single row in the grid.
        codeValueGrid.asSingleSelect().addValueChangeListener(
                event -> {
                    CodeValue codeValue = event.getValue();
                    if (codeValue != null) {
                        codeValueViewLogic.rowSelected(codeValue);
                        codeTextDataProvider = new CodeTextDataProvider(codeValue.getType(), codeValue.getValue());
                        if (!codeTextDataProvider.getItems().isEmpty()) {
                            CodeText codeText = codeTextDataProvider.getItems().iterator().next();
                            textDataProvider = new TextDataProvider(codeText.getTextId());
                            textDataGrid.setDataProvider(textDataProvider);
                            textDataGrid.setVisible(true);
                            newCodeText.setVisible(false);
                        } else {
                            textDataGrid.setVisible(false);
                            newCodeText.setVisible(true);
                        }
                    } else {
                        textDataGrid.setVisible(false);
                        newCodeText.setVisible(false);
                    }
                });

        textDataGrid.asSingleSelect().addValueChangeListener(event -> {
            TextData textData = event.getValue();
            if (textData != null) {
                CodeText codeText = codeTextDataProvider.getItems().iterator().next();
                if (codeText != null && codeText.getTextId().equals(textData.getId())) {
                    codeTextViewLogic.rowSelected(codeText);
                }
            }


        });

        add(barAndGridLayout);

        add(codeValueForm);
        add(codeTextForm);

        codeValueViewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        domainSelect = new Select<>();
        domainSelect.setPlaceholder("Select Domain");
        domainSelect.setItems(new DomainDataProvider().getItems());
        domainSelect.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                codeTypeSelect.setEnabled(true);
                codeTypeDataProvider.setFilter(changeEvent.getValue().getId());
                codeTypeSelect.setDataProvider(codeTypeDataProvider);
                codeValueGrid.setVisible(false);

            } else {
                codeValueGrid.setVisible(false);
            }
        });

        codeTypeSelect = new Select<>();
        codeTypeSelect.setEnabled(false);
        codeTypeSelect.setPlaceholder("Select codeType");
        codeTypeDataProvider = new CodeTypeDataProvider();
        codeTypeSelect.setItems(codeTypeDataProvider.getItems());
        codeTypeSelect.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                codeValueDataProvider = new CodeValueDataProvider(this.codeTypeSelect.getValue());
                codeValueGrid.setDataProvider(codeValueDataProvider);
                newCodeValue.setEnabled(true);
                codeValueDataProvider.setFilter(changeEvent.getValue());
                codeValueGrid.setVisible(true);
            } else {
                codeValueGrid.setVisible(false);
                textDataGrid.setVisible(false);
            }
        });
        newCodeValue = new Button("New codeValue");
        newCodeText = new Button("Assign text to code-value");
        newCodeText.setVisible(false);
        // Setting theme variant of new codeValueion button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newCodeValue.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newCodeValue.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newCodeValue.addClickListener(click -> codeValueViewLogic.newCodeValue());
        // A shortcut to click the new codeValue button by pressing ALT + N
        newCodeValue.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        newCodeValue.setEnabled(false);

        newCodeText.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newCodeText.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newCodeText.addClickListener(click -> codeTextViewLogic.newCodeText());
        newCodeText.setVisible(false);


        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(domainSelect);
        topLayout.add(codeTypeSelect);
        topLayout.add(newCodeValue);
        topLayout.setVerticalComponentAlignment(Alignment.START, codeTypeSelect);
        topLayout.expand(codeTypeSelect);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Shows a temporary popup notification to the user.
     *
     * @param msg
     * @see Notification#show(String)
     */
    public void showNotification(String msg) {
        Notification.show(msg);
    }

    /**
     * Enables/Disables the new codeValue button.
     *
     * @param enabled
     */
    public void setNewCodeValueEnabled(boolean enabled) {
        newCodeValue.setEnabled(enabled);
    }

    /**
     * Enables/Disables the new codeValue button.
     *
     * @param enabled
     */
    public void setNewCodeTextEnabled(boolean enabled) {
        newCodeText.setEnabled(enabled);
    }

    /**
     * Deselects the selected row in the grid.
     */
    public void clearValueSelection() {
        codeValueGrid.getSelectionModel().deselectAll();
    }

    /**
     * Deselects the selected row in the grid.
     */
    public void clearTextSelection() {
        textDataGrid.getSelectionModel().deselectAll();
    }

    /**
     * Selects a row
     *
     * @param row
     */
    public void selectRow(CodeValue row) {
        codeValueGrid.getSelectionModel().select(row);
    }

    public void selectRow(TextData row) {
        textDataGrid.getSelectionModel().select(row);
    }

    /**
     * Updates a codeValue in the list of codeValues.
     *
     * @param codeValue
     */
    public void updateCodeValue(CodeValue codeValue) {
        codeValueDataProvider.save(codeValue);
        if (codeValue.isNewCodeValue()) {
            reloadFromServer();
        }
    }

    /**
     * Removes a codeValue from the list of codeValues.
     *
     * @param codeValue
     */
    public void removeCodeValue(CodeValue codeValue) {
        codeValueDataProvider.delete(codeValue);
        reloadFromServer();
    }

    /**
     * Updates a codeText in the list of codeTexts.
     *
     * @param codeText
     */
    public void updateCodeText(CodeText codeText) {
        codeTextDataProvider.save(codeText);
        if (codeText.isNewCodeText()) {
            reloadFromServer();
        }
    }

    /**
     * Removes a codeText from the list of codeTexts.
     *
     * @param codeText
     */
    public void removeCodeText(CodeText codeText) {
        codeTextDataProvider.delete(codeText);
        reloadFromServer();
    }

    private void reloadFromServer() {
        this.codeValueDataProvider = new CodeValueDataProvider(this.codeTypeSelect.getValue());
        this.codeValueGrid.setDataProvider(codeValueDataProvider);
    }

    /**
     * Displays user a form to edit a CodeValue.
     *
     * @param codeValue
     */
    public void editCodeValue(CodeValue codeValue) {
        showCodeValueForm(codeValue != null);
        if (codeValue != null && codeValue.isNewCodeValue() && codeTypeSelect != null && codeTypeSelect.getValue() != null) {
            codeValue.setType(codeTypeSelect.getValue().getId());
        }
        codeValueForm.editCodeValue(codeValue);
    }

    /**
     * Displays user a form to edit a CodeText.
     *
     * @param codeText
     */
    public void editCodeText(CodeText codeText) {
        showCodeTextForm(codeText != null);
        CodeValue codeValue = this.codeValueGrid.getSelectedRow();
        if (codeText != null
                && codeText.isNewCodeText() && codeValue != null) {
            codeText.setType(codeValue.getType());
            codeText.setValue(codeValue.getValue());
            codeText.setName(codeValue.getName());
        }
        codeTextForm.editCodeText(codeText);
    }

    /**
     * Shows and hides the new codeValue form
     *
     * @param show
     */
    public void showCodeValueForm(boolean show) {
        codeValueForm.setVisible(show);
        codeValueForm.setEnabled(show);
    }

    /**
     * Shows and hides the new codeValue form
     *
     * @param show
     */
    public void showCodeTextForm(boolean show) {
        codeTextForm.setVisible(show);
        codeTextForm.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        parameter = parameter == null ? "" : parameter;
        if (parameter.startsWith(CodeValueViewLogic.FRAGMENT_PREFIX)) {
            codeValueViewLogic.enter(codeTypeSelect.getValue(), parameter);
            // close CodeText-Form if CodeValue selection is canceled or has changed
            codeTextViewLogic.enter(codeTypeSelect.getValue(), CodeTextViewLogic.FRAGMENT_PREFIX);
        } else if (parameter.startsWith(CodeTextViewLogic.FRAGMENT_PREFIX)) {
            codeTextViewLogic.enter(codeTypeSelect.getValue(), parameter);
        }
    }

}
