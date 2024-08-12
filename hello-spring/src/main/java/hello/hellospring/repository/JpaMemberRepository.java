package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    /*
    저장, 조회, 업데이트, 삭제 sql 필요 X
    PK기반이 아닌 기능은 JPQL을 작성해야한다
    jpql 객체지향 쿼리 언어 pk가 아니면
     */
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) throws SQLException {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name ", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
