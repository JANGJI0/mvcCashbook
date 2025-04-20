package mvcCashbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mvcCashbook.dto.Admin;

public class AdminDao {
	// 로그인 기능
	// 아이디는 admin으로 고정하귀 위해 Pw만 사용
	public Admin selectAdminId(String adminPw) throws ClassNotFoundException, SQLException {
		// 로그인 성공/실패에 따라 처리해버리므로 변수는 필요없다.
		Admin admin = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; // select 조회 할때만 받아서 쓰는경우
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashbook", "root", "java1234");
		
		String sql = "SELECT admin_id adminId, admin_pw adminPw FROM admin WHERE admin_id = ? AND admin_pw = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, "admin"); // 아이디는 admin으로 고정값으로 넘기기위해
		stmt.setString(2, "adminPw");
		
		rs = stmt.executeQuery();
		
		if(rs.next()) { // whille 말고  if로 쓰는 이유가 값이 1개이냐 여러개 차이/ 로그인은 아이디, 중복체크만
			admin = new Admin(); // 결과 있을 때만 객체 생성해서, 실해한 경우 null로 유지하려고
			admin.setAdmin_pw(rs.getString("adminPw"));
		}
		
		// 자원정리
		conn.close();
		stmt.close();
		rs.close();
		
		return admin;
	}
}











