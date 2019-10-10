package ch.basler.cat.generator;

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

}
