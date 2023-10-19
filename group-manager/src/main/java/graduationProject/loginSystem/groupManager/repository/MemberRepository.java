package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.entity.Member;
import graduationProject.loginSystem.groupManager.entity.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

    List<Member> getMemberByUsername(String username);

    List<Member> getMemberByGroupId(String groupId);

    @Modifying
    @Query(value = "DELETE FROM [member] WHERE group_id = ?1", nativeQuery = true)
    void deleteByGroupId(String groupId);
}
