package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.Application;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import ch.basler.cat.client.backend.data.Domain;

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
    void deleteCodeValue(String codeValueId);
    CodeValue getCodeValueById(String codeValueId);


    static DataService get() {
        return RestDataService.getInstance();
    }

}
