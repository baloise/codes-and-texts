package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.Application;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import ch.basler.cat.client.backend.data.Responsible;

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
    
    Collection<Responsible> getAllResponsibles();
    void saveResponsible(Responsible r);
    void deleteResponsible(long responsibleId);
    Responsible getResponsibleById(long responsibleId);

    Collection<CodeType> getAllCodeTypes();
    void saveCodeType(CodeType ct);
    void deleteCodeType(long codeTypeId);
    CodeType getCodeTypeById(long codeTypeId);


    Collection<CodeValue> getAllCodeValues(CodeType codeType);
    void saveCodeValue(CodeValue codeValue);
    void deleteCodeValue(long type,long value);
    CodeValue getCodeValue(long type, long value);


    static DataService get() {
        return RestDataService.getInstance();
    }

}
