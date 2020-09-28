package ch.basler.cat.client.common.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class UpperCaseNoSpacesConverter implements Converter<String, String> {


    private String errorMsg;

    @Override
    public Result<String> convertToModel(String value, ValueContext context) {
        if (value == null) {
            return Result.ok(null);
        }
        String converted = value.replaceAll(" ", "_");
        converted = converted.trim().toUpperCase();

        if (!converted.matches("^[A-Za-z0-9_]+$")) {
            return Result.error("invalid chars");
        }

        return Result.ok(converted);
    }

    @Override
    public String convertToPresentation(String value, ValueContext context) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
