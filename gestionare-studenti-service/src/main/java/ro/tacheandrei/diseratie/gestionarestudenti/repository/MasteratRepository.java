package ro.tacheandrei.diseratie.gestionarestudenti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Masterat;

@Repository
public interface MasteratRepository extends JpaRepository<Masterat, Long> {
}
