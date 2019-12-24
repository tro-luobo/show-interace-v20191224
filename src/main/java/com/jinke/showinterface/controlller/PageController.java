package com.jinke.showinterface.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * @author luobo
 */
@Controller
@Slf4j
public class PageController {
	
	@GetMapping("/page")
	public String getPage() {
		log.info("进入报表首页:index");
		return "index";
	}
}
