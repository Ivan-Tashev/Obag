package bg.obag.obag.repo;

import bg.obag.obag.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u.email FROM UserEntity u ORDER BY u.email ASC")
    List<String> findAllUsersEmails();

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.id <> :id")
    Optional<UserEntity> findByEmailExceptId(String email, Long id);
}
