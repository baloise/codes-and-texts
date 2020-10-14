package ch.basler.cat.api.base;

class TextItemMatcher<T> {

    private final T identifier;

    TextItemMatcher(T identifier) {
        this.identifier = identifier;
    }

    boolean matches(TextItem<T> textItem) {
        if (textItem == null) {
            return false;
        }
        return identifier.equals(textItem.getIdentifier());
    }
}
