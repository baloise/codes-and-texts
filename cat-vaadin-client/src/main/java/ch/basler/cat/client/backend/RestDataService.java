package ch.basler.cat.client.backend;


import ch.basler.cat.client.backend.data.*;
import ch.basler.cat.client.backend.mock.MockDataService;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

public class RestDataService extends DataService {

    private static final String HOST = "localhost";
    private static final String PORT = "8088";
    private static final String URL_PREFIX = "http://" + HOST + ":" + PORT;
    private static RestDataService INSTANCE;
    private final Gson gson = new Gson();
    private final RestTemplate restTemplate = new RestTemplate();

    public static synchronized DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestDataService();
        }
        return INSTANCE;
    }

    @Override
    public Collection<Application> getAllApplications() {
        final String uri = URL_PREFIX + "/applications";
        String response = restTemplate.getForObject(uri, String.class);
        return Arrays.asList(gson.fromJson(response, Application[].class));
    }

    @Override
    public void updateApplication(Application application) {


        if (application.isNewApplication()) {
            final String uri = URL_PREFIX + "/applications";
            restTemplate.postForObject(uri, application, Application.class);
        } else {
            final String uri = URL_PREFIX + "/applications/" + application.getId();
            restTemplate.put(uri, application);
        }

    }

    @Override
    public void deleteApplication(long applicationId) {
        final String uri = URL_PREFIX + "/applications/" + applicationId;
        restTemplate.delete(uri);
    }

    @Override
    public Application getApplicationById(long applicationId) {
        final String uri = URL_PREFIX + ("/applications/" + applicationId);
        String response = restTemplate.getForObject(uri, String.class);
        return gson.fromJson(response, Application.class);
    }

    @Override
    public Collection<Responsible> getAllResponsibles() {
        return MockDataService.getInstance().getAllResponsibles();
    }

    @Override
    public void updateResponsible(Responsible r) {

    }

    @Override
    public void deleteResponsible(long responsibleId) {

    }

    @Override
    public Responsible getResponsibleById(long responsibleId) {
        return null;
    }

    @Override
    public Collection<CodeType> getAllCodeTypes() {
        return MockDataService.getInstance().getAllCodeTypes();
    }

    @Override
    public void updateCodeType(CodeType ct) {

    }

    @Override
    public void deleteCodeType(long codeTypeId) {

    }

    @Override
    public CodeType getCodeTypeById(long codeTypeId) {
        return null;
    }

    @Override
    public Collection<CodeValue> getAllCodeValues(CodeType codeType) {
        return null;
    }

    @Override
    public void updateCodeValue(CodeValue ct) {

    }

    @Override
    public void deleteCodeValue(String codeValueId) {

    }

    @Override
    public CodeValue getCodeValueById(String codeValueId) {
        return null;
    }
}
