package com.ssafy.coffeeing.modules.survey.mapper;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyMapper {

    public static Preference supplyPreferenceOf(PreferenceRequest request, Long memberId) {

        return Preference.builder()
                .memberId(memberId)
                .productType(request.isCapsule() ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN)
                .machineType(request.isCapsule() ? request.machineType() : null)
                .coffeeCriteria(new CoffeeCriteria(request.roast(), request.acidity(), request.body()))
                .flavorNote(request.flavorNote())
                .build();
    }

    public static SurveyResponse supplySurveyResponseOf(List<SimpleProductElement> products, PreferenceRequest request) {

        return new SurveyResponse(new SimilarProductResponse(request.isCapsule(), products),
                null, null,
                request.roast(), request.acidity(), request.body());
    }

    public static SurveyResponse supplySurveyResponseOf(List<SimpleProductElement> products, PreferenceRequest request, Member member) {

        return new SurveyResponse(new SimilarProductResponse(request.isCapsule(), products),
                member.getNickname(), member.getProfileImage(),
                request.roast(), request.acidity(), request.body());
    }

    public static PreferenceRequest supplyPreferenceRequestFrom(Preference preference) {

        return new PreferenceRequest(preference.getProductType() == ProductType.COFFEE_CAPSULE,
                preference.getMachineType(),
                preference.getCoffeeCriteria().getRoast(),
                preference.getCoffeeCriteria().getAcidity(),
                preference.getCoffeeCriteria().getBody(),
                preference.getFlavorNote());
    }

}