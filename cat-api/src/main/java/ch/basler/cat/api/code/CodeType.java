package ch.basler.cat.api.code;

import java.util.Objects;

public class CodeType {

    private final short id;

    private final String name;
    private final Short domain;

    public CodeType(short id) {
        this(id, null, null);
    }

    CodeType(short id, String name, Short domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Short getDomain() {
        return domain;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeType other = (CodeType) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CodeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", domain=" + domain +
                '}';
    }
}
