package com.spring.data.crawl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.spring.data.crawl.util.IUrlFilter;
import com.spring.data.crawl.util.UrlNormalizer;


/**
 */
public class Dcrawler {

	private static final Logger log = LogManager.getLogger(Crawler.class);

//	Resource r = new FileSystemResource("F:/workspace/SpringDataCenterProject/src/main/webapp/WEB-INF/spring/root-context.xml");
	//Naver
	Resource r = new FileSystemResource("/apps/apache-tomcat-8.0.48/webapps/SpringDataCenterProject/WEB-INF/spring/root-context.xml");
	//Ocean
//	Resource r = new FileSystemResource("/apps/tomcat/webapps/SpringDataCenterProject/WEB-INF/spring/root-context.xml");
	
	BeanFactory bf =  new XmlBeanFactory(r);
	SqlSession sqlsession = (SqlSession) bf.getBean("sqlSession"); 
	
    protected IUrlFilter urlFilter     = null;
    protected List<String> urlsToCrawl = new ArrayList<String>();
    protected Set<String>  crawledUrls = new HashSet<String>();
    protected IPageProcessor pageProcessor = null;
    
    public Dcrawler() {
    }

    public void setUrlFilter(IUrlFilter urlFilter) {
        this.urlFilter = urlFilter;
    }

    public void setPageProcessor(IPageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    public void addUrl(String url) {
        this.urlsToCrawl.add(url);
    }
    
    public List<String> crawl(HashMap<String, Object> pMap) {
    	
    	long curSize = 0;
        long startTime = System.currentTimeMillis();
        
        final List<String> bodyResultList = new ArrayList<String>();

            try {

            	for(int i = 0 ; i < Integer.parseInt(pMap.get("page").toString()) ; i++){
            		
            		log.info(pMap.get("url").toString() + i);
            		
            		Document doc = Jsoup.connect(pMap.get("url").toString() + i)
            				.header("authority","search.daum.net")
            				.header("method","GET")
            				.header("path","/search?w=news&q=%EC%BD%94%EC%9D%B8&period=w&sd=20180120000000&ed=20180122235900&nil_search=btn&DA=PGD&enc=utf8&cluster=y&cluster_page=1&p=1")
            				.header("scheme","https")
            				.header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            				.header("accept-encoding","gzip, deflate, br")
            				.header("accept-language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
            				.header("cookie","webid=f39b5de0e6ee11e78bab000af759d210; uvkey=WmStycuFopgAAAY7apoAAAA3; ssab=; _ga=GA1.2.363662692.1516547945; _gid=GA1.2.1583039090.1516547945; ODT=NNSZ_IVRZ_1DVZ_CCBZ_BR1Z_WSAZ_DICZ_; DDT=IIMZ_VO2Z_FGKZ_NKSZ_GG1Z_SNPZ_MS2Z_; DTQUERY=%EC%BD%94%EC%9D%B8; TIARA=TuHoZIlG-4jV_tRfEhNUKjf5JwNHOv-LxWrRwgZfmvlqZotw7ondF_QAKgZKjjXh2NWYvqMbmbZ1PNwu3IMsMA00; webid_sync=1516552666700")
            				.header("upgrade-insecure-requests","1")
            				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            				.referrer("")
            				.get();
            		
        			bodyResultList.add(doc.select("div.cont_inner").toString());
        			
            	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return bodyResultList;
        

    }

    private boolean shouldCrawlUrl(String nextUrl) {
        if(this.urlFilter != null && !this.urlFilter.include(nextUrl)){
            return false;
        }
        if(this.crawledUrls.contains(nextUrl)) { return false; }
        if(nextUrl.startsWith("javascript:"))  { return false; }
        if(nextUrl.contains("mailto:"))        { return false; }
        if(nextUrl.startsWith("#"))            { return false; }
        if(nextUrl.endsWith(".swf"))           { return false; }
        if(nextUrl.endsWith(".pdf"))           { return false; }
        if(nextUrl.endsWith(".png"))           { return false; }
        if(nextUrl.endsWith(".gif"))           { return false; }
        if(nextUrl.endsWith(".jpg"))           { return false; }
        if(nextUrl.endsWith(".jpeg"))          { return false; }

        return true;
    }


}

