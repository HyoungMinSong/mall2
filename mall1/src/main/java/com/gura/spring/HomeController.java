package com.gura.spring;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String home(HttpServletRequest request) {
		//DB 에서 읽어온 공지사항이라고 가정하자 
		List<String> notice=new ArrayList<String>();
		notice.add("쇼핑몰입니다");
		//공지 사항을 request 에 담기
		request.setAttribute("notice", notice);
		//view 페이지 (jsp페이지) 로 forward 이동해서 응답 
		return "home";
	}
}




