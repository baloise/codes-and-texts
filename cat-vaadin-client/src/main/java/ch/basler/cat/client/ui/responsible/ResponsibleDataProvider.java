package ch.basler.cat.client.ui.responsible;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Responsible;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link Responsible} entities.
 * <p>
 */
public class ResponsibleDataProvider extends ListDataProvider<Responsible> {

    /** Text filter that can be changed separately. */
    private String filterText = "";

    public ResponsibleDataProvider() {
        super(DataService.get().getAllResponsibles().stream()
                .sorted(
                        Comparator.comparing(Responsible::getPrefix).thenComparing(Responsible::getProjectName))
                .collect(Collectors.toList()));
    }

    /**
     * Store given responsible to the backing data service.
     *
     * @param responsible
     *            the updated or new responsible
     */
    public void save(Responsible responsible) {
        final boolean newResponsible = responsible.isNewResponsible();

        DataService.get().saveResponsible(responsible);
        if (newResponsible) {
            refreshAll();
        } else {
            refreshItem(responsible);
        }
    }

    /**
     * Delete given responsible from the backing data service.
     *
     * @param responsible
     *            the responsible to be deleted
     */
    public void delete(Responsible responsible) {
        DataService.get().deleteResponsible(responsible.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for responsible name.
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

        setFilter(responsible -> passesFilter(responsible.getProjectName(), this.filterText)
                || passesFilter(responsible.getPackageName(), this.filterText)
                || passesFilter(responsible.getCreator(), this.filterText));
    }

    @Override
    public Long getId(Responsible responsible) {
        Objects.requireNonNull(responsible,
                "Cannot provide an id for a null responsible.");

        return responsible.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
