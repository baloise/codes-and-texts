package ch.basler.cat.services;


import ch.basler.cat.model.Responsible;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "responsible", path = "responsible")
public interface ResponsibleRepository extends PagingAndSortingRepository<Responsible, String> {

    List<Responsible> findByPrefix(@Param("prefix") String prefix);

}
