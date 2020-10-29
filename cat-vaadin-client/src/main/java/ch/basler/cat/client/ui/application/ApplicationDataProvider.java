package ch.basler.cat.client.ui.application;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Application;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link Application} entities.
 * <p>
 */
public class ApplicationDataProvider extends ListDataProvider<Application> {

    private final DataService dataService;
    /** Text filter that can be changed separately. */
    private String filterText = "";

    public ApplicationDataProvider(DataService dataService) {
        super(dataService.getAllApplications().stream()
                .sorted(
                    Comparator.comparing(Application::getName).thenComparing(Application::getPackageName))
                .collect(Collectors.toList()));

        this.dataService = dataService;
    }

    /**
     * Store given application to the backing data service.
     *
     * @param application
     *            the updated or new application
     */
    public void save(Application application) {
        final boolean newApplication = application.isNewApplication();

        dataService.saveApplication(application);
        if (newApplication) {
            refreshAll();
        } else {
            refreshItem(application);
        }
    }

    /**
     * Delete given application from the backing data service.
     *
     * @param application
     *            the application to be deleted
     */
    public void delete(Application application) {
        dataService.deleteApplication(application.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for application name.
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

        setFilter(application -> passesFilter(application.getName(), this.filterText)
                || passesFilter(application.getPackageName(), this.filterText)
                || passesFilter(application.getCreator(), this.filterText));
    }

    @Override
    public Long getId(Application application) {
        Objects.requireNonNull(application,
                "Cannot provide an id for a null application.");

        return application.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
