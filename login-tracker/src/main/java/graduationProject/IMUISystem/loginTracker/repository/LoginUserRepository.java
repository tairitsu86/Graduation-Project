package graduationProject.IMUISystem.loginTracker.repository;

import graduationProject.IMUISystem.loginTracker.entity.LoginUser;
import graduationProject.IMUISystem.loginTracker.entity.IMUserData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, IMUserData> {
    LoginUser findByUsername(String username);
    boolean existsByUsername(String username);
    @Transactional
    void deleteByUsername(String username);
}
