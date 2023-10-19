package graduationProject.loginSystem.userDatabase.repository;

import graduationProject.loginSystem.userDatabase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, String> {

}
