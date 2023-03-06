package pinomaker.jdbc.repository;

import org.junit.jupiter.api.Test;
import pinomaker.jdbc.domain.Member;

import java.sql.SQLException;

public class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member =  new Member("memberV0", 10000);
        repository.save(member);
    }
}
