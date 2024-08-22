package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 탬플릿
 */
@Slf4j
public class MemberServiceV3_2 {

    //    private final PlatformTransactionManager transactionManager;

    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 repository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 repository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.repository = repository;
    }


    public void accountTransfer(String fromId, String toId, int money) {
        txTemplate.executeWithoutResult((status) -> {
            //비지니스 로직
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
//        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
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
