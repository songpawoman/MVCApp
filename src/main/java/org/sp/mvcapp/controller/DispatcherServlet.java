package org.sp.mvcapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//웹어플리케이션의 모든 요청을 1차적으로 받는 서블릿 정의
public class DispatcherServlet extends HttpServlet{
	/*
	 * 컨트롤러의 5대 업무 처리 과정 
	 * 1) 요청을 받는다 
	 * 2) 요청을 분석한다 (즉 어떤 요청을 원하는지 분석...하위 컨트롤러를 선택하기 위해)
	 * 3) 알맞는 로직 객체에게 일을 시킨다( 만일 직접 하게 되면, 그 순간 컨트롤러는 모델이 되버리기 때문에
	 * 	                                             추후 다른 플젝에서 해당 로직을 재사용할 수 없다..)
	 * 4) 업무수행 후, view로 가져갈 것이 있다면 결과를 요청객체에 저장한다..(포워딩..)
	 *      DML - view로 가져갈 것 없고 , select -view 가져갈것이 있다..(DTO, List...)
	 * 5) 알맞는 뷰 보여준다      
	 * */
	FileReader reader;
	JSONParser jsonParser;
	JSONObject obj;
	
	//서블릿이 인스턴스화 되고 나서, 초기화 과정을 수행할때 호출되는 메서드 init()
	public void init(ServletConfig config) throws ServletException {
		//요청을 받기 전에, json 파일에 Stream 생성하여, 해석해놓기(파싱) 
		jsonParser = new JSONParser();
		
		URL url=this.getClass().getResource("/org/sp/mvcapp/controller/mapper.js");
		
		try {
			reader=new FileReader(new File(url.toURI()));
			//문자기반 입력스트림
			obj=(JSONObject)jsonParser.parse(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1단계) 요청을 받는다
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		//2) 요청을 분석한다.. 
		//클라이언트가 요청시 사용한 URI 자체가 곧 원하는게 뭔지에 대한 의미가 담겨져 있으므로
		//URI를 이용하여 클라이언트의 요청을 분석해보자 
		String uri=request.getRequestURI();
		
		//요청이 /movie.do 라면 MovieController 를 메모리에 올리고
		//MovieController의 doRequest () 메서드 호출 
		JSONObject controllerObj=(JSONObject)obj.get("controller");
		
		//이 요청에 대해 동작할 하위 컨트롤러의 경로를 얻어옴...단 실제 객체는 아님..
		//따라서 인스턴스 생성불가...
		String controllerName=(String)controllerObj.get(uri);
		System.out.println("이 요청에 동작할 클래스 경로는 "+controllerName);
		
	}
	
	//서블릿의 인스턴스가 소멸되기 직전에 호출..
	public void destroy() {
		//만일 닫히지 않은 스트림이 있다면, 여기서 닫아버리자 
		if(reader!=null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}






