package com.test;

import javax.annotation.Resource;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;

import com.test.demo.elasticsearch.Article;
import com.test.demo.elasticsearch.ElasticSearchPage;
import com.test.demo.elasticsearch.TransportClientRepository;

/**
 *
 * @author jinyouming
 * @email  jinyouming@tuandai.com
 * @date   2017-11-4 下午5:35:09
 * @Copyright Copyright (c) 2017 Niiwoo Inc. All Rights Reserved.
 * @desc
 */
public class ElasticSearchBaseTest extends BaseTest {
    
    @Resource(name="transportClientRepository")
    TransportClientRepository client;
  
    @Test
     public void findByNameAndPrice() throws Exception{
        System.out.println(client.getIdx("blog","article","20171104"));
     }
  
    @Test
    public void saveDoc() throws Exception{
       Article article = new Article();
       article.setDescription("今天是个好日子");
       article.setTitle("好日子");
       System.out.println(client.saveDoc("blog","article","20171104",article));
    }
   
    @Test
    public void searchFullText(){
       Article param = new Article();
       param.setDescription("日子");
       ElasticSearchPage<Article> page= new ElasticSearchPage<Article>();
       page.setPageSize(10);
       HighlightBuilder highlight = new HighlightBuilder();
       highlight.field("description").preTags("<span style=\"color:red\">").postTags("</span>");
       page = client.searchFullText(param,page, highlight,"blog");
       for(Article aa : page.getRetList()){
          System.out.println("===="+aa.getDescription()+"===title:=="+aa.getTitle());
       }
       System.out.println(page.getTotal());
    }

}

