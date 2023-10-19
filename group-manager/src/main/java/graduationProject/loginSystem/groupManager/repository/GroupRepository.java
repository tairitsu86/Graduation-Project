package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,String> {
}
