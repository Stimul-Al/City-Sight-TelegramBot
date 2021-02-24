package by.home.telegrambot.repository;

import by.home.telegrambot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByChatUserId(Integer chatUserId);
}
