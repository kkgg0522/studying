package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    private final MemoryMemberRepository memoryMemberRepository;

    @Autowired
    public MemberServiceImpl(MemoryMemberRepository memberRepository) {
        this.memoryMemberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memoryMemberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memoryMemberRepository.findById(memberId);
    }

    //테스트 용도
//    public MemberRepository getMemberRepository() {
//        return memberRepository;
//    }
}
