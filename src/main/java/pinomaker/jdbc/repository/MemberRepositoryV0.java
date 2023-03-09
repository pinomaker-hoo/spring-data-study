package pinomaker.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import pinomaker.jdbc.connection.DBConnectionUtil;
import pinomaker.jdbc.domain.Member;

import java.sql.*;
import java.util.NoSuchElementException;

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

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setMmeberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberIid = " + memberId);
            }
        }catch (SQLException e){
            log.error("DB ERROR", e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        }catch (SQLException e){
            log.error("DB ERROR", e);
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
