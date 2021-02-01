package by.home.telegrambot.repository;

import by.home.telegrambot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM cities ORDER BY RAND() LIMIT 1")
    City getByRandomCity();
}
