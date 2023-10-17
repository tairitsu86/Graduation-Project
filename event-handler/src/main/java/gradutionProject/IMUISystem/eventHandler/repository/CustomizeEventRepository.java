package gradutionProject.IMUISystem.eventHandler.repository;

import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomizeEventRepository extends JpaRepository<CustomizeEvent,String> {
    @Query(value = "SELECT event_name FROM [customize_event]",nativeQuery = true)
    List<String> getAllEventName();
}
