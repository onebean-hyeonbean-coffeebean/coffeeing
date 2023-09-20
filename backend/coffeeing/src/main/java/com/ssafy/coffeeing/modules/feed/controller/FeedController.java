package com.ssafy.coffeeing.modules.feed.controller;

import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.service.FeedService;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public BaseResponse<UploadFeedResponse> uploadFeedRequest(@Valid @RequestBody UploadFeedRequest uploadFeedRequest) {
        return BaseResponse.<UploadFeedResponse>builder()
                .data(feedService.uploadFeedByMember(uploadFeedRequest))
                .build();
    }

    @PatchMapping("/{feedId}")
    public BaseResponse<Void> updateFeedContent(
            @PathVariable @NumberFormat Long feedId,
            @Valid @RequestBody UpdateFeedRequest updateFeedRequest) {
        feedService.updateFeedById(feedId, updateFeedRequest);
        return BaseResponse.<Void>builder()
                .build();
    }

    @DeleteMapping("/{feedId}")
    public BaseResponse<Void> deleteFeed(@PathVariable @NumberFormat Long feedId) {
        feedService.deleteFeedById(feedId);
        return BaseResponse.<Void>builder()
                .build();
    }

    @PostMapping("/{feedId}/like")
    public BaseResponse<ToggleResponse> toggleFeedLike(@PathVariable @NumberFormat Long feedId) {
        return BaseResponse.<ToggleResponse>builder()
                .data(feedService.toggleFeedLike(feedId))
                .build();
    }


    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "cursor"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "Long"
                            , paramType = "query"
                            , defaultValue = ""
                    )
                    ,
                    @ApiImplicitParam(
                            name = "size"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "Integer"
                            , paramType = "query"
                            , defaultValue = "10"
                    )
            })
    @GetMapping("/my-list")
    public BaseResponse<ProfileFeedsResponse> getMyFeeds(@Valid FeedsRequest feedsRequest) {
        return BaseResponse.<ProfileFeedsResponse>builder()
                .data(feedService.getMyFeeds(feedsRequest))
                .build();
    }

    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "memberId"
                            , value = "멤버 ID"
                            , required = true
                            ,dataType = "Long"
                            ,paramType = "path"
                    ),
                    @ApiImplicitParam(
                            name = "cursor"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "Long"
                            , paramType = "query"
                            , defaultValue = ""
                    )
                    ,
                    @ApiImplicitParam(
                            name = "size"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "Integer"
                            , paramType = "query"
                            , defaultValue = "10"
                    )
            })
    @GetMapping("/{memberId}/list")
    public BaseResponse<ProfileFeedsResponse> getFeedsByMemberId(@Valid MemberFeedsRequest memberFeedsRequest) {
        return BaseResponse.<ProfileFeedsResponse>builder()
                .data(feedService.getFeedsByMemberId(memberFeedsRequest))
                .build();
    }

    @GetMapping("/{feedId}")
    public BaseResponse<FeedDetailResponse> getFeedDetailById(@PathVariable @NumberFormat Long feedId) {
        return BaseResponse.<FeedDetailResponse>builder()
                .data(feedService.getFeedDetailById(feedId))
                .build();
    }

    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "cursor"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "Long"
                            , paramType = "query"
                            , defaultValue = ""
                    )
                    ,
                    @ApiImplicitParam(
                            name = "size"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "Integer"
                            , paramType = "query"
                            , defaultValue = "10"
                    )
            })
    @GetMapping
    public BaseResponse<FeedPageResponse> getFeedsByFeedPage(@Valid FeedsRequest feedsRequest) {
        return BaseResponse.<FeedPageResponse>builder()
                .data(feedService.getFeedsByFeedPage(feedsRequest))
                .build();
    }
}
