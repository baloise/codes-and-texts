package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Locale;
import java.util.Objects;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link CodeValue} entities.
 * <p>
 */
public class CodeValueDataProvider extends ListDataProvider<CodeValue> {

    /** Text filter that can be changed separately. */
    private String filterText = "";


    public CodeValueDataProvider(CodeType codeType) {
        super(DataService.get().getAllCodeValues(codeType));
    }

    /**
     * Store given codeValue to the backing data service.
     *
     * @param codeValue
     *            the updated or new codeValue
     */
    public void save(CodeValue codeValue) {
        final boolean newCodeValue = codeValue.isNewCodeValue();

        DataService.get().saveCodeValue(codeValue);
        if (newCodeValue) {
            refreshAll();
        } else {
            refreshItem(codeValue);
        }
    }

    /**
     * Delete given codeValue from the backing data service.
     *
     * @param codeValue
     *            the codeValue to be deleted
     */
    public void delete(CodeValue codeValue) {
        DataService.get().deleteCodeValue(codeValue.getId());
        refreshAll();
    }


    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(codeValue -> passesFilter(codeValue.getCodeType().getName(), this.filterText)
                || passesFilter(codeValue.getCodeType().getPrefix(), this.filterText));
    }
    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for codeValue name.
     *
     * @param codeType to filter
     *            the text to filter by, never null
     */
    public void setFilter(CodeType codeType) {
        this.setFilter(codeValue -> codeType.equals(codeType));
    }

    @Override
    public String getId(CodeValue codeValue) {
        Objects.requireNonNull(codeValue,
                "Cannot provide an id for a null codeValue.");

        return codeValue.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
