package com.spring.data.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.data.crawl.core.Crawler;
import com.spring.data.crawl.util.SameWebsiteOnlyFilter;
import com.spring.data.serviceimpl.MainServiceImpl;

@Controller
@RequestMapping("/main")
public class MainController {
	
	public static final Logger log = LogManager.getLogger(MainController.class);
	
	@Autowired
	MainServiceImpl service;
	
	@RequestMapping("/control")
	public String control(@RequestParam HashMap<String, Object> pMap
							,Model model
							,HttpServletRequest req, HttpServletResponse res
							,ArrayList<HashMap<String, Object>> rMap
							){
		
		pMap.put("msg", "msgg");
		
		model.addAttribute("rmap", service.getNow(pMap));
		model.addAttribute("pmap", pMap);
		
		return "/main/control";
	}
	

	@RequestMapping(value="/craw", method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public void crawl(@RequestParam HashMap pMap
									, Model model
									, HttpServletRequest req
									, HttpServletResponse res
									, ArrayList<String> imgList
			) throws Exception{
		
		Crawler crawler = new Crawler();
		
		log.info("=========================================== START");
		
		pMap.put("url", "http://www.ygosu.com/community/yeobgi");
		pMap.put("top", "N");

        crawler.setUrlFilter(new SameWebsiteOnlyFilter(pMap.get("url").toString()));
        crawler.setPageProcessor(null); // set an IPageProcessor instance here.
        crawler.addUrl(pMap.get("url").toString());
        imgList = (ArrayList<String>) crawler.crawl(pMap);
		
        PrintWriter out = res.getWriter();
        out.print("<html> <head><title></title><head>");
        out.println(pMap.get("size").toString());
		out.println("<p> It's Done</p> \n" + "<p>COMPLETE : " + pMap.get("size").toString() + "<p>" );
		
		for(String imgSrc : imgList){
			out.println("<img src=\"" + imgSrc + "\">");
		}
		
		out.print("</html>");
		
	}
	

}
