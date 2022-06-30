package com.template.api.apps.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepo memberRepo;

    //멤버 선택하기
    public List<Member> selectMembers() {
        return memberRepo.findAll();
    }

    // 멤버 찾기
    public Member getMember(Long id) {
        return memberRepo.findById(id).orElseThrow(IllegalArgumentException::new);
    }
    // 멤버 추가하기
    public Member saveMember(Member member) {
        return memberRepo.save(member);
    }
    // 멤버 삭제하기
    public void deleteMember(Long id) {
        memberRepo.deleteById(id);
    }


    public Member updateMember(Long id, Member member) {
        Member memberData = memberRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        memberData.update(member.getName(), member.getAddress());
        return memberData;
    }
}
