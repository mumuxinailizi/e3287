package com.itheima.create.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class CreateIndex {
	
	/**
	 * 需求:创建索引库索引(单词) 
	 * @throws Exception 
	 */
	@Test
	public void addIndex() throws Exception{
		//指定存储路径,存储索引单词
		String indexPath = "F:\\indexs";
		//指定目录对象,关联索引单词存储位置
		FSDirectory directory = FSDirectory.open(new File(indexPath));
		//创建一个基本分词器 ==把文档拆分成一个一个单词(索引)
		//1,使用基本分词器创建索引库
		//Analyzer analyzer = new StandardAnalyzer();
		
		//2,使用CJK分词器创建索引库
		//Analyzer analyzer = new CJKAnalyzer();
		
		//3,使用聪明的中国人分词器
		//Analyzer analyzer = new SmartChineseAnalyzer();
		
		//4,使用IK分词器创建索引库
		Analyzer analyzer = new IKAnalyzer();
		
		//创建索引库写对象核心对象配置对象
		//参数1:指定lucene使用版本
		//参数2:指定所使用分词器
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//创建写索引库核心对象
		IndexWriter indexWriter = new IndexWriter(directory,iwc);
		//创建文档对象,文档对象数据来自网页,数据库,文件系统
		//网页变成文档对象==网页特点: 网页标题,网页内容,网页url
		//文件服务器:===特点: 文件标题,描述,文件内容,文件服务器url
		//数据库数据====特点:字段===文档对象域字段
		Document doc = new Document();
		//文档对象封装很多域字段,域字段封装数据
		//StringField类似于数据库varChar,表示索引域字段类型
		//特点:StringField==不分词==有索引==Store.NO/Store.YES
		doc.add(new StringField("id", "1010110", Store.NO));
		//文档标题  TextField文档域字段类型
		//TextField:特点==必须分词==必须索引==Store.NO/Store.YES
		doc.add(new TextField("title", "黄晓明在传智播客学习lucene经典教程也使则!", Store.YES));
		//文档内容
		//TextField:特点==必须分词==必须索引==Store.NO/Store.YES
		doc.add(new TextField("content", "Lucene并不是现成的搜索引擎产品，但可以用来制作搜索引擎产品", Store.NO));
		//写索引库==把文档写入索引库
		indexWriter.addDocument(doc);
		//提交事物
		indexWriter.commit();
		
	}

}
