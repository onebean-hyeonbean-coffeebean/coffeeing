package com.ssafy.coffeeing.modules.feed.repository;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedDynamicRepository {

    Optional<Feed> findByIdAndMember(Long feedId, Member member);

    @Query("select f from Feed f join fetch f.member where f.id = :feedId")
    Optional<Feed> findFeedDetail(Long feedId);
}
