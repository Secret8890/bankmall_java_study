package com.template.api.apps.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"멤버 관리"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/member")
public class MemberController {
    private final MemberService memberService;

    // 멤버 불러오기
    @ApiOperation(value = "멤버 불러오기")
    @GetMapping
    public List<Member> selectMembers() {
        return memberService.selectMembers();
    }
    // 특정 멤버 불러오기
    @ApiOperation(value = "특정 멤버 불러오기")
    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }
    // 멤버 추가하기
    @ApiOperation(value = "멤버 추가하기")
    @PostMapping
    public Member saveMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    // 멤버 수정하기
    @ApiOperation(value = "멤버 수정하기")
    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member member) {
        return memberService.updateMember(id, member);
    }
    // 멤버 삭제하기
    @ApiOperation(value = "멤버 삭제하기")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }



}
