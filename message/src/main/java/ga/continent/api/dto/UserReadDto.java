package ga.continent.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {
    String id;
    String name;
    String userpic;
}
