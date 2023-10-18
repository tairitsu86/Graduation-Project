package gradutionProject.loginSystem.groupManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "member")
@IdClass(MemberId.class)
public class Member {
    @Id
    @Column(name = "group_id")
    private String groupId;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "group_role")
    @Enumerated(EnumType.STRING)
    private GroupRole groupRole;
}
