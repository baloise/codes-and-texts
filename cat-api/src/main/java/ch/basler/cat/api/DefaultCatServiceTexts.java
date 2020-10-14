package ch.basler.cat.api;

import ch.basler.cat.api.service.json.JsonFileService;
import ch.basler.cat.api.text.CodeText;
import ch.basler.cat.api.text.LabelText;
import ch.basler.cat.api.text.MessageText;

import java.nio.file.Path;

public class DefaultCatServiceTexts extends CatServiceTexts {

    public DefaultCatServiceTexts(Path baseDir) {
        super(
                new JsonFileService<>(baseDir.resolve("codetexts.json"), CodeText[].class),
                new JsonFileService<>(baseDir.resolve("labeltexts.json"), LabelText[].class),
                new JsonFileService<>(baseDir.resolve("messagetexts.json"), MessageText[].class)
        );
    }

}
