
package by.konovalchik.domesticservice.repository;
import by.konovalchik.domesticservice.entity.Role;
import by.konovalchik.domesticservice.entity.Telephone;
import by.konovalchik.domesticservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserRepository  extends JpaRepository<User, Long>  {


    Optional<User> findByUsername(String username);

    @Query(value="SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE u.email = ?1 AND ur.roles = ?2 ", nativeQuery=true)
    Optional<User> findByEmailAndRoles(String email, String role);


}

