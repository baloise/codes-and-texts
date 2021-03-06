package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.*;

import java.util.Collection;
import java.util.Collections;

public class RestDataService extends AbstractRestDataService implements DataService {

    public static final String APPLICATIONS = "applications";
    public static final String DOMAINS = "domains";
    public static final String CODETYPES = "codetypes";
    public static final String CODEVALUES = "codevalues";
    public static final String TEXTS = "texts";
    public static final String CODETEXTS = "codetexts";
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

    @Override
    public Collection<TextData> getAllTexts() {
        return getAllData(TEXTS, TextData[].class);
    }

    @Override
    public void saveText(TextData textData) {
        if (textData.isNewText()) {
            createData(TEXTS, textData, TextData.class);
        } else {
            updateData(TEXTS, textData.getId(), textData);
        }
    }

    @Override
    public void deleteText(long id) {
        deleteData(TEXTS, id);
    }

    @Override
    public TextData getTextById(long id) {
        return getById(TEXTS, id, TextData.class);
    }

    @Override
    public Collection<CodeText> getAllCodeTexts() {
        return getAllData(CODETEXTS, CodeText[].class);
    }

    @Override
    public void saveCodeText(CodeText codeText) {
        if (codeText.isNewCodeText()) {
            createData(CODETEXTS, codeText, CodeText.class);
        } else {
            String uri = combine(CODETYPES, codeText.getType(), CODETEXTS);
            updateData(uri, codeText.getValue(), codeText);
        }
    }

    @Override
    public void deleteCodeText(long type, long value) {
        String uri = combine(CODETYPES, type, CODETEXTS);
        deleteData(uri, value);
    }

    @Override
    public CodeText getCodeTextByIds(long type, long value) {
        String uri = combine(CODETYPES, type, CODETEXTS);
        return getById(uri, value, CodeText.class);
    }
}
