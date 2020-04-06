package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.Application;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import ch.basler.cat.client.backend.data.Responsible;
import ch.basler.cat.client.backend.mock.MockDataService;

import java.io.Serializable;
import java.util.Collection;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    public abstract Collection<Application> getAllApplications();
    public abstract void updateApplication(Application a);
    public abstract void deleteApplication(long applicationId);
    public abstract Application getApplicationById(long applicationId);
    
    public abstract Collection<Responsible> getAllResponsibles();
    public abstract void updateResponsible(Responsible r);
    public abstract void deleteResponsible(long responsibleId);
    public abstract Responsible getResponsibleById(long responsibleId);

    public abstract Collection<CodeType> getAllCodeTypes();
    public abstract void updateCodeType(CodeType ct);
    public abstract void deleteCodeType(long codeTypeId);
    public abstract CodeType getCodeTypeById(long codeTypeId);


    public abstract Collection<CodeValue> getAllCodeValues();
    public abstract void updateCodeValue(CodeValue ct);
    public abstract void deleteCodeValue(String codeValueId);
    public abstract CodeValue getCodeValueById(String codeValueId);


    public static DataService get() {
        return MockDataService.getInstance();
    }

}
