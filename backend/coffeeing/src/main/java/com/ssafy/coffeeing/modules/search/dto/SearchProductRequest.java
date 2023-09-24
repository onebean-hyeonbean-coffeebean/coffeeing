package com.ssafy.coffeeing.modules.search.dto;

import com.ssafy.coffeeing.modules.search.domain.TagType;
import lombok.Builder;

import java.util.Objects;

public record SearchProductRequest(
        String keyword,
        String roast,
        String acidity,
        String body,
        String flavorNote,
        TagType tagType,
        Integer page,
        Integer size

) {

    @Builder
    public SearchProductRequest(String keyword, String roast, String acidity, String body, String flavorNote,
                                TagType tagType, Integer page, Integer size) {
        if(Objects.isNull(tagType)) {
            tagType = TagType.BEAN;
        }
        if(Objects.isNull(page)) {
            page = 0;
        }
        if(Objects.isNull(size)) {
            size = 8;
        }
        this.keyword = keyword;
        this.roast = roast;
        this.acidity = acidity;
        this.body = body;
        this.flavorNote = flavorNote;
        this.tagType = tagType;
        this.page = page;
        this.size = size;
    }
}
