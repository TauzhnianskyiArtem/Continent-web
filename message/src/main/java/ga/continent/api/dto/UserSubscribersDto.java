package ga.continent.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSubscribersDto {
    String id;
    String name;
    String userpic;
    List<String> subscribers;
}
