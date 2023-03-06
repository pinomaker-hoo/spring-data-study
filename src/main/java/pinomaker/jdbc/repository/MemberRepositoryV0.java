package pinomaker.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import pinomaker.jdbc.connection.DBConnectionUtil;
import pinomaker.jdbc.domain.Member;

import java.sql.*;

@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMmeberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.execute();
            return member;
        }catch (SQLException e){
            log.error("DB ERROR : ", e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }

    public void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("ERROr : ", e);
            }
        }
        if (stmt != null) {
            try{
                stmt.close();
            }catch (SQLException e){
                log.info("ERROr : ", e);
            }
        }

        if (con != null) {
            try{
                con.close();
            }catch (SQLException e){
                log.info("ERROr : ", e);
            }
        }
    }

    private Connection getConnection(){
        return DBConnectionUtil.getConnection();
    }
}
