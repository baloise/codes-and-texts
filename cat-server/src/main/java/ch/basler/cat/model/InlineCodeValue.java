package ch.basler.cat.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {CodeType.class})
public interface InlineCodeValue {
    long getId();

    String getName();

    String getPrefix();

    List<CodeValue> getCodeValues();
}
