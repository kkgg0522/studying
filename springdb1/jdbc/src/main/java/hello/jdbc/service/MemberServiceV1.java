package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 repository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
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
