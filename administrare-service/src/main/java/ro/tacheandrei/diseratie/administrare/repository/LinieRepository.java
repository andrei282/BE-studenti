package ro.tacheandrei.diseratie.administrare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tacheandrei.diseratie.administrare.domain.Linie;

import java.util.List;

public interface LinieRepository extends JpaRepository<Linie, Long> {
    List<Linie> findAllByIdIn(List<Long> idsLinii);
}
