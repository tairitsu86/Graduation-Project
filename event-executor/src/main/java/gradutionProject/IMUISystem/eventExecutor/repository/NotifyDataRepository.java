package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyDataRepository extends JpaRepository<NotifyConfig,String> {

}
