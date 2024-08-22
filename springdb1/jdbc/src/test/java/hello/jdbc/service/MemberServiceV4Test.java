package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 *
 * MemberRepository 인터페이스 의존
 */
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberServiceV4 service;


    @TestConfiguration
    static class TestConfig{
        private final DataSource dataSource;

        TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepository(){
//            return new MemberRepositoryV4_1(dataSource);
//            return new MemberRepositoryV4_2(dataSource);
            return new MemberRepositoryV5(dataSource);
        }


        @Bean
        MemberServiceV4 memberServiceV4(){
            return new MemberServiceV4(memberRepository());
        }
    }

    @AfterEach
    void after() throws SQLException {
        repository.delete(MEMBER_A);
        repository.delete(MEMBER_B);
        repository.delete(MEMBER_EX);
    }
    @Test
    @DisplayName("정상 이체")
    void accountTransfer() {
        //Given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        repository.save(memberA);
        repository.save(memberB);
        
        //When
        service.accountTransfer(memberA.getMemberId(), memberB.getMemberId(),2000);
        //Then
        Member findMemberA = repository.findById(memberA.getMemberId());
        Member findMemberB = repository.findById(memberB.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체 중 예외 발생")
    void accountTransferEX() {
        //Given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEX = new Member(MEMBER_EX, 10000);
        repository.save(memberA);
        repository.save(memberEX);

        //When
        assertThatThrownBy(() -> service.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(),2000))
                .isInstanceOf(IllegalStateException.class);


        //Then
        Member findMemberA = repository.findById(memberA.getMemberId());
        Member findMemberEX = repository.findById(memberEX.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberEX.getMoney()).isEqualTo(10000);
    }

}