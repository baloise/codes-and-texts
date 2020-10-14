package ch.basler.cat.api.base;

import ch.basler.cat.api.service.CatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TextItemProvider<T extends TextItem<I>, I> {

    private final List<T> items = new ArrayList<>();

    private final CatService<T> service;

    public TextItemProvider(CatService<T> service) {
        this.service = service;
    }

    public void clear() {
        items.clear();
    }

    private List<T> getItems() {
        if (items.isEmpty()) {
            items.addAll(service.getData());
        }
        return items;
    }

    public Optional<T> getItem(I identifier) {
        TextItemMatcher<I> matcher = new TextItemMatcher<>(identifier);
        return getItems().stream().filter(matcher::matches).findFirst();
    }

}
