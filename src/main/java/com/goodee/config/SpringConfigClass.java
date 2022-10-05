package com.goodee.config;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// 전체 환경 설정을 해주는 엔트리 클래스
public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	//AbstractAnnotationConfigDispatcherServletInitializer : java에서 web설정을 할때 사용하는 추상클래스임.
	
	// 프로젝트에서 사용할 Bean들을 정의하기 위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// <?>: 와일드카드. 어떤 타입이든지 다 받겠다는 뜻.
		//컴포턴트 형태 말고 임시적으로 써야할 bean들이 생길 수 있는데 그런 bean들을 사용할 수 있는 class의 위치를 지정함.
		return new Class[] {RootAppContext.class};
	}
	
	// Spring MVC 프로젝트 설정을 위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// MVC에 필요한 여러 구성정보를 담은 class를 지정할 때 사용하는 매소드
		return new Class[] {ServletAppContext.class};
	}

	// 아래 두개는 스프링 스팩이 아니라 서블릿 스펙이다.
	
	// DispatcherServlet에 매핑할 요청 주소를 세팅한다.
	@Override
	protected String[] getServletMappings() {
		// root를 기준으로 주소를 접근할 때 기준을 잡음. 보통 이 구조는 변하지 않음.
		// 사용자가 어떤 주소로 접근을 할깨 어떤 주소로 접근할지 
		return new String[] {"/"};
	}
	
	//파라미터 인코딩 설정
	@Override
	protected Filter[] getServletFilters() {
		//request.setcharacter~ 같은 정보를 설정하는 것. 
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
}




