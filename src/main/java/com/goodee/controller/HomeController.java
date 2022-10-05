package com.goodee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 이거 하나로 서블릿 완성
public class HomeController {
	
	@RequestMapping(value = "/") // controller에서 @WebServlet("/PageController") 이런식으로 나오는 것을 말함.
	public String home() {
		return "index";
		// index = > servlet-content.xml 파일에 설정값이 있음.
				   //<beans:property name="prefix" value="/WEB-INF/views/"/>
		           //<beans:property name="suffix" value=".jsp"/>
	}
	
}
