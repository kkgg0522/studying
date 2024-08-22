package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 repository;


    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = dataSource.getConnection();
        //시작
        try{
            conn.setAutoCommit(false);

            //비지니스 로직
            log.info("START TX");
            bizLogic(fromId, toId, money, conn);
            log.info("STOP TX");

            //커밋 ,롤백
            conn.commit();
        }catch (Exception e){
            conn.rollback();
            throw new IllegalStateException(e);
        }finally {
            release(conn);
        }

    }

    private void bizLogic(String fromId, String toId, int money, Connection conn) throws SQLException {
        Member fromMember =  repository.findById(fromId, conn);
        Member toMember =  repository.findById(toId, conn);

        repository.update(fromId, fromMember.getMoney() - money, conn);
        validation(toMember.getMemberId());
        repository.update(toId, toMember.getMoney() + money, conn);
    }

    private static void validation(String toId) {
        if(toId.equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

    private static void release(Connection conn) {
        if(conn != null)
            try{
                conn.setAutoCommit(true); //커넥션 풀 고려
                conn.close();
            } catch (Exception e){
                log.error("error", e);
            }
    }
}
