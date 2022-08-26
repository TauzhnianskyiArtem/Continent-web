package ga.continent.api.mapper;

import ga.continent.api.dto.UserChannelDto;
import ga.message.grpc.UserRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserChannelMapper implements Mapper<UserRequest, UserChannelDto> {


    @Override
    public UserChannelDto map(UserRequest object) {
        return UserChannelDto
                .builder()
                .userId(object.getUserId())
                .channelsId(object.getChannelsIdList())
                .build();
    }
}
