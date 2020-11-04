package ch.basler.cat.fhir.mapper;

import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ValueSet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ch.basler.cat.model.CodeType;

@Component
public class ValueSetMapper {

  public ValueSet mapFromBaslerCodeType(CodeType codeType) {
    final ValueSet valueSet = new ValueSet();

    valueSet.setId(new IdType(codeType.getId()));
    valueSet.setStatus(PublicationStatus.ACTIVE);
    setContactDetails(valueSet, codeType.getCreator());
    valueSet.setName(codeType.getName());
    valueSet.addUseContext().setValue(new Reference("Organization/" + codeType.getDomainId()));

    return valueSet;
  }

  private void setContactDetails(ValueSet valueSet, String creator) {
    if (!StringUtils.hasText(creator)) {
      return;
    }

    valueSet.addContact() //
        .setName(creator);
  }

}
