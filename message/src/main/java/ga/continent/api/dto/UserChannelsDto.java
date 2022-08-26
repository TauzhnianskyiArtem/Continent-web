package ga.continent.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserChannelsDto {
    String userId;
    List<String> channelsId;
}
