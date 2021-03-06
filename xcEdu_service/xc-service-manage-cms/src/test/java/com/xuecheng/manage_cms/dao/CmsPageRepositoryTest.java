package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsPageService cmsPageService;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.err.println(all);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testFindPage() {
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<CmsPage> all = cmsPageRepository.findAll(pageRequest);
        System.err.println(all.getContent());
    }


    @Test
    public void testInsert() {
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("atom--1");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);

    }


    /**
     * 删除
     */
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("602b7c6fd9d0561f58be5921");
    }


    /**
     * 修改，修改记录也是使用save方法
     */
    @Test
    public void testUpdate() {
        Optional<CmsPage> optional = cmsPageRepository.findById("602b7db8d9d0561f820c6448");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("atom-测试");
            cmsPageRepository.save(cmsPage);
        }
    }

    @Test
    public void testFindByPageName(){
        CmsPage page = cmsPageRepository.findByPageName("atom-测试");
        System.err.println(page);
    }


    /**
     * 文件下载
     *
     * @throws IOException
     */
    @Test
    public void testGetFile() throws IOException {
        String fileId = "5d7b815d5f31573e2021d898";
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(s);

    }



    @Test
    public void testGenHtml() {
        String pageId = "5a795ac7dd573c04508f3a56";
        // 生成html
        String s = cmsPageService.genHtml(pageId);
        System.out.println(s);
    }


}