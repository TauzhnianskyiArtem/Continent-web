package ga.continent.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageReadDto {
    String id;
    String text;
    UserReadDto author;
    List<CommentDto> comments;
    String link;
    String linkTitle;
    String linkDescription;
    String linkCover;
    Long timestamp;
}
