package ro.tacheandrei.diseratie.administrare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;

import java.util.List;
import java.util.Set;

public interface ValoriNomenclatorRepository  extends JpaRepository<ValoriNomenclator, Long>,
        JpaSpecificationExecutor<ValoriNomenclator> {

    List<ValoriNomenclator> findAllByIdNomenclatorInAndIdLinieIn(List<Long> idNomeclatoare, Set<Long> idLinii);

    List<ValoriNomenclator> findAllByIdNomenclator(Long idNom);

    List<ValoriNomenclator> findAllByCod(String cod);

    ValoriNomenclator findByIdNomenclatorAndValoare(Long idNomeclatoar, String valoare);

    ValoriNomenclator findByValoareAndIdLinkedBy(String valoare, Long idLinkedByNom);

    List<ValoriNomenclator> findAllByCampAndIdLinieIn(String camp, List<Long> idLinieList);

    boolean existsByCodAndCampAndValoareAndIdLinieNot(String cod, String camp, String valoare, Long id);

    @Query("SELECT V FROM v_valori_nomenclator V WHERE V.idNomenclator IN" +
            "(SELECT V.idNomenclator FROM v_valori_nomenclator V WHERE V.idLinkedByCamp = " +
            "(SELECT V.idCamp FROM v_valori_nomenclator V WHERE V.valoare = :proiect AND V.camp='cod'))")
    List<ValoriNomenclator> findColumnsByProiect(String proiect);

}
