package ga.continent.api.mapper;

import ga.continent.api.dto.UserReadDto;
import ga.continent.api.dto.UserSubscribersDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserSubscribersMapper implements Mapper<UserSubscribersDto, UserReadDto> {

    @Override
    public UserReadDto map(UserSubscribersDto object) {
        return UserReadDto
                .builder()
                .id(object.getId())
                .userpic(object.getUserpic())
                .name(object.getName())
                .build();
    }

}
