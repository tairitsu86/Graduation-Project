package gradutionProject.IMUISystem.loginTracker.repository;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, IMUserData> {
    List<LoginUser> findByUsername(String username);
}
