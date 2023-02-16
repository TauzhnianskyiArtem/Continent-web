package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.SubscriptionReadDto;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;

public interface SubscriptionService {

    ProfileReadDto changeSubscription(UserEntity channel, UserEntity subscriber);

    List<SubscriptionReadDto> getSubscribers(UserEntity channel);

    SubscriptionReadDto changeSubscriptionStatus(UserEntity channel, UserEntity subscriber);
}
