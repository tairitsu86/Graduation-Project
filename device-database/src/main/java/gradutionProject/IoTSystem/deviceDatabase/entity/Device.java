package gradutionProject.IoTSystem.deviceDatabase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="device")
public class Device {
    @Id
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "device_name")
    private String deviceName;
    @Column(name = "device_description")
    private String deviceDescription;
    @Column(name = "owner")
    private String owner;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "grant_permission_groups")
    @Column(name = "grant_permission_group")
    private Set<String> grantPermissionGroup;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "grant_permission_users")
    @Column(name = "grant_permission_user")
    private Set<String> grantPermissionUser;
}
