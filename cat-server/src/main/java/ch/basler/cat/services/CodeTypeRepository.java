package ch.basler.cat.services;


import ch.basler.cat.model.CodeType;
import ch.basler.cat.model.InlineCodeValuesAndStyles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "codeType", path = "codeType", excerptProjection = InlineCodeValuesAndStyles.class)
public interface CodeTypeRepository extends PagingAndSortingRepository<CodeType, Long> {

    Page<List<CodeType>> findByName(@Param("name") String name, Pageable pageable);

    Page<List<CodeType>> findByResponsiblePrefix(@Param("prefix") String prefix, Pageable pageable);

}
