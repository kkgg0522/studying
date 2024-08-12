package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
 //스프링 빈에 알아서 등록해줌
@Transactional //필요한 메서드에만 작성해도 됌
public class MemberService {
    //ctrl + shift + T 테스트 클래스
    private MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member) throws SQLException {
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/
        validateDuplicateMember(member); //중복 회원검증 Ctrl + alt + shift + T -> 9 Extract method 하면 메서드로 보낼 수 잇음
        //리팩토링 관련 옵션
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    //전체 회원 조회
    //서비스 메서드는 가독성 좋은 메서드
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
