package graduationProject.IMUISystem.eventHandler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="menu")
public class Menu {
    @Id
    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "description")
    private String description;

    @Column(name = "options")
    private String options;

    @Column(name = "parameters")
    private String parameters;
}
