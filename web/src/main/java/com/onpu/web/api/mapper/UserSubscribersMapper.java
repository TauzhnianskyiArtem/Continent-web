package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.UserSubscribersDto;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserSubscribersMapper implements Mapper<UserEntity, UserSubscribersDto>{
    @Override
    public UserSubscribersDto map(UserEntity object) {
        return UserSubscribersDto
                .builder()
                .id(object.getId())
                .name(object.getName())
                .userpic(object.getUserpic())
                .subscribers(getSubscriptions(object))
                .build();
    }

    private List<String> getSubscriptions(UserEntity object) {
        return object.getSubscribers()
                .stream()
                .filter(UserSubscriptionEntity::isActive)
                .map(s -> s.getSubscriber().getId())
                .collect(Collectors.toList());
    }
}
