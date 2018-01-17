package com.spring.data.crawl.core;

import org.jsoup.nodes.Document;

/**
 */
public interface IPageProcessor {
    public void process(String url, Document doc);
}
