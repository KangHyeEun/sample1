package com.goodee.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.vo.MediaVO;

@Controller
public class TestController {
	
	// Spring file upload 정책
	/* - 스프링에서는 파일을 받기 위한 스펙을 제공하고 있으며 그중 하나가 MultipartFile 클래스에 바로 바이너리 파일을 넣는 형태를 제공한다.
	 * - 형식 : @RequestParam("type="file" input 이름") MultipartFile file
	 * */
	@PostMapping("/test1")
	public String singleFileUplaod(@RequestParam("mediaFile") MultipartFile file) throws IllegalStateException, IOException {
		
		if(!file.getOriginalFilename().isEmpty()) {
			Path path = Paths.get("D:/sample/"+file.getOriginalFilename());
			file.transferTo(path);
			System.out.println("매우 잘 저장되었습니다.");
		} else {
			System.out.println("에러가 발생했습니다.");
		}
		
		return "test1_result";
	} 
	
	@PostMapping("/test2")
	public String multiFileUpload(@RequestParam("mediaFile") MultipartFile[] files) throws IllegalStateException, IOException{
		 for (MultipartFile file : files) {
			 if(!file.getOriginalFilename().isEmpty()) {
//					Path path = Paths.get("D:/sample/"+file.getOriginalFilename());
//					file.transferTo(path);
				 	file.transferTo(Paths.get("D:/sample/"+file.getOriginalFilename()));
					System.out.println(file.getOriginalFilename() + "저장완료");
				} else {
					System.out.println("에러가 발생했습니다.");
				}
		 }
		 return "test2_result";
	}

	@PostMapping("/test3")
	public String multiFileUpload(@RequestParam("mediaFile") MultipartFile[] files, @RequestParam String user, @RequestParam String url, Model model) 
			throws IllegalStateException, IOException{
		for (MultipartFile file : files) {
			if(!file.getOriginalFilename().isEmpty()) {
				file.transferTo(Paths.get("D:/sample/"+file.getOriginalFilename()));
				
				System.out.println(file.getOriginalFilename() + "저장완료");
			} else {
				System.out.println("에러가 발생했습니다.");
			}
		}
		
		model.addAttribute("user",user);
		model.addAttribute("url",url);
		
		return "test3_result";
	}

	@PostMapping("/test4")
	public String multiFileUpload(MediaVO vo) throws IllegalStateException, IOException{
		MultipartFile[] files = vo.getMediaFile();
		for (MultipartFile file : files) {
			if(!file.getOriginalFilename().isEmpty()) {
				file.transferTo(Paths.get("D:/sample/"+file.getOriginalFilename()));
				
				System.out.println(file.getOriginalFilename() + "저장완료");
			} else {
				System.out.println("에러가 발생했습니다.");
			}
		}
		
		return "test4_result";
	}
	
	@PostMapping("/test5")
	@ResponseBody
	public String multiFileUploadWithAjax(MultipartFile[] uploadFile) 
			throws IllegalStateException, IOException {
		for(MultipartFile file : uploadFile) {
			if(!file.getOriginalFilename().isEmpty()) {
				file.transferTo(Paths.get("D:/sample/"+file.getOriginalFilename()));
				System.out.println(file.getOriginalFilename() + "저장완료.");
			}else {
				System.out.println("에러가 발생했습니다.");
			}
		}
		return "test5_received";
	}
	
	// 데이터 전송시에는 반드시 response 객체를 통해 전송을 해야 한다.
	@GetMapping("/download1")
	public void download(HttpServletResponse response) throws IOException {
		String path = "D:/sample/문제4.pdf";
		
		// 다운로드 받고자 하는 파일에 대한 Path 지정
		Path file = Paths.get(path);
		
		// 파일이름 utf-8로 인코딩 : 파일 이름이 깨지지 않도록 설정하기 위함 
		String filename = URLEncoder.encode(file.getFileName().toString(),"UTF-8");
		
		// response 객체의 해더 새팅
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		
		// 파일 channel 설정
		FileChannel fc = FileChannel.open(file, StandardOpenOption.READ);
		
		// response에서 output스트림 추출 
		OutputStream out = response.getOutputStream();  // getOutputStream는 파일IO에서 가져온 단방향 스트림임.
		// response에서는 channel형태로 가져올 수 없고 getOutputStream의 형태로 가져올 수 밖에 없다. => 따라서 두번을 나눠서 가져와야 함.
		
		// outputStream에서 Channel추출
		WritableByteChannel outputChannel = Channels.newChannel(out);  // 뽑아온 OutputStream을 넣어서 채널을 추출함.
		
		// response 객체로 파일 전송
		fc.transferTo(0, fc.size(), outputChannel);
	}
}

















