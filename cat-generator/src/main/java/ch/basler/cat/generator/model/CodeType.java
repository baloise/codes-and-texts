package ch.basler.cat.generator.model;

import java.util.ArrayList;
import java.util.List;

public class CodeType {

    private String name;
    private String packageName;
    private Integer id;
    private List<CodeValue> codeValues;

    public CodeType(String name, String packageName, Integer id) {
        this.name = name;
        this.packageName = packageName;
        this.id = id;
        codeValues = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public void addCodeValue(CodeValue cv) {
        this.codeValues.add(cv);
    }

    @Override
    public String toString() {
        return "CodeType{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", id=" + id +
                ", codeValues=" + codeValues +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeType codeType = (CodeType) o;

        if (name != null ? !name.equals(codeType.name) : codeType.name != null) return false;
        if (packageName != null ? !packageName.equals(codeType.packageName) : codeType.packageName != null)
            return false;
        if (id != null ? !id.equals(codeType.id) : codeType.id != null) return false;
        return codeValues != null ? codeValues.equals(codeType.codeValues) : codeType.codeValues == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (codeValues != null ? codeValues.hashCode() : 0);
        return result;
    }
}
