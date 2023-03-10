package pinomaker.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pinomaker.jdbc.domain.Member;

import static org.assertj.core.api.Assertions.*;


import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member =  new Member("memberV4", 10000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMmeberId());
        log.info("findMmber = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        //update
        repository.update(member.getMmeberId(), 20000);
        Member updateMember = repository.findById(member.getMmeberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete(member.getMmeberId());
        assertThatThrownBy(() -> repository.findById(member.getMmeberId())).isInstanceOf(NoSuchElementException.class);
    }
}
