package ch.basler.cat.services;


import ch.basler.cat.model.CodeValue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "codeValue", path = "codeValue")
public interface CodeValueRepository extends PagingAndSortingRepository<CodeValue, String> {

    List<CodeValue> findByName(@Param("name") String name);

}
