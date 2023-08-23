package org.sp.mvcapp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sp.mvcapp.model.movie.MovieManager;

//영화에 대한 조언요청을 받는 컨트롤러
public class MovieController{

	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//파라미터 받기 
		String movie=request.getParameter("movie");
		
		//컨틀로러에서는 가능은 해도, 로직을 작성하지 말자 
		//이유는? Model(로직)을 분리시켜놓아야 다른 프로젝트 등에서 재사용가능하다..
		//그리고 여기서 만일 로직을 작성해버리면, MVC중 Controller+ Model 이 되버림..
		
		//3) 알맞는 로직 객체에 일 시킨다..
		MovieManager manager=new MovieManager();
		String msg=manager.getAdvice(movie);
		
		
		//결과페이지와 컨트롤러가 분리되어 있으므로 msg와 같은 결과가 담겨진 지역변수가
		//유지되려면, 어딘 가에 저장해놓지 않으면 안됨... 
		//현재로써는 session 에 저장 
		//HttpSession session = request.getSession();
		//session.setAttribute("msg", msg);
		
		//만일 요청을 끊지 않고, 결과페이지인 result.jsp로 포워딩 하는 방법만 
		//있다면, 우리는 session까지 사용할 필요 조차 없다.. 
		//4단계) 결과 페이지인 result.jsp로 가져갈 것이 있다면 결과 저장..
		request.setAttribute("msg", msg);
		
		//서버의  view 중 어떤 view로 포워딩할지를 결정하는 객체 
		RequestDispatcher dis=request.getRequestDispatcher("/movie/result.jsp"); 
		dis.forward(request, response);
		
		//PrintWriter out=response.getWriter();		
		// 서블릿이 디자인결과를 표현할 수는 잇지만, MVC로 분리시키지 않으면
		// 디자인 코드는 디자이너 퍼블리셔와 협업의 대상이므로 java 코드에 두어서는 안됨
		// 디자인 즉 view 를 담당하는 기술로 표현해야 한다...
		//<script>locatio.href="/movie/result.jsp";</script>
		//response.sendRedirect("/movie/result.jsp");
		
	}
	
}









