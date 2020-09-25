package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.*;

import java.util.Collection;
import java.util.Collections;

public class RestDataService extends AbstractRestDataService implements DataService {

    public static final String APPLICATIONS = "applications";
    public static final String RESPONSIBLES = "responsibles";
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
    public Collection<Responsible> getAllResponsibles() {
        return getAllData(RESPONSIBLES, Responsible[].class);
    }

    @Override
    public void saveResponsible(Responsible responsible) {
        if (responsible.isNewResponsible()) {
            createData(RESPONSIBLES, responsible, Responsible.class);
        } else {
            updateData(RESPONSIBLES, responsible.getId(), responsible);
        }
    }

    @Override
    public void deleteResponsible(long responsibleId) {
        deleteData(RESPONSIBLES, responsibleId);
    }

    @Override
    public Responsible getResponsibleById(long responsibleId) {
        return getById(RESPONSIBLES, responsibleId, Responsible.class);
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
        String route = getCodeValueRoute(codeType.getId());
        return getAllData(route, CodeValue[].class);
    }

    @Override
    public void saveCodeValue(CodeValue codeValue) {
        String route = getCodeValueRoute(codeValue.getType());
        if (codeValue.isNewCodeValue()) {
            createData(route, codeValue, CodeValue.class);
        } else {
            updateData(route, codeValue.getValue(), codeValue);
        }
    }

    @Override
    public void deleteCodeValue(long type, long value) {
        String route = getCodeValueRoute(type);
        deleteData(route, value);
    }

    @Override
    public CodeValue getCodeValue(long type, long value) {
        String route = getCodeValueRoute(type);
        return getById(route, value, CodeValue.class);
    }

    private String getCodeValueRoute(long type) {
        return combine(CODETYPES, type, CODEVALUES);
    }

}
