package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV100", 10000);
        Member save = repository.save(member);

        assertThat(save).isEqualTo(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMMember={}", findMember);

        assertThat(findMember).isEqualTo(member);

        //update
        repository.update(member.getMemberId(), 20000);

        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);


        //DELETE
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}