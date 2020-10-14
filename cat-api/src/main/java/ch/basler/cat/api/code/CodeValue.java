package ch.basler.cat.api.code;

import java.util.Objects;

public class CodeValue {

    private final short type;
    private final short value;

    private final String name;

    public CodeValue(CodeType type, short value) {
        this(type.getId(), value, null);
    }

    CodeValue(short type, short value, String name) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    public short getType() {
        return type;
    }

    public short getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeValue other = (CodeValue) o;
        return this.type == other.type && this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return "CodeValue{" +
                "type=" + type +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
