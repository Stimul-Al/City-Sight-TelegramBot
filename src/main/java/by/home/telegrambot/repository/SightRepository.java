package by.home.telegrambot.repository;

import by.home.telegrambot.model.Sight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SightRepository extends JpaRepository<Sight, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM sights ORDER BY RANDOM() LIMIT 1")
    Sight getRandomSight();
}
