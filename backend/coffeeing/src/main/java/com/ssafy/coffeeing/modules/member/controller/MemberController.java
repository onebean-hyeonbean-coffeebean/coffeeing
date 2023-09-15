package com.ssafy.coffeeing.modules.member.controller;

import javax.validation.Valid;

import com.ssafy.coffeeing.modules.member.dto.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;

import com.ssafy.coffeeing.modules.member.service.MemberService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/unique-nickname")
    public BaseResponse<ExistNickNameResponse> checkDuplicateNickname(@RequestParam String nickname) {
        return BaseResponse.<ExistNickNameResponse>builder()
                .data(memberService.checkDuplicateNickname(nickname))
                .build();
    }

    @PostMapping("/onboard")
    public BaseResponse<OnboardResponse> insertAdditionalMemberInfo(@Valid @RequestBody OnboardRequest onboardRequest) {

        return BaseResponse.<OnboardResponse>builder()
                .data(memberService.insertAdditionalMemberInfo(onboardRequest))
                .build();
    }

    @GetMapping("/info/{memberId}")
    public BaseResponse<BaseInfoResponse> getMemberInfo(
            @PathVariable
            @NumberFormat
            Long memberId
            ) {
        return BaseResponse.<BaseInfoResponse>builder()
                .data(memberService.getMemberInfo(memberId))
                .build();
    }

    @GetMapping("/experience")
    public BaseResponse<ExperienceInfoResponse> getMemberExperience() {
        return BaseResponse.<ExperienceInfoResponse>builder()
                .data(memberService.getMemberExperience())
                .build();
    }

    @PutMapping("/change-profile")
    public BaseResponse<Void> updateMemberProfileImage(@Valid @RequestBody ProfileImageChangeRequest profileImageChangeRequest) {
        memberService.updateMemberProfileImage(profileImageChangeRequest);
        return BaseResponse.<Void>builder().build();
    }


}

