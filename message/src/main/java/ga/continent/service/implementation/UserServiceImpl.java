package ga.continent.service.implementation;

import ga.continent.api.dto.MessageReadDto;
import ga.continent.api.dto.UserReadDto;
import ga.continent.api.mapper.UserReadMapper;
import ga.continent.service.interfaces.UserService;
import ga.continent.service.util.CacheHelper;
import ga.continent.store.entity.UserEntity;
import ga.continent.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserReadMapper userReadMapper;

    CacheHelper cacheHelper;


    @Override
    public UserEntity getUser(UserReadDto userReadDto) {

        UserEntity user = userRepository.findById(userReadDto.getId()).orElseGet(() -> userReadMapper.map(userReadDto));
        return create(user);
    }

    @Transactional
    @Override
    public UserEntity create(UserEntity user) {

        cacheHelper.evictCacheBy((userId, messages) ->
                userId.equals(user.getId()) || isAuthorMessages(user, userId, messages));
        return userRepository.saveAndFlush(user);
    }

    private boolean isAuthorMessages(UserEntity user, String userId, List<MessageReadDto> messages) {
        return messages.stream()
                .map(MessageReadDto::getAuthor)
                .map(UserReadDto::getId)
                .anyMatch(authorId -> userId.equals(user.getId()));
    }
}
