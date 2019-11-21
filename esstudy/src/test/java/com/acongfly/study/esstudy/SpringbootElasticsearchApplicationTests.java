package com.acongfly.study.esstudy;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.study.esstudy.elasticsearchStudy.Article;
import com.acongfly.study.esstudy.elasticsearchStudy.ArticleSearchRepository;
import com.acongfly.study.esstudy.elasticsearchStudy.Author;
import com.acongfly.study.esstudy.elasticsearchStudy.Tutorial;

import cn.hutool.json.JSONUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

    @Test
    public void contextLoads() {}

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 参看：https://www.cnblogs.com/wenbronk/p/6432990.html https://www.jianshu.com/p/a3694b13bf89
     */

    @Test
    public void testSaveArticleIndex() {
        UpdateRequest updateRequest = new UpdateRequest();
        // updateRequest.

        Author author = new Author();
        author.setId(2L);
        author.setName("王五");
        author.setRemark(null);
        author.setAge(20);

        Tutorial tutorial = new Tutorial();
        tutorial.setId(2L);
        tutorial.setName("elastic3 search");

        List<Author> authors = Lists.newArrayList();
        authors.add(author);
        authors.add(author);

        Article article = new Article();
        article.setId(123457891L);
        article.setTitle("spring boot integrate elasticsearch");
        article.setAbstracts("elasticsearch test update");
        article.setTutorial(tutorial);
        article.setAuthor(author);
        article.setContent("elasticsearch based on lucene");
        article.setPostTime("20180704");
        article.setClickCount("1");
        article.setAuthors(authors);
        // article.setCreateTime(new Date());
        articleSearchRepository.save(article);

    }

    @Test
    public void testSearch() {
        String queryString = "elasticsearch";// 搜索关键字
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<Article> searchResult = articleSearchRepository.search(builder);
        Iterator<Article> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testDel() {
        Article article = new Article();
        article.setId(12345678900987L);
        articleSearchRepository.delete(article);
    }

    @Test
    public void testSearch01() {

        Long documentId = 123457891L;
        Article article = new Article();
        article.setId(documentId);
        // article.setTitle("elasticsearch");

        QueryStringQueryBuilder id =
            QueryBuilders.queryStringQuery(documentId + "").field("id").defaultOperator(Operator.AND);
        Iterable<Article> search = articleSearchRepository.search(id);
        Iterator<Article> iterator = search.iterator();
        while (iterator.hasNext()) {
            System.out.println("***********************" + JSONUtil.toJsonStr(iterator.next()));
        }
    }

    /**
     * 按照字段查询
     */
    @Test
    public void testSearch02() {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        // title = elasticsearch
        query.must(new QueryStringQueryBuilder("elasticsearch").field("title"));
        // age 属于[20,30）
        query.must(new RangeQueryBuilder("author.age").gte(10).lt(30));

        Iterable<Article> search = articleSearchRepository.search(query);
        Iterator<Article> iterator = search.iterator();
        while (iterator.hasNext()) {
            System.out.println("***********************" + iterator.next());
        }
    }

    /**
     * 查询所有
     */
    @Test
    public void testSearch03() {
        Iterable<Article> all = articleSearchRepository.findAll();
        Iterator<Article> iterator = all.iterator();
        while (iterator.hasNext()) {
            System.out.println("***********************" + JSONUtil.toJsonStr(iterator.next()));
        }
    }

    @Test
    public void testSearch04() {
        MatchPhrasePrefixQueryBuilder slp = QueryBuilders.matchPhrasePrefixQuery("authors.name", "李四");
        Iterable<Article> search = articleSearchRepository.search(slp);
        Iterator<Article> iterator = search.iterator();
        while (iterator.hasNext()) {
            System.out.println("***********************" + JSONUtil.toJsonStr(iterator.next()));
        }
    }

    /**
     * 多词条查询
     */
    @Test
    public void testSearch05() {
        QueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("abstracts", Lists.newArrayList("elasticsearch"));
        Iterable<Article> search = articleSearchRepository.search(termsQueryBuilder);
        Iterator<Article> iterator = search.iterator();
        while (iterator.hasNext()) {
            System.out.println("***********************" + JSONUtil.toJsonStr(iterator.next()));
        }
    }

    /**
     * 模糊查询
     */
    @Test
    public void testSearch06() {
        QueryBuilder termsQueryBuilder = QueryBuilders.fuzzyQuery("author.name", "王");
        Iterable<Article> search = articleSearchRepository.search(termsQueryBuilder);
        Iterator<Article> iterator = search.iterator();
        while (iterator.hasNext()) {
            System.out.println("***********************" + JSONUtil.toJsonStr(iterator.next()));
        }
    }

    @Test
    public void testSearch07() {
        Optional<Article> byId = articleSearchRepository.findById(1234579L);
        if (byId.isPresent()) {
            Article article = byId.get();
            System.out.println(article);
            System.out.println("1111111111111111111111111");
        } else {
            System.out.println(byId.get());
            System.out.println("222222222222222222222222222");
        }
    }

    /**
     * 部分更新(https://www.javazhiyin.com/4588.html#3%E6%9B%B4%E6%96%B0)
     * 
     * @throws IOException
     */
    @Test
    public void testUpdate() throws IOException {

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("projectname");
        updateRequest.type("article");
        updateRequest.id(123457891 + "");
        updateRequest.doc(jsonBuilder().startObject().field("title", "test update").endObject());
        UpdateQuery updateQuery = new UpdateQueryBuilder().withId(123457891 + "").withIndexName("projectname")
            .withType("article").withClass(Article.class).withUpdateRequest(updateRequest).build();

        DocWriteResponse.Result result = elasticsearchTemplate.update(updateQuery).getResult();
        System.out.println(result);

    }
}