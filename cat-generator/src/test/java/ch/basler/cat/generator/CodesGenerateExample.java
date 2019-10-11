package ch.basler.cat.generator;

import ch.basler.cat.generator.model.CodeType;

import java.io.IOException;
import java.util.List;

public class CodesGenerateExample {
    //
    public static void main(String[] args) {
        // "http://localhost:8080/codeType/search/findByResponsiblePrefix?prefix=PABLO"
        String json = new CodesRestLoader().export("localhost", "8080", "PABLO");

        System.out.println(json);

        List<CodeType> codes = new CodesParser().parse(json);

        System.out.println(codes);

        try {
            new LegacyCodesGenerator().generate(codes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
