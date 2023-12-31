package com.ssafy.coffeeing.modules.feed.util;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class FeedRedisUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, Long, HashMap<Long, Boolean>> hashOperations;
    private final FeedLikeRepository feedLikeRepository;
    private static final String KEY = "feedLike";

    public boolean isLikedFeedInRedis(Feed feed, Member member) {
        HashMap<Long, Boolean> feedLikeCache = hashOperations.get(KEY, feed.getId());
        if(Objects.isNull(feedLikeCache)) feedLikeCache = new HashMap<>();

        if(feedLikeCache.containsKey(member.getId())) {
            return feedLikeCache.get(member.getId());
        }

        setFeedLikeStatus(feed, member, feedLikeCache);

        return feedLikeCache.get(member.getId());
    }

    public boolean isNotLikedFeedInRedis(Feed feed, Member member) {
        return !isLikedFeedInRedis(feed, member);
    }

    public void disLikeFeedInRedis(Feed feed, Member member) {
        HashMap<Long, Boolean> feedLikeCache = hashOperations.get(KEY, feed.getId());

        if (Objects.isNull(feedLikeCache)) feedLikeCache = new HashMap<>();

        feedLikeCache.put(member.getId(), false);
        hashOperations.put(KEY, feed.getId(), feedLikeCache);
    }

    public void disLikeFeedInRedis(Feed feed) {
        HashMap<Long, Boolean> feedLikeCache = hashOperations.get(KEY, feed.getId());

        if (Objects.nonNull(feedLikeCache)) {
            feedLikeCache.replaceAll((i, v) -> false);
            hashOperations.put(KEY, feed.getId(), feedLikeCache);
        }
    }

    public void likeFeedInRedis(Feed feed, Member member) {
        HashMap<Long, Boolean> feedLikeCache = hashOperations.get(KEY, feed.getId());

        if (!Objects.nonNull(feedLikeCache)) {
            feedLikeCache = new HashMap<>();
        }
        feedLikeCache.put(member.getId(), true);
        hashOperations.put(KEY, feed.getId(), feedLikeCache);
    }

    public void decreaseLikeCount(Feed feed) {
        String value = redisTemplate.opsForValue().get(KEY + feed.getId());
        int likeCount = Objects.isNull(value) ? feed.getLikeCount() : Integer.parseInt(value);

        if(likeCount > 0) redisTemplate.opsForValue().set(KEY + feed.getId(), String.valueOf(likeCount - 1));
    }

    public void increaseLikeCount(Feed feed) {
        String value = redisTemplate.opsForValue().get(KEY + feed.getId());
        int likeCount = Objects.isNull(value) ? feed.getLikeCount() : Integer.parseInt(value);

        redisTemplate.opsForValue().set(KEY + feed.getId(), String.valueOf(likeCount + 1));
    }

    public int getFeedLikeCount(Feed feed) {
        String value = redisTemplate.opsForValue().get(KEY + feed.getId());
        int likeCount;

        if(Objects.isNull(value)) {
            likeCount = feed.getLikeCount();
            redisTemplate.opsForValue().set(KEY + feed.getId(), String.valueOf(likeCount));
        } else {
            likeCount = Integer.parseInt(value);
        }

        return likeCount;
    }

    public Set<Long> getFeedKeys() {
        return hashOperations.keys(KEY);
    }

    public HashMap<Long, Boolean> getHashMap(Long feedId) {
        return hashOperations.get(KEY, feedId);
    }

    public void updateFeedLikeCount(Feed feed) {
        String value = redisTemplate.opsForValue().get(KEY + feed.getId());

        if(Objects.nonNull(value)) {
            Integer likeCount = Integer.valueOf(value);
            feed.updateLikeCount(likeCount);
            redisTemplate.delete(KEY + feed.getId());
        }
    }

    public void deleteFeedLikeKey() {
        redisTemplate.delete(KEY);
    }

    private boolean isNotSetExpireTime() {
        if (Objects.nonNull(redisTemplate.getExpire(KEY))) {
            return Objects.requireNonNull(redisTemplate.getExpire(KEY)).equals(-1L);
        }
        return false;
    }

    private void setFeedLikeStatus(Feed feed, Member member, HashMap<Long, Boolean> feedLikeCache) {
        Optional<FeedLike> feedLike = feedLikeRepository.findFeedLikeByFeedAndMember(feed, member);

        feedLikeCache.put(member.getId(), feedLike.isPresent() ? Boolean.TRUE : Boolean.FALSE);
        hashOperations.put(KEY, feed.getId(), feedLikeCache);

        if (isNotSetExpireTime()) {
            redisTemplate.expire(KEY, 8, TimeUnit.HOURS);
        }
    }
}
