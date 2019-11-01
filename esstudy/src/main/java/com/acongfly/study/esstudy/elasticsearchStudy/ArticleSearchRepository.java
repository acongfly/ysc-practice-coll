package com.acongfly.study.esstudy.elasticsearchStudy;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * description: 文章reposiroty 泛型的参数分别是实体类型和主键类型
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019/1/10
 * <p>
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {

}