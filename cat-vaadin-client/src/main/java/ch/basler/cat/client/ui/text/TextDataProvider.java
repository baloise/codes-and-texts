package ch.basler.cat.client.ui.text;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.TextData;
import ch.basler.cat.client.backend.data.TextType;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Arrays;
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

    private final DataService dataService;
    /** Text filter that can be changed separately. */
    private String filterTextD = "";
    private String filterTextF = "";
    private String filterTextI = "";
    private String filterTextE = "";
    private TextType filterTextType = TextType.ALL;
    private Long textIdFilter;

    public TextDataProvider(DataService dataService) {
        super(dataService.getAllTexts().stream()
                .sorted(
                        Comparator.comparing(TextData::getId).thenComparing(TextData::getType))
                .collect(Collectors.toList()));

        this.dataService = dataService;
    }

    public TextDataProvider(DataService dataService, long textId) {
        super(Arrays.asList(dataService.getTextById(textId)));
        this.dataService = dataService;
    }
    /**
     * Store given text to the backing data service.
     *
     * @param textData
     *            the updated or new text
     */
    public void save(TextData textData) {
        final boolean newText = textData.isNewText();

        dataService.saveText(textData);
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
        dataService.deleteText(textData.getId());
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
//        if (Objects.equals(this.filterTextD, filterText.trim().toLowerCase(Locale.GERMAN)) ) {
//            return;
//        }
        this.filterTextD = filterText.trim().toLowerCase(Locale.GERMAN);
        this.filterTextF = filterText.trim().toLowerCase(Locale.FRENCH);
        this.filterTextI = filterText.trim().toLowerCase(Locale.ITALIAN);
        this.filterTextE = filterText.trim().toLowerCase(Locale.ENGLISH);

        applyFilter();
    }

    public void setFilter(long textId) {
        this.textIdFilter = textId;
        applyFilter();
    }

    private void applyFilter() {
        if (textIdFilter != null) {
            setFilter(textData -> textData.getId() == textIdFilter.longValue());
        }
        else {
            setFilter(textData -> {
                boolean textPasses = passesFilter(textData.getTextD(), this.filterTextD, Locale.GERMAN)
                        || passesFilter(textData.getTextF(), this.filterTextF, Locale.FRENCH)
                        || passesFilter(textData.getTextI(), this.filterTextI, Locale.ITALIAN)
                        || passesFilter(textData.getTextE(), this.filterTextE, Locale.ENGLISH)
                        || passesFilter(textData.getCreator(), this.filterTextD, Locale.GERMAN);
                boolean textTypePasses = this.filterTextType == TextType.ALL || this.filterTextType == textData.getTextType();
                boolean passes = textPasses && textTypePasses;
                return passes;
            });
        }
    }

    public void setFilter(TextType textType) {
        Objects.requireNonNull(textType, "Filter muss be a Text type");

        this.filterTextType = textType;
        applyFilter();
    }

    @Override
    public Long getId(TextData textData) {
        Objects.requireNonNull(textData,
                "Cannot provide an id for a null text.");

        return textData.getId();
    }

    private boolean passesFilter(Object object, String filterText, Locale locale) {
        return object != null && object.toString().toLowerCase(locale)
                .contains(filterText);
    }
}
