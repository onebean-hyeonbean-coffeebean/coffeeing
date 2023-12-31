package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
@RequiredArgsConstructor
@Component
public class CoffeeDummy {

    private final List<String> coffeeNamesKr = List.of("케냐 에이에이", "케냐 에이비", "게이샤", "아르페지오", "로마");
    private final List<String> coffeeNamesEng = List.of("Kenya AA", "Kenya AB", "Geisha", "Arpegio", "Roma");
    private final List<String> regionsKr = List.of(
            "남아메리카",
            "코스타리카",
            "에티오피아",
            "콜롬비아",
            "인도"
    );
    private final List<String> regionsEng = List.of(
            "South america",
            "Costa Rica",
            "Ethiopia",
            "Colombia",
            "India"
    );
    private final List<Double> roasts = List.of(1.0, 1.0, 0.8, 0.8, 0.6);
    private final List<Double> acidities = List.of(1.0, 1.0, 0.8, 0.8, 0.6);
    private final List<Double> bodies = List.of(0.2, 0.2, 0.6, 0.4, 0.8);
    private final List<String> aroma = List.of("roasted, intense dark roasted", "spicy, dark roasted, woody", "dark roasted", "intense, cocoa", "woody, grain");

    private final List<String> imageUrls = List.of(
            "https://sample.image1.png",
            "https://sample.image2.png",
            "https://sample.image3.png",
            "https://sample.image4.png",
            "https://sample.image5.png"
    );
    private final List<String> productDescriptions = List.of(
            "sample description 1",
            "sample description 2",
            "sample description 3",
            "sample description 4",
            "sample description 5"
    );
    private final List<Integer> totalScores = List.of(38, 48, 42, 49, 25);


    private final CoffeeRepository coffeeRepository;

    public List<Coffee> create5CoffeeDummy() {

        List<Coffee> coffees = new ArrayList<>();

        for (int i = 0; i < coffeeNamesKr.size(); i++) {
            coffees.add(
                    createCoffee(
                            coffeeNamesKr.get(i),
                            coffeeNamesEng.get(i),
                            aroma.get(i),
                            imageUrls.get(i),
                            productDescriptions.get(i),
                            new CoffeeCriteria(roasts.get(i), acidities.get(i), bodies.get(i)),
                            totalScores.get(i),
                            5,
                            regionsKr.get(i),
                            regionsEng.get(i)
                    )
            );
        }

        return coffeeRepository.saveAll(coffees);
    }

    private Coffee createCoffee(String coffeeNameKr, String coffeeNameEng, String flavorNote, String imageUrl, String productDescription,
                                CoffeeCriteria coffeeCriteria, Integer totalScore, Integer totalReviewer, String regionKr, String regionEng) {
        return Coffee.builder()
                .coffeeNameKr(coffeeNameKr)
                .coffeeNameEng(coffeeNameEng)
                .coffeeCriteria(coffeeCriteria)
                .flavorNote(flavorNote)
                .imageUrl(imageUrl)
                .productDescription(productDescription)
                .totalScore(totalScore)
                .totalReviewer(totalReviewer)
                .regionKr(regionKr)
                .regionEng(regionEng)
                .build();
    }
}
