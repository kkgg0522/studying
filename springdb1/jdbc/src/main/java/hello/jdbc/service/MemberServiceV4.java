package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 *
 * MemberRepository 인터페이스 의존
 */
@Slf4j
public class MemberServiceV4 {

    //    private final PlatformTransactionManager transactionManager;

    private final MemberRepository repository;

    public MemberServiceV4(MemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money) {
        Member fromMember =  repository.findById(fromId);
        Member toMember =  repository.findById(toId);

        repository.update(fromId, fromMember.getMoney() - money);
        validation(toMember.getMemberId());
        repository.update(toId, toMember.getMoney() + money);
    }

    private static void validation(String toId) {
        if(toId.equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

}
