package ch.basler.cat.fhir.mapper;

import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.Organization;
import org.springframework.stereotype.Component;

import ch.basler.cat.model.Domain;

@Component
public class OrganizationMapper {

  public static final String SYSTEM_ORGANIZATION_PACKAGE_NAME = "http://basler.ch/fhir/sid/package-name";
  public static final String SYSTEM_ORGANIZATION_PREFIX = "http://basler.ch/fhir/sid/organization-prefix";

  public Organization mapDomain(Domain domain) {
    final Organization organization = new Organization();

    organization.setId(domain.getId().toString());

    
    addSystem(organization, SYSTEM_ORGANIZATION_PACKAGE_NAME, domain.getPackageName());
    addSystem(organization, SYSTEM_ORGANIZATION_PREFIX, domain.getPrefix());
    organization.setName(domain.getProjectName());
    organization.setActive(true);
    addTelecom(organization, ContactPointSystem.EMAIL, domain.getEmail());

    return organization;
  }

  private void addSystem(Organization organization, String system, String value) {
    organization.addIdentifier() //
        .setSystem(system) //
        .setValue(value);
  }

  private void addTelecom(Organization organization, ContactPointSystem system, String value) {
    organization.getContactFirstRep().addTelecom() //
        .setSystem(system) //
        .setValue(value);
  }

}
