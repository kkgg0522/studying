package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    private DataSource dataSource;

//    @PersistenceContext
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository());
//    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository(); Memory 사용시
//        return new JdbcMemberRepository(dataSource); //DB사용시
//        return new JdbcTemplateMemberRepository(dataSource); jdbc 템플릿 사용시
//        return new JpaMemberRepository(em); //JPA 사용시
//    }  //스프링 데이터 jpa 사용시 필요없음


}
