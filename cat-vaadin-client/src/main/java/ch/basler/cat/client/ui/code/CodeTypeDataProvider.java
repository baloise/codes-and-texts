package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeType;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link CodeType} entities.
 * <p>
 */
public class CodeTypeDataProvider extends ListDataProvider<CodeType> {

    /** Text filter that can be changed separately. */
    private String filterText = "";

    public CodeTypeDataProvider() {
        super(DataService.get().getAllCodeTypes().stream()
                .sorted(
                        Comparator.comparing(CodeType::getName))
                .collect(Collectors.toList()));
    }

    /**
     * Store given codeType to the backing data service.
     *
     * @param codeType
     *            the updated or new codeType
     */
    public void save(CodeType codeType) {
        final boolean newCodeType = codeType.isNewCodeType();

        DataService.get().saveCodeType(codeType);
        if (newCodeType) {
            refreshAll();
        } else {
            refreshItem(codeType);
        }
    }

    /**
     * Delete given codeType from the backing data service.
     *
     * @param codeType
     *            the codeType to be deleted
     */
    public void delete(CodeType codeType) {
        DataService.get().deleteCodeType(codeType.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for codeType name.
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

        this.setFilter(codeType -> passesFilter(codeType.getName(), this.filterText));
    }

    public void setFilter(Long domainId) {
        super.setFilter(codeType -> codeType.getDomain().equals(domainId));
    }

    @Override
    public Long getId(CodeType codeType) {
        Objects.requireNonNull(codeType,
                "Cannot provide an id for a null codeType.");

        return codeType.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
