package gradutionProject.IMIGSystem.lineService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SendingServiceDto {
    private List<String> userIdList;
    private String message;
}
