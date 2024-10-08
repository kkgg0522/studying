package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member) throws SQLException;

    Optional<Member> findById(long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
