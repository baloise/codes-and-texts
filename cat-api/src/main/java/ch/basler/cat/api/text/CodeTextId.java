package ch.basler.cat.api.text;

import ch.basler.cat.api.code.CodeValue;

import java.util.Objects;

public class CodeTextId {

    private final long type;
    private final long value;

    public static CodeTextId fromCodeValue(CodeValue codeValue) {
        return new CodeTextId(codeValue.getType(), codeValue.getValue());
    }

    public CodeTextId(long type, long value) {
        this.type = type;
        this.value = value;
    }

    public long getType() {
        return type;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CodeTextId{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeTextId that = (CodeTextId) o;
        return type == that.type &&
                value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
