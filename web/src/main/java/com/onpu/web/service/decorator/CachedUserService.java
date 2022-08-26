package com.onpu.web.service.decorator;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.onpu.web.config.CacheConfig.USERS_CACHE;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CachedUserService implements UserService {


    public CachedUserService(UserService userServiceImpl, CacheManager cacheManager) {
        this.userServiceImpl = userServiceImpl;
        this.usersCache = cacheManager.getCache(USERS_CACHE);
    }

    UserService userServiceImpl;
    Cache usersCache;

    @Override
    public Optional<UserEntity> findById(String id) {
        if(!Objects.isNull(usersCache.get(id))){
            return Optional.ofNullable(usersCache.get(id, UserEntity.class));
        }

        Optional<UserEntity> user = userServiceImpl.findById(id);

        user.ifPresent( u -> usersCache.put(u.getId(), u));

        return user;
    }

    @Override
    public UserEntity getById(String id) {
        return this.findById(id).orElseThrow();
    }

    @Override
    public UserReadDto getOauthUser(String id) {
        return userServiceImpl.getOauthUser(id);
    }

    @Override
    public ProfileReadDto getProfile(String userId) {
        return userServiceImpl.getProfile(userId);
    }

    @Override
    public List<UserReadDto> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @Override
    public UserEntity create(UserEntity user) {
        userServiceImpl.create(user);
        usersCache.put(user.getId(), user);
        return user;
    }

    @Override
    public UserEntity uploadAvatar(MultipartFile image, UserEntity user) {
        if (!Objects.isNull(usersCache.get(user.getId()))) {
            usersCache.evict(user.getId());
        }

        UserEntity savedUser = userServiceImpl.uploadAvatar(image, user);

        usersCache.put(savedUser.getId(), savedUser);
        return savedUser;
    }

    @Override
    public Optional<byte[]> findAvatar(String id) {
        return userServiceImpl.findAvatar(id);
    }


    public UserEntity saveSubscription(UserEntity channel, UserEntity subscriber) {
        if (!Objects.isNull(usersCache.get(subscriber.getId()))){
            usersCache.evict(subscriber.getId());
        }

        UserEntity savedChannel = userServiceImpl.create(channel);

        usersCache.put(savedChannel.getId(), savedChannel);
        return savedChannel;
    }
}
