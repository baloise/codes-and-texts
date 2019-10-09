package ch.basler.cat.model;

import java.io.Serializable;

public class CodeValueId implements Serializable {
    private long id;
    private long codeTypeId;

    public CodeValueId(long id, long codeTypeId) {
        this.id = id;
        this.codeTypeId = codeTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeValueId that = (CodeValueId) o;

        if (id != that.id) return false;
        return codeTypeId == that.codeTypeId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (codeTypeId ^ (codeTypeId >>> 32));
        return result;
    }
}
