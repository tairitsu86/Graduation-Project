package gradution_project.login_system.user_database.repository;

import gradution_project.login_system.user_database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, String> {

}
