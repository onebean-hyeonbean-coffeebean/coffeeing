package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("test")
@Service
public class MockRecommendService implements RecommendService{

    private static List<Long> mockCapsuleIds = List.of(1L, 2L, 3L, 4L);
    private static List<Long> mockCoffeeIds = List.of(1L, 2L, 3L, 4L);

    @Override
    public RecommendResponse getRecommendationsByParameter(PreferenceRequest preferenceRequest) {

        return new RecommendResponse(preferenceRequest.isCapsule() ? mockCapsuleIds : mockCoffeeIds);
    }

    @Override
    public RecommendResponse getSimilarProduct(Boolean isCapsule, Long id) {

        return new RecommendResponse(isCapsule ? mockCapsuleIds : mockCoffeeIds);
    }
}