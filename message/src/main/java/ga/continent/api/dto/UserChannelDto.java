package ga.continent.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserChannelDto {
    String userId;
    List<String> channelsId;
}
