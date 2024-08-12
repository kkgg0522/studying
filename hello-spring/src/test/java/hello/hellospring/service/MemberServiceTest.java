package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        //같은객체 공유하게끔
    }
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() throws SQLException { //한글로 적어도됌 (실제 돌아가는 코드는 다른거기 때문!
        /* given-when-then 문법 (뭔가 주어졌을때 이것을 실행했을때 이것이 나와야한다 */
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원가입_예외() throws SQLException {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when 방법1
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //방법2 메시지 비교
        //         IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*   try { 방법 3
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}