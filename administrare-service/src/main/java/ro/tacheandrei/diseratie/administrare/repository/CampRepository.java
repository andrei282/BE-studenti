package ro.tacheandrei.diseratie.administrare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.tacheandrei.diseratie.administrare.domain.Camp;

import java.util.List;

public interface CampRepository extends JpaRepository<Camp, Long>, JpaSpecificationExecutor<Camp> {

    List<Camp> findAllByNomenclatorId(Long id);

    List<Camp> findAllByLinkId(Long id);

}
