package com.ssafy.coffeeing.modules.product.mapper;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static CapsuleResponse supplyCapsuleResponseBy(Capsule capsule) {

        return new CapsuleResponse(capsule.getId(), capsule.getBrandKr(), capsule.getCapsuleName(), capsule.getAroma(),
                capsule.getCoffeeCriteria().getRoast(), capsule.getCoffeeCriteria().getAcidity(),
                capsule.getCoffeeCriteria().getAcidity(), capsule.getDescription(),
                capsule.getTotalScore() / capsule.getTotalReviewer());
    }
}
