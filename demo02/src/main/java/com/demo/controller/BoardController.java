package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	
	@GetMapping("/hi")
	public String nice(Model model) {
		model.addAttribute("message", "Hello");
		return "hello";
	}
	
	@GetMapping("/main")
	public String nicetomett(Model model) {
		model.addAttribute("message", "Hello");
		return "Main/Homepage";
	}
}
	