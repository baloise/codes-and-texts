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
}
