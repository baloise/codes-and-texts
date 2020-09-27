package ch.basler.cat.client.ui.domain;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Domain;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link Domain} entities.
 * <p>
 */
public class DomainDataProvider extends ListDataProvider<Domain> {

    /** Text filter that can be changed separately. */
    private String filterText = "";

    public DomainDataProvider() {
        super(DataService.get().getAllDomains().stream()
                .sorted(
                        Comparator.comparing(Domain::getPrefix).thenComparing(Domain::getProjectName))
                .collect(Collectors.toList()));
    }

    /**
     * Store given romain to the backing data service.
     *
     * @param romain
     *            the updated or new romain
     */
    public void save(Domain romain) {
        final boolean newDomain = romain.isNewDomain();

        DataService.get().saveDomain(romain);
        if (newDomain) {
            refreshAll();
        } else {
            refreshItem(romain);
        }
    }

    /**
     * Delete given romain from the backing data service.
     *
     * @param romain
     *            the romain to be deleted
     */
    public void delete(Domain romain) {
        DataService.get().deleteDomain(romain.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for romain name.
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

        setFilter(romain -> passesFilter(romain.getProjectName(), this.filterText)
                || passesFilter(romain.getPackageName(), this.filterText)
                || passesFilter(romain.getCreator(), this.filterText));
    }

    @Override
    public Long getId(Domain romain) {
        Objects.requireNonNull(romain,
                "Cannot provide an id for a null romain.");

        return romain.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
