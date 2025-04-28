package mvcCashbook.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvcCashbook.dao.AdminDao;
import mvcCashbook.dto.Admin;

import java.io.IOException;

// 웹서버 주소에 "/login" 요청이 오면 이 서블릿이 실행된다.
@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	// view forward : login.jsp
	// GET방식 요청 처리 : 로그인 화면을 보여줄 때 사용
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청 분석
		// GET 요청이므로 로그인 페이지를 보여주기만 하면 된다.
		// 화면(view)으로 이동 (포워딩)
		
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		
	}
		// POST방식 요청 처리 : 로그인 버튼을 눌렀을 때 실행
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청 분석(폼에서 입력한 아이디/비밀번호 꺼내기)
		// 아이디는 admin으로 고정되기때문에 비밀번호만 꺼내기
		String adminPw = request.getParameter("pw"); // 입력한 비밀번호
		
		// 2) DAO를 통해 DB에서 비밀번호 확인
		AdminDao adminDao = new AdminDao(); // dopost() 안에서만 사용 -> 지역변수로 선언
		Admin admin = adminDao.selectAdminId(adminPw); //비밀번호가 맞는지 확인
		
		// 3) 결과에 따라 로그인 성공/실패 처리
		if(admin != null) {
			// 로그인 성공
		HttpSession session = request.getSession();	// 세션 객체 가져오기
		session.setAttribute("loginAdmin", "admin"); // 세션에 로그인한 사용자 저장
		response.sendRedirect(request.getContextPath() + "/main"); // 메인페이지로 이동
		
		} else {
			// 로그인 실패
			request.setAttribute("errorMsg", "비밀번호가 틀렸습니다."); // 실패 메세지 저장
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response); // 다시 로그인 폼 보여주기
		
		}
	}
}