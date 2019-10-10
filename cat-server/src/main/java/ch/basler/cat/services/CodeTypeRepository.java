package ch.basler.cat.services;


import ch.basler.cat.model.CodeType;
import ch.basler.cat.model.InlineCodeValue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "codeType", path = "codeType", excerptProjection = InlineCodeValue.class)
public interface CodeTypeRepository extends PagingAndSortingRepository<CodeType, Long> {

    List<CodeType> findByName(@Param("name") String name);

    List<CodeType> findByResponsiblePrefix(@Param("prefix") String prefix);

}