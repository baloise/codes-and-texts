package ch.basler.cat.generator.codes.model;

public class CodeValue {
    String name;
    Integer code;

    public CodeValue(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeValue codeValue = (CodeValue) o;

        if (name != null ? !name.equals(codeValue.name) : codeValue.name != null) return false;
        return code != null ? code.equals(codeValue.code) : codeValue.code == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CodeValue{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
