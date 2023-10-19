package graduationProject.loginSystem.groupManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "group")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group")
public class Group {
    @Id
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "description")
    private String description;
    @Column(name = "visible")
    private boolean visible;
    @Column(name = "join_actively")
    private boolean joinActively;
}
