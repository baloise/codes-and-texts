package ch.basler.cat.fhir.mapper;

import org.flywaydb.core.internal.util.StringUtils;
import org.hl7.fhir.r4.model.ValueSet.ConceptReferenceComponent;
import org.springframework.stereotype.Component;

import ch.basler.cat.model.CodeText;

@Component
public class CodeableConceptMapper {

  public ConceptReferenceComponent mapFromCodeText(CodeText codeText) {
    final ConceptReferenceComponent concept = new ConceptReferenceComponent();

    concept.setId(Long.toString(codeText.getTextId()));
    concept.setCode(Long.toString(codeText.getValue()));
    concept.setDisplay(codeText.getName());

    addDesignation(concept, "de", codeText.getTextD());
    addDesignation(concept, "fr", codeText.getTextF());
    addDesignation(concept, "it", codeText.getTextI());
    addDesignation(concept, "en", codeText.getTextE());

    return concept;
  }

  private void addDesignation(ConceptReferenceComponent concept, String language, String text) {
    if (!StringUtils.hasText(text)) {
      return;
    }

    concept.addDesignation() //
        .setLanguage(language) //
        .setValue(text);
  }

}
