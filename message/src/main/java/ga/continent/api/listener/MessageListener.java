package ga.continent.api.listener;

import com.google.gson.Gson;
import ga.continent.api.dto.EventType;
import ga.continent.api.dto.MessageCreateDto;
import ga.continent.service.interfaces.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageListener {

    Gson gson;
    MessageService loggedMessageService;

    @KafkaListener(
            id = "messageListener",
            topics = "${topics.message}")
    public void kafkaListen(String data) {
        MessageCreateDto messageDto = gson.fromJson(data, MessageCreateDto.class);

        if (EventType.CREATE == messageDto.getEventType())
            loggedMessageService.createMessage(messageDto);
        else if (EventType.UPDATE == messageDto.getEventType())
            loggedMessageService.updateMessage(messageDto.getId(), messageDto);
        else if (messageDto.getEventType() == EventType.REMOVE)
            loggedMessageService.deleteMessage(messageDto.getId());
    }
}
