package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface GroupRepository extends JpaRepository<Group,String> {

    @Query(value = "SELECT DISTINCT G.[group_id] AS groupId, G.[group_name] AS groupName \n" +
                    "FROM [group_table] AS G\n" +
                    "JOIN [member] AS M ON G.group_id = M.group_id\n" +
                    "WHERE M.username = 'OAO'", nativeQuery = true)
    List<Map<String, String>> getGroupByUsername(String username);
}
