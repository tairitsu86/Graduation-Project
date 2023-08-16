package gradutionProject.IMUISystem.loginTracker.repository;

import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser,String> {
}
