package ch.basler.cat.client.ui.code;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.CodeText;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link CodeText} entities.
 * <p>
 */
public class CodeTextDataProvider extends ListDataProvider<CodeText> {

    /** Text filter that can be changed separately. */
    private String filterText = "";

    public CodeTextDataProvider(long type, long value) {
        super(load(type, value));
    }

    private static List<CodeText> load(long type, long value) {
        try {
            return Arrays.asList(DataService.get().getCodeTextByIds(type, value));
        }
        catch (RestClientException rce) {
            return Arrays.asList();
        }
    }

    /**
     * Store given codeText to the backing data service.
     *
     * @param codeText
     *            the updated or new codeText
     */
    public void save(CodeText codeText) {
        final boolean newCodeText = codeText.isNewCodeText();

        DataService.get().saveCodeText(codeText);
        if (newCodeText) {
            refreshAll();
        } else {
            refreshItem(codeText);
        }
    }

    /**
     * Delete given codeText from the backing data service.
     *
     * @param codeText
     *            the codeText to be deleted
     */
    public void delete(CodeText codeText) {
        DataService.get().deleteCodeText(codeText.getType(), codeText.getValue());
        refreshAll();
    }

    public void setFilter(Long codeTypeId) {
        super.setFilter(codeText -> codeText.getType().equals(codeTypeId));
    }

    @Override
    public String getId(CodeText codeText) {
        Objects.requireNonNull(codeText,
                "Cannot provide an id for a null codeText.");

        return codeText.getType() + "" +codeText.getValue() + "" + codeText.getTextId();
    }
}
