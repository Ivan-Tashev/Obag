package bg.obag.obag.repo;

import bg.obag.obag.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.email FROM User u ORDER BY u.email ASC")
    List<String> findAllUsersEmails();
}
