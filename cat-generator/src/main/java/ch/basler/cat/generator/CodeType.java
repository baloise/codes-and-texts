package ch.basler.cat.generator;

import java.util.ArrayList;
import java.util.List;

public class CodeType {

    private String name;
    private List<CodeValue> codeValues;

    public CodeType(String name) {
        this.name = name;
        codeValues = new ArrayList<>();
    }

    public void addCodeValue(CodeValue cv) {
        this.codeValues.add(cv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeType codeType = (CodeType) o;

        if (name != null ? !name.equals(codeType.name) : codeType.name != null) return false;
        return codeValues != null ? codeValues.equals(codeType.codeValues) : codeType.codeValues == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (codeValues != null ? codeValues.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CodeType{" +
                "name='" + name + '\'' +
                ", codeValues=" + codeValues +
                '}';
    }
}
