package com.goodee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring MVC 프로젝트 관련된 설정을 하는 클래스
@Configuration
// Controller 어노테이션이 세팅되어 있는 클래스를 등록하는 어노테이션
// 혼자서 못움직이고 반드시 ComponentScan과 같이 움직여야 함. 즉 관련되어 있는 모든 기능 정보들을 다 구성해줌.
@EnableWebMvc
//스캔할 패키지 지정
@ComponentScan("com.goodee.controller")
public class ServletAppContext implements WebMvcConfigurer {
	// WebMvcConfigurer : webMVC의 스팩 정보를 지원하는 것. 
		// 스프링 코어에서의 설정이 아닌 spring web MVC에 특화되어 있는 설정들을 설정하고자 할 때 사용.
	
	
	//Controller의 메서드가 jsp의 아름 앞뒤에 경로와 확장자를 붙여주도록 설정한다.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// return 한 String데이터의 앞과 뒤 문장을 합쳐서 우리가 불려올 view의 경로를 알아서 설정해주는 매소드
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/",".jsp");
	}
	
	// 정적 파일의 경로 세팅
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// view가 return이 되었을 때 필요로 하는 정보들을 접근하게 도와주는 매소드.
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/resource/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:/sample/");
		// 참조하고자 하는 경로의 위치를 resources를 참조하겠다는 뜻
		// /**   => 하위의 모든 경로를 말하는 것.
	}
	
	// 파일 업로드 세팅
	 
	private final int MAX_SIZE = 10 * 1024 * 1024; // 10메가
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		// 디폴트 인토딩 타입 설정
		multipartResolver.setDefaultEncoding("UTF-8");
		// 전체 올릴 수 잇는 파일들의 총 용량 한계
		multipartResolver.setMaxUploadSize(MAX_SIZE*10); //100메가
		// 파일 한개 당 올릴 수 있는 용량 최대치
		multipartResolver.setMaxUploadSizePerFile(MAX_SIZE);
		
		return multipartResolver;
	} 
}

















