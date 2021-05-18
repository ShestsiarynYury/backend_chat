package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByLogin(String login);
    @Query("SELECT u.foto FROM User u WHERE u.login = ?1")
    public String findFotoByLogin(String login);
}
