package ch.basler.cat.generator;

import java.util.List;

public class CodeGenerateExample {
    //
    public static void main(String[] args) {
        // "http://localhost:8080/codeType/search/findByResponsiblePrefix?prefix=PABLO"
        String json = new CodeRestLoader().export("localhost", "8080", "PABLO");

        System.out.println(json);

        List<CodeType> codes = new CodeParser().parse(json);

        System.out.println(codes);


    }
}
