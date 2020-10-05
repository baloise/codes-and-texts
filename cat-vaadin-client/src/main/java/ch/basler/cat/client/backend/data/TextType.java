package ch.basler.cat.client.backend.data;

import java.util.Arrays;

public enum TextType {

    ALL(0L), LABEL(1L), MESSAGE(2L), CODE(3L);

    Long value;

    TextType(Long value) {
        this.value = value;
    }

    public static TextType of(Long textTypeValue) {
        return Arrays.stream(TextType.values())
                .filter(textType -> textType.value.equals(textTypeValue))
                .findFirst()
                .orElse(TextType.ALL);
    }

    public Long getValue() {
        return value;
    }
}
