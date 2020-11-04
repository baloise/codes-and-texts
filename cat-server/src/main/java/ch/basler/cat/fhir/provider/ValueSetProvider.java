package ch.basler.cat.fhir.provider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.ValueSet;
import org.hl7.fhir.r4.model.ValueSet.ConceptSetComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import ch.basler.cat.fhir.mapper.CodeableConceptMapper;
import ch.basler.cat.fhir.mapper.ValueSetMapper;
import ch.basler.cat.services.CodeTextRepository;
import ch.basler.cat.services.CodeTypeRepository;

@Component
public class ValueSetProvider implements IResourceProvider {

  @Autowired
  private CodeTextRepository codeTextRepository;

  @Autowired
  private CodeTypeRepository codeTypeRepository;

  @Autowired
  private ValueSetMapper valueSetMapper;

  @Autowired
  private CodeableConceptMapper codeableConceptMapper;

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return ValueSet.class;
  }

  @Read
  public ValueSet findById(@IdParam IdType id) {
    final ValueSet valueSet = codeTypeRepository.findById(id.getIdPartAsLong()) //
        .map(valueSetMapper::mapFromBaslerCodeType) //
        .orElseThrow(() -> new ResourceNotFoundException(id));

    fillInCodes(valueSet);

    return valueSet;
  }

  @Search
  public ValueSet findByName(@RequiredParam(name = ValueSet.SP_NAME) StringParam name) {
    final ValueSet valueSet = codeTypeRepository.findByName(name.getValue()) //
        .map(valueSetMapper::mapFromBaslerCodeType) //
        .orElseThrow(() -> new ResourceNotFoundException(name + " not found"));

    fillInCodes(valueSet);

    return valueSet;
  }

  @Search
  public List<ValueSet> getAll() {
    final List<ValueSet> valueSets = StreamSupport.stream(codeTypeRepository.findAll().spliterator(), true) //
        .map(valueSetMapper::mapFromBaslerCodeType) //
        .collect(Collectors.toList());

    return valueSets;
  }

  private void fillInCodes(ValueSet valueSet) {
    final ConceptSetComponent include = valueSet.getCompose().addInclude();

    codeTextRepository.findByType(valueSet.getIdElement().getIdPartAsLong()).stream() //
        .map(codeableConceptMapper::mapFromCodeText) //
        .forEach(include::addConcept);
  }

}
