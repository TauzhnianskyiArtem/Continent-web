package ga.continent.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDto {
    String id;
    String text;
    String message;
    UserReadDto author;
    Long timestamp;
    EventType eventType;
}
