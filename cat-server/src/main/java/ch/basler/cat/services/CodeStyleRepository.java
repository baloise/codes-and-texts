package ch.basler.cat.services;


import ch.basler.cat.model.CodeStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "codeStyle", path = "codeStyle")
public interface CodeStyleRepository extends PagingAndSortingRepository<CodeStyle, String> {

    Page<List<CodeStyle>> findByName(@Param("name") String name, Pageable pageable);

    Page<List<CodeStyle>> findByCodeTypeId(@Param("type") long typeId, Pageable pageable);
}
