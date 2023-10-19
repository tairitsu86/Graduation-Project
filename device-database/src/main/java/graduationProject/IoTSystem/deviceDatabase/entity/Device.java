package graduationProject.IoTSystem.deviceDatabase.entity;

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

    @Column(name = "description")
    private String description;

    @Column(name = "owner")
    private String owner;

    @Column(name = "states")
    private String states;

    @Column(name = "functions")
    private String functions;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "device_permission_groups", joinColumns = @JoinColumn(name = "device_id"))
    @Column(name = "group_id")
    private Set<String> groups;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "device_permission_users", joinColumns = @JoinColumn(name = "device_id"))
    @Column(name = "username")
    private Set<String> users;
}
