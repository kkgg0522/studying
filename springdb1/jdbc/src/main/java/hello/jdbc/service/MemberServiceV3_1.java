package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 repository;


    public void accountTransfer(String fromId, String toId, int money) {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        //시작
        try{

            //비지니스 로직
            log.info("START TX");
            bizLogic(fromId, toId, money);
            log.info("STOP TX");

            //커밋 ,롤백
            transactionManager.commit(status);
        }catch (Exception e){
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }

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
