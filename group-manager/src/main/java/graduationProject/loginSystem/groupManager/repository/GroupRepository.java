package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.dto.GroupDetailDto;
import graduationProject.loginSystem.groupManager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group,String> {
    @Query("SELECT new graduationProject.loginSystem.groupManager.dto.GroupDetailDtoProject.loginSystem.groupManager.dto.GroupDetailDto(g.group_id, g.group_name, g.description) FROM `group` AS g WHERE group_id = ?1")
    GroupDetailDto getGroupDetail(String groupId);
}
