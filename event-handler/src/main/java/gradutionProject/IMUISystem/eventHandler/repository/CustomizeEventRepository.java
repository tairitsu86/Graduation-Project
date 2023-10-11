package gradutionProject.IMUISystem.eventHandler.repository;

import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomizeEventRepository extends JpaRepository<CustomizeEvent,String> {
    @Query(value = "SELECT event_name FROM [customize_event]",nativeQuery = true)
    List<String> getAllEventName();

    @Query(value = "SELECT variable FROM [customize_event_variables] WHERE event_name = ?1 ORDER BY [variables_order]",nativeQuery = true)
    List<String> getEventVariables(String eventName);
}
