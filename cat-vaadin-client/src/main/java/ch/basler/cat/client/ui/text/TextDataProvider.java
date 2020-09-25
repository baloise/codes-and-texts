package ch.basler.cat.client.ui.text;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.TextData;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link TextData} entities.
 * <p>
 */
public class TextDataProvider extends ListDataProvider<TextData> {

    /** Text filter that can be changed separately. */
    private String filterText = "";

    public TextDataProvider() {
        super(DataService.get().getAllTexts().stream()
                .sorted(
                        Comparator.comparing(TextData::getId).thenComparing(TextData::getType))
                .collect(Collectors.toList()));
    }

    /**
     * Store given text to the backing data service.
     *
     * @param textData
     *            the updated or new text
     */
    public void save(TextData textData) {
        final boolean newText = textData.isNewText();

        DataService.get().saveText(textData);
        if (newText) {
            refreshAll();
        } else {
            refreshItem(textData);
        }
    }

    /**
     * Delete given text from the backing data service.
     *
     * @param textData
     *            the text to be deleted
     */
    public void delete(TextData textData) {
        DataService.get().deleteText(textData.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for Text name.
     *
     * @param filterText
     *            the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(textData -> passesFilter(textData.getTextD(), this.filterText)
                || passesFilter(textData.getTextF(), this.filterText)
                || passesFilter(textData.getTextI(), this.filterText)
                || passesFilter(textData.getTextE(), this.filterText)
                || passesFilter(textData.getCreator(), this.filterText));
    }

    @Override
    public Long getId(TextData textData) {
        Objects.requireNonNull(textData,
                "Cannot provide an id for a null text.");

        return textData.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
