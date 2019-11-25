package ch.basler.cat.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {CodeType.class})
public interface InlineCodeValuesAndStyles {
    long getId();

    String getName();

    String getPrefix();

    String getPackageName();

    List<CodeValue> getCodeValues();
    List<CodeStyle> getCodeStyles();
}
