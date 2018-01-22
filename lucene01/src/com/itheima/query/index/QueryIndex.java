package com.itheima.query.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class QueryIndex {
	
	/**
	 * 需求:查询索引库索引
	 * 查询原理:根据索引单词(最小分词单元)进行匹配查询
	 * @throws Exception 
	 */
	@Test
	public void searchIndex() throws Exception{
		//指定索引库存储位置
		String indexPath = "F:\\indexs";
		//读取索引库
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		//创建查询索引库核心对象
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		
		//指定查询关键词
		String qName = "lucene";
		//创建查询解析器,对查询关键词进行分词
		//参数1:指定查询域字段
		//参数2:指定使用什么分词器,使用和创建索引库相同的分词器
		QueryParser qParser = new QueryParser("title", new IKAnalyzer());
		//解析查询关键词,进行分词,返回分词查询包装类对象
		Query query = qParser.parse(qName);
		
		//使用查询核心对象查询,返回文档概要信息
		//查询前10条:查询得分最高的前10条,匹配度高(出现词语频率高),得分就高
		//文档概要信息:文档得分,文档id,文档总记录数
		TopDocs topDocs = indexSearcher.search(query, 10);
		//获取文档总记录数
		int totalHits = topDocs.totalHits;
		System.out.println("命中总记录数:"+totalHits);
		//获取文档得分,文档id
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		//循环获取
		for (ScoreDoc sdoc : scoreDocs) {
			//获取文档id
			int docID = sdoc.doc;
			System.out.println("文档id:"+docID);
			//获取文档得分
			float score = sdoc.score;
			System.out.println("文档得分:"+score);
			//根据文档id唯一定位一个文档
			Document doc = indexSearcher.doc(docID);
			//获取文档域id
			String id = doc.get("id");
			System.out.println("文档域id:"+id);
			//获取文档标题
			String title = doc.get("title");
			System.out.println("文档域字段标题:"+title);
			//获取文档内容
			String content = doc.get("content");
			System.out.println("文档域字段内容:"+content);
			
			
			
			
		}
		
	}

}
