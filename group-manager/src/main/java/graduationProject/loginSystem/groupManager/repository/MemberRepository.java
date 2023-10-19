package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.entity.Member;
import graduationProject.loginSystem.groupManager.entity.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
    List<String> getUsernameByGroupId(String groupId);

    List<String> getGroupIdByUsername(String username);

    void deleteByGroupId(String groupId);
}
