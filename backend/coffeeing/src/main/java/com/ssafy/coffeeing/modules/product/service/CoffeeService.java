package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.CoffeeBookmarkElement;
import com.ssafy.coffeeing.modules.member.dto.CoffeeBookmarkResponse;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeBookmarkQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoffeeService {

    private final SecurityContextUtils securityContextUtils;

    private final CoffeeRepository coffeeRepository;

    private final CoffeeReviewRepository coffeeReviewRepository;

    private final CoffeeBookmarkRepository coffeeBookmarkRepository;

    private final MemberRepository memberRepository;

    private final CoffeeBookmarkQueryRepository coffeeBookmarkQueryRepository;
    private final RecommendService recommendService;

    private static final Integer BOOKMARK_PAGE_SIZE = 8;
    private static final Integer SIMILAR_PRODUCT_SIZE = 4;
    private static final Boolean IS_CAPSULE = false;

    @Transactional(readOnly = true)
    public CoffeeResponse getDetail(Long id) {

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        Boolean isBookmarked = Boolean.FALSE;

        CoffeeReview memberReview = null;

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        if (member != null) {

            isBookmarked = coffeeBookmarkRepository.existsByCoffeeAndMember(coffee, member);
            memberReview = coffeeReviewRepository.findCoffeeReviewByCoffeeAndMember(coffee, member);
        }

        return ProductMapper.supplyCoffeeResponseOf(coffee, isBookmarked, memberReview);
    }

    @Transactional
    public ToggleResponse toggleBookmark(Long id) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        CoffeeBookmark bookmark = coffeeBookmarkRepository.findByCoffeeAndMember(coffee, member);

        // 찜 등록
        if (bookmark == null) {
            bookmark = CoffeeBookmark
                    .builder()
                    .coffee(coffee)
                    .member(member)
                    .build();

            coffeeBookmarkRepository.save(bookmark);

            return new ToggleResponse(Boolean.TRUE);
        }

        // 찜 해제
        coffeeBookmarkRepository.delete(bookmark);

        return new ToggleResponse(Boolean.FALSE);
    }

    @Transactional(readOnly = true)
    public SimilarProductResponse getSimilarCoffees(Long id) {
        RecommendResponse recommendResponse = recommendService.pickBySimilarity(SIMILAR_PRODUCT_SIZE, false, id);
        List<Coffee> coffees = coffeeRepository.findAllById(recommendResponse.results());

        return new SimilarProductResponse(true, coffees.stream()
                .map(ProductMapper::supplySimpleProductElementFrom).toList());
    }

    @Transactional(readOnly = true)
    public CoffeeBookmarkResponse getBookmarkedCoffees(Long id, PageInfoRequest pageInfoRequest) {
        Pageable pageable = pageInfoRequest.getPageableWithSize(BOOKMARK_PAGE_SIZE);
        Member member = memberRepository.findById(id).orElseThrow(() -> new BusinessException(MemberErrorInfo.NOT_FOUND));
        Page<CoffeeBookmarkElement> coffeeBookmarkElements = coffeeBookmarkQueryRepository.findBookmarkedCoffeeElements(member, pageable);
        return ProductMapper.supplyCoffeeBookmarkResponseOf(
                coffeeBookmarkElements.getNumber(),
                coffeeBookmarkElements.getTotalPages(),
                coffeeBookmarkElements.getContent(),
                IS_CAPSULE
        );
    }
}
