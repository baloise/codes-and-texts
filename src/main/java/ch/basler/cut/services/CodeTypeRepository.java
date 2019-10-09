package ch.basler.cut.services;


import ch.basler.cut.model.CodeType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "codeType", path = "codeType")
public interface CodeTypeRepository extends PagingAndSortingRepository<CodeType, Long> {

    List<CodeType> findByName(@Param("name") String name);

}
