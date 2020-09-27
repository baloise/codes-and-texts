package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.*;

import java.util.Collection;
import java.util.Collections;

public class RestDataService extends AbstractRestDataService implements DataService {

    public static final String APPLICATIONS = "applications";
    public static final String DOMAINS = "domains";
    public static final String CODETYPES = "codetypes";
    public static final String CODEVALUES = "codevalues";
    private static RestDataService INSTANCE;

    public static synchronized DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestDataService();
        }
        return INSTANCE;
    }

    @Override
    public Collection<Application> getAllApplications() {
        return getAllData(APPLICATIONS, Application[].class);
    }

    @Override
    public void saveApplication(Application application) {
        if (application.isNewApplication()) {
            createData(APPLICATIONS, application, Application.class);
        } else {
            updateData(APPLICATIONS, application.getId(), application);
        }
    }

    @Override
    public void deleteApplication(long applicationId) {
        deleteData(APPLICATIONS, applicationId);
    }

    @Override
    public Application getApplicationById(long applicationId) {
        return getById(APPLICATIONS, applicationId, Application.class);
    }

    @Override
    public Collection<Domain> getAllDomains() {
        return getAllData(DOMAINS, Domain[].class);
    }

    @Override
    public void saveDomain(Domain domain) {
        if (domain.isNewDomain()) {
            createData(DOMAINS, domain, Domain.class);
        } else {
            updateData(DOMAINS, domain.getId(), domain);
        }
    }

    @Override
    public void deleteDomain(long domainId) {
        deleteData(DOMAINS, domainId);
    }

    @Override
    public Domain getDomainById(long domainId) {
        return getById(DOMAINS, domainId, Domain.class);
    }

    @Override
    public Collection<CodeType> getAllCodeTypes() {
        return getAllData(CODETYPES, CodeType[].class);
    }

    @Override
    public void saveCodeType(CodeType codeType) {
        if (codeType.isNewCodeType()) {
            createData(CODETYPES, codeType, CodeType.class);
        } else {
            updateData(CODETYPES, codeType.getId(), codeType);
        }
    }

    @Override
    public void deleteCodeType(long codeTypeId) {
        deleteData(CODETYPES, codeTypeId);
    }

    @Override
    public CodeType getCodeTypeById(long codeTypeId) {
        return getById(CODETYPES, codeTypeId, CodeType.class);
    }

    @Override
    public Collection<CodeValue> getAllCodeValues(CodeType codeType) {
        if (codeType == null) {
            return Collections.emptyList();
        }
        String route = CODETYPES + "/" + codeType.getId() + "/" + CODEVALUES;
        return getAllData(route, CodeValue[].class);
    }

    @Override
    public void saveCodeValue(CodeValue codeValue) {
        if (codeValue.isNewCodeValue()) {
            createData(CODEVALUES, codeValue, CodeValue.class);
        } else {
            updateData(CODEVALUES, codeValue.getId(), codeValue);
        }
    }

    @Override
    public void deleteCodeValue(String codeValueId) {
        deleteData(CODEVALUES, codeValueId);
    }

    @Override
    public CodeValue getCodeValueById(String codeValueId) {
        return getById(CODEVALUES, codeValueId, CodeValue.class);
    }
}
