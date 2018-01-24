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
import com.spring.data.crawl.core.Dcrawler;
import com.spring.data.crawl.util.SameWebsiteOnlyFilter;
import com.spring.data.serviceimpl.MainServiceImpl;

@Controller
@RequestMapping("/craw")
public class CrawllingController {
	
	public static final Logger log = LogManager.getLogger(CrawllingController.class);
	
	@Autowired
	MainServiceImpl service;
	
	@RequestMapping("/ygosu")
	public String control(@RequestParam HashMap<String, Object> pMap
							,Model model
							,HttpServletRequest req, HttpServletResponse res
							,ArrayList<HashMap<String, Object>> rMap
							){
		
		pMap.put("msg", "msgg");
		
		model.addAttribute("rmap", service.getNow(pMap));
		model.addAttribute("pmap", pMap);
		
		return "main/crawresult/ygosuimg";
	}
	

	@RequestMapping(value="/ygosu/img", method={RequestMethod.POST, RequestMethod.GET})
	public String ygosuImg(@RequestParam HashMap pMap
									, Model model
									, HttpServletRequest req
									, HttpServletResponse res
									, ArrayList<String> resultList
			) throws Exception{
		Crawler crawler = new Crawler();
		final String ygosuPreUrl = "http://www.ygosu.com/community/";
		
		if(!pMap.containsKey("s")){
			pMap.put("s", "10");
		}
		if(!pMap.containsKey("url")){
			pMap.put("url", ygosuPreUrl + "stars"); 
		}else{
			pMap.put("url", ygosuPreUrl + pMap.get("url").toString()); 
		}
		
		pMap.put("top", "N");
		pMap.put("flag", "i");
		
        crawler.setPageProcessor(null); // set an IPageProcessor instance here.
        crawler.setUrlFilter(new SameWebsiteOnlyFilter(pMap.get("url").toString()));
        crawler.addUrl(pMap.get("url").toString());
        
        resultList = (ArrayList<String>) crawler.crawl(pMap);

		model.addAttribute("resultList", resultList);
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		return "main/crawresult/ygosuimg";
	}
	
	/**
	 * 
	 * @param url
	 * @param flag
	 * @param w
	 * @param q
	 * @param sd
	 * @param ed
	 * @param page
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/daum/news", method={RequestMethod.POST, RequestMethod.GET})
	public String crawlNews(@RequestParam HashMap pMap
			, Model model
			, HttpServletRequest req
			, HttpServletResponse res
			, ArrayList<String> resultList
			) throws Exception{
		
	Dcrawler crawler = new Dcrawler();
	ArrayList<String> tagsListString = new ArrayList<>();
	ArrayList<Object> tagsListint= new ArrayList<>();
	
	final String daumSearchPreUrl = "https://search.daum.net/search?w=" + pMap.get("w").toString() + "&";
		
		String paramQ = "q=" + pMap.get("q").toString() + "&period=w&"	;
		String paramSd="sd=" + pMap.get("sd").toString() + "000000&"       ;
		String paramEd="ed=" + pMap.get("ed").toString() + "235900&"       ;
		String paramNil_search="nil_search=btn&"          ;
		String paramDA="DA=PGD&"                  ;
		String paramEnc="enc=utf8&"                ;
		String paramCluster="cluster=y&"               ;
		String paramCluster_page="cluster_page=1&p="          ;
		
		int paramP = 1                      ;
		
	pMap.put("url"
			, daumSearchPreUrl 
			+ paramQ + paramSd + paramEd + paramNil_search + paramDA + paramEnc + paramCluster + paramCluster_page
			);

	resultList = (ArrayList<String>) crawler.crawl(pMap);

	String[] tagsArray = pMap.get("tags").toString().split(",");
	for(String tag : tagsArray) tagsListString.add(tag);
	
	if(pMap.containsKey("tags") && null != pMap.get("tags")){
		for(int i = 0; i < tagsListString.size(); i++){
			int count = 0;
			for(String news : resultList){
//				log.info(news);
				if(news.toLowerCase().contains(tagsListString.get(i).toString().toLowerCase())){
					count++;
				}
			}
			tagsListint.add(count);
		}
	}
	
	model.addAttribute("tagsListString", tagsListString);
	model.addAttribute("tagsListint", tagsListint);
	model.addAttribute("resultList", resultList);
	
	res.setCharacterEncoding("UTF-8");
	res.setContentType("text/html; charset=UTF-8");
	return "main/crawresult/daumnews";
	
	}
	

}
