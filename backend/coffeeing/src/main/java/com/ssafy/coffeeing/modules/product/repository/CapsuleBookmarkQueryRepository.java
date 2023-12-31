package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.CapsuleBookmarkElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CapsuleBookmarkQueryRepository {

    Page<CapsuleBookmarkElement> findBookmarkedCapsuleElements(Member member, Pageable pageable);

}
