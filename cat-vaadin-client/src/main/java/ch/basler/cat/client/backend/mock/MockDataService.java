package ch.basler.cat.client.backend.mock;

import ch.basler.cat.client.backend.DataService;
import ch.basler.cat.client.backend.data.Application;
import ch.basler.cat.client.backend.data.CodeType;
import ch.basler.cat.client.backend.data.CodeValue;
import ch.basler.cat.client.backend.data.Responsible;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService implements DataService {

    private static MockDataService INSTANCE;

    private List<Application> applications;
    private List<Responsible> responsibles;
    private List<CodeType> codeTypes;

    private int nextProductId = 0;
    private int nextCategoryId = 0;
    private int nextApplicationId = 0;
    private int nextResponsibleId = 0;
    private int nextCodeTypeId = 0;
    private List<CodeValue> allCodeValues;

    private MockDataService() {

        applications = MockDataGenerator.createApplications();
        responsibles = MockDataGenerator.createResponsibles();
        codeTypes = MockDataGenerator.createCodeTypes();

        nextApplicationId = applications.size() + 1;
        nextResponsibleId = responsibles.size() + 1;
        nextCodeTypeId = codeTypes.size() + 1;
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
        return INSTANCE;
    }


    @Override
    public synchronized List<Application> getAllApplications() {
        return Collections.unmodifiableList(applications);
    }

    @Override
    public synchronized Application getApplicationById(long applicationId) {
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getId() == applicationId) {
                return applications.get(i);
            }
        }
        return null;
    }


    @Override
    public synchronized void saveApplication(Application a) {
        if (a.getId() < 0) {
            // New Application
            a.setId(nextApplicationId++);
            a.setCreator("B006969");
            applications.add(a);
            return;
        }
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getId() == a.getId()) {
                applications.set(i, a);
                return;
            }
        }

        throw new IllegalArgumentException("No application with id " + a.getId()
                + " found");
    }

    @Override
    public synchronized void deleteApplication(long applicationId) {
        Application p = getApplicationById(applicationId);
        if (p == null) {
            throw new IllegalArgumentException("Application with id " + applicationId
                    + " not found");
        }
        applications.remove(p);
    }


    @Override
    public Collection<Responsible> getAllResponsibles() {
        return Collections.unmodifiableList(responsibles);
    }

    @Override
    public synchronized Responsible getResponsibleById(long responsibleId) {
        for (int i = 0; i < responsibles.size(); i++) {
            if (responsibles.get(i).getId() == responsibleId) {
                return responsibles.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void saveResponsible(Responsible a) {
        if (a.getId() < 0) {
            // New Responsible
            a.setId(nextResponsibleId++);
            a.setCreator("B006969");
            responsibles.add(a);
            return;
        }
        for (int i = 0; i < responsibles.size(); i++) {
            if (responsibles.get(i).getId() == a.getId()) {
                responsibles.set(i, a);
                return;
            }
        }

        throw new IllegalArgumentException("No responsible with id " + a.getId()
                + " found");
    }

    @Override
    public synchronized void deleteResponsible(long responsibleId) {
        Responsible p = getResponsibleById(responsibleId);
        if (p == null) {
            throw new IllegalArgumentException("Responsible with id " + responsibleId
                    + " not found");
        }
        responsibles.remove(p);
    }

    @Override
    public Collection<CodeType> getAllCodeTypes() {
        return Collections.unmodifiableList(codeTypes);
    }

    @Override
    public synchronized CodeType getCodeTypeById(long codeTypeId) {
        for (int i = 0; i < codeTypes.size(); i++) {
            if (codeTypes.get(i).getId() == codeTypeId) {
                return codeTypes.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void saveCodeType(CodeType a) {
        if (a.getId() < 0) {
            // New CodeType
            a.setId(nextCodeTypeId++);
            a.setCreator("B006969");
            codeTypes.add(a);
            return;
        }
        for (int i = 0; i < codeTypes.size(); i++) {
            if (codeTypes.get(i).getId() == a.getId()) {
                codeTypes.set(i, a);
                return;
            }
        }

        throw new IllegalArgumentException("No codeType with id " + a.getId()
                + " found");
    }

    @Override
    public synchronized void deleteCodeType(long codeTypeId) {
        CodeType p = getCodeTypeById(codeTypeId);
        if (p == null) {
            throw new IllegalArgumentException("CodeType with id " + codeTypeId
                    + " not found");
        }
        codeTypes.remove(p);
    }
    @Override
    public synchronized Collection<CodeValue> getAllCodeValues(CodeType codeType){
        if (codeType == null) {
            return Collections.EMPTY_LIST;
        }
        else {
            updateAllCodeValues();
            return codeType.getCodeValues();
        }
    }

    private void updateAllCodeValues() {
        allCodeValues = getAllCodeTypes().stream()
                .flatMap(ct -> ct.getCodeValues().stream())
                .collect(toList());
    }

    @Override
    public synchronized CodeValue getCodeValueById(String codeValueId) {

        List<CodeValue> codeValues = getAllCodeTypes().stream()
                .flatMap(ct -> ct.getCodeValues().stream())
                .collect(toList());

        return codeValues.stream()
                .filter(cv -> cv.getId().equals(codeValueId))
                .findFirst().get();
    }

    @Override
    public synchronized void saveCodeValue(CodeValue codeValue) {
        CodeType codeType = codeValue.getCodeType();
        if (codeValue.getId().isEmpty()) {
            // New CodeValue
            Integer maxId = codeType.getCodeValues().stream()
                    .map(CodeValue::getId)
                    .map(id -> id.split(":")[1])
                    .map(Integer::valueOf)
                    .reduce(1, (one, two) -> Math.max(one, two));
            Integer nextId = maxId + 1;
            codeValue.setId(codeType.getId() + ":" + nextId);
            codeValue.setCreator("B006969");
            codeValue.setValue(nextId);
            codeType.getCodeValues().add(codeValue);
            updateAllCodeValues();
            return;
        }
        for (int i = 0; i < allCodeValues.size(); i++) {
            List<CodeValue> codeValues = codeType.getCodeValues();
            if (codeValues.get(i).getId() == codeValue.getId()) {
                codeValues.set(i, codeValue);
                return;
            }
        }

        throw new IllegalArgumentException("No codeValue with id " + codeValue.getId()
                + " found");
    }

    @Override
    public synchronized void deleteCodeValue(String codeValueId) {
        CodeValue codeValue = getCodeValueById(codeValueId);
        if (codeValue == null) {
            throw new IllegalArgumentException("CodeValue with id " + codeValueId
                    + " not found");
        }
        codeValue.getCodeType().getCodeValues().remove(codeValue);
    }
}
