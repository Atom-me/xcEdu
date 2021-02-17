package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GridFSTest {


    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 文件默认是上传到数据中的 fs.files 和 fs.chunks 中
     * GridFS 用两个集合来存储一个文件：fs.files与fs.chunks。
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testStoreFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("/Users/atom/temp/index_banner.ftl");
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "index_banner.ftl");
        System.err.println(objectId);
    }


    /**
     * 文件下载
     *
     * @throws IOException
     */
    @Test
    public void testGetFile() throws IOException {
        String fileId = "602cc6b4d9d05675b5086e5c";
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
        System.err.println(s);

    }


}