package ro.tacheandrei.diseratie.administrare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NomenclatorRepository extends JpaRepository<Nomenclator, Long> , PagingAndSortingRepository<Nomenclator, Long>, JpaSpecificationExecutor<Nomenclator> {

    Optional<Nomenclator> findByCod(String cod);

    List<Nomenclator> findByIdIn(Set<Long> ids);
}
