package ch.basler.cat.fhir.provider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import ch.basler.cat.fhir.mapper.OrganizationMapper;
import ch.basler.cat.services.DomainRepository;

@Component
public class OrganizationProvider implements IResourceProvider {

  @Autowired
  private DomainRepository domainRepository;

  @Autowired
  private OrganizationMapper organizationMapper;

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Organization.class;
  }

  @Read
  public Organization findById(@IdParam IdType id) {
    final Organization organization = domainRepository.findById(id.getIdPartAsLong()) //
        .map(organizationMapper::mapDomain) //
        .orElseThrow(() -> new ResourceNotFoundException(id));

    return organization;
  }

  @Search
  public List<Organization> getAll() {
    final List<Organization> organizations = StreamSupport.stream(domainRepository.findAll().spliterator(), true) //
        .map(organizationMapper::mapDomain) //
        .collect(Collectors.toList());

    return organizations;
  }

}
