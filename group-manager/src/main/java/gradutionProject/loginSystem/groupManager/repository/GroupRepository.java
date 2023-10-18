package gradutionProject.loginSystem.groupManager.repository;

import gradutionProject.loginSystem.groupManager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,String> {
}
