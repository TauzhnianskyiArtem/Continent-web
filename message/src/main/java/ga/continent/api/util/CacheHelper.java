package ga.continent.api.util;

import com.google.gson.Gson;
import ga.continent.api.dto.MessageReadDto;
import ga.message.grpc.MessageRead;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static ga.continent.config.CacheConfig.MESSAGES_CACHE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CacheHelper {

    Gson gson;

    CacheManager cacheManager;

    RedisTemplate<String, String> redisTemplate;

    public Map<String, String> getCache(String cacheName) {
        return redisTemplate
                .keys(cacheName + "*")
                .stream()
                .map(key -> key.substring(cacheName.length() + 2)) // 2 - count colon between cacheName and key
                .collect(Collectors.toMap(key -> key,
                        key -> Objects.requireNonNull(cacheManager.getCache(cacheName).get(key, String.class))));

    }

    public void evictCacheBy(BiPredicate<String, List<MessageReadDto>> match) {

        Map<String, String> map = getCache(MESSAGES_CACHE);
        map.forEach((k,v) -> {
            var value = List.of(gson.fromJson(v, MessageReadDto[].class));
            if(match.test(k, value))
                cacheManager.getCache(MESSAGES_CACHE).evict(k);
        });
    }
}

