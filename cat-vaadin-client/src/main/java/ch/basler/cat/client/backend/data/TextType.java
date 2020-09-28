package ch.basler.cat.client.backend.data;

public enum TextType {

    ALL(null), LABEL(1), MESSAGE(2), CODE(3);

    Integer value;

    TextType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
