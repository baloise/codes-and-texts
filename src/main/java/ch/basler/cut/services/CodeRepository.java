package ch.basler.cut.services;


import ch.basler.cut.model.CodeTyp;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "code", path = "code")
public interface CodeRepository extends PagingAndSortingRepository<CodeTyp, Long> {

    List<CodeTyp> findByName(@Param("name") String name);

}
