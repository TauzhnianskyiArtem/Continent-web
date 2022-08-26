package ga.continent.service.listener;

import com.google.gson.Gson;
import ga.continent.api.dto.CommentDto;
import ga.continent.api.dto.EventType;
import ga.continent.service.interfaces.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentListener {

    Gson gson;
    CommentService loggedCommentService;

    @KafkaListener(
            id = "commentListener",
            topics = "${topics.comment}")
    public void kafkaListen(String data) {
        CommentDto commentDto = gson.fromJson(data, CommentDto.class);

        if(EventType.CREATE == commentDto.getEventType())
            loggedCommentService.create(commentDto);
        else if(commentDto.getEventType() == EventType.REMOVE)
            loggedCommentService.deleteMessage(commentDto.getId());
    }
}
