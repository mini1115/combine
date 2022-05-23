package com.example0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example0.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserContorller {

	private final UserService uservice;
	
	//마이페이지
	@GetMapping("mypage")
	public String mypage() {
		return "user/mypage";
	}
	
	//상세보기
	@GetMapping("view/{id}")
	public String view(Model model, @PathVariable Long id) {
		model.addAttribute("user",uservice.detail(id));
		return "user/view";
	}
	
}