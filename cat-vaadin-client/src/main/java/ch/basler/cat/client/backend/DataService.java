package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public interface DataService extends Serializable {

    Collection<Application> getAllApplications();
    void saveApplication(Application a);
    void deleteApplication(long applicationId);
    Application getApplicationById(long applicationId);

    Collection<Domain> getAllDomains();
    void saveDomain(Domain r);
    void deleteDomain(long domainId);
    Domain getDomainById(long domainId);

    Collection<CodeType> getAllCodeTypes();
    void saveCodeType(CodeType ct);
    void deleteCodeType(long codeTypeId);
    CodeType getCodeTypeById(long codeTypeId);


    Collection<CodeValue> getAllCodeValues(CodeType codeType);
    void saveCodeValue(CodeValue codeValue);
    void deleteCodeValue(long type,long value);
    CodeValue getCodeValue(long type, long value);

    Collection<TextData> getAllTexts();
    void saveText(TextData textData);
    void deleteText(long id);
    TextData getTextById(long id);

    Collection<CodeText> getAllCodeTexts();
    void saveCodeText(CodeText codeText);
    void deleteCodeText(long type, long value);
    CodeText getCodeTextByIds(long type, long value);
}
