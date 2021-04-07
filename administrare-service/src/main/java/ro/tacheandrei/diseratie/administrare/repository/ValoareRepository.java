package ro.tacheandrei.diseratie.administrare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tacheandrei.diseratie.administrare.domain.Valoare;

import java.util.List;

@Repository
public interface ValoareRepository extends JpaRepository<Valoare, Long> {
    List<Valoare> findAllByCampCodAndCampNomenclatorId(String campCod, long nomenclatorId);
}
