package gradutionProject.loginSystem.groupManager.repository;

import gradutionProject.loginSystem.groupManager.entity.Member;
import gradutionProject.loginSystem.groupManager.entity.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
    List<String> getUsernameByGroupId(String groupId);

    void deleteByGroupId(String groupId);
}
