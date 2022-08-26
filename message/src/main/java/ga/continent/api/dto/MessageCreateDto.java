package ga.continent.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageCreateDto {
    String id;
    String text;
    UserSubscribersDto author;
    Long timestamp;
    EventType eventType;
    String link;
    String linkTitle;
    String linkDescription;
    String linkCover;
}
