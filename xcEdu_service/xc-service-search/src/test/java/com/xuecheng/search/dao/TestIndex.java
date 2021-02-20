package com.xuecheng.search.dao;

import com.xuecheng.framework.domain.search.EsCoursePub;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Atom
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndex {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 删除索引库对象
     */
    @Test
    public void testDeleteIndexUseESTemplate() {
        boolean b = elasticsearchTemplate.deleteIndex("xc_course_media");
        System.err.println(b);
    }

    /**
     * 创建索引库 with settings
     */
    @Test
    public void testCreateIndexWithSettings() {
        Map<String, Object> settings = new HashMap<>();
        settings.put("number_of_shards", 1);
        settings.put("number_of_replicas", 0);
        boolean xx_course = elasticsearchTemplate.createIndex("xx_course", settings);
        System.err.println(xx_course);
    }

    /**
     * 为索引库创建映射
     */
    @Test
    public void testCreateIndexWithMappings() {
        String mappings = "{\n" +
                "  \"properties\": {\n" +
                "    \"name\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"description\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"studymodel\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        boolean b = elasticsearchTemplate.putMapping("xx_course", "doc", mappings);
        System.err.println(b);
    }


    /**
     * 查询指定索引库的映射
     */
    @Test
    public void testQueryIndexMappings() {
        Map mapping = elasticsearchTemplate.getMapping("xx_course", "doc");
        System.err.println(mapping);
    }


    /**
     * 添加文档
     */
    @Test
    public void testAddDocument() {
        EsCoursePub coursePub = new EsCoursePub();
        coursePub.setName("java课程");
        coursePub.setDescription("基础课程");
        coursePub.setStudymodel("12121");

        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(coursePub);
        String documentID = elasticsearchTemplate.index(indexQuery);
        System.err.println(documentID);
    }


}
