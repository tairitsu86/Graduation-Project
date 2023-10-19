package graduationProject.IMUISystem.eventHandler.repository;

import graduationProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomizeEventRepository extends JpaRepository<CustomizeEvent,String> {
    @Query(value = "SELECT event_name FROM [customize_event]",nativeQuery = true)
    List<String> getAllEventName();
}
