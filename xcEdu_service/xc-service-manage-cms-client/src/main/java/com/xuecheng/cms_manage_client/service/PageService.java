package com.xuecheng.cms_manage_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.cms_manage_client.dao.CmsPageRepository;
import com.xuecheng.cms_manage_client.dao.CmsSiteRepository;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

/**
 * @author atom
 */
@Slf4j
@Service
public class PageService extends BaseService {

    @Resource
    private GridFSBucket gridFSBucket;

    @Resource
    private GridFsTemplate gridFsTemplate;

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Resource
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 保存指定页面ID的html到服务器
     *
     * @param pageId 页面ID
     */
    public void savePageToServerPath(String pageId) {
        CmsPage cmsPage;
        // 查询CmsPage
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_EDITPAGE_NOTEXISTS);
        }
        cmsPage = optionalCmsPage.get();

        // 根据HTML文件ID，从GridFS中获取文件 inputStream
        InputStream inputStream = getFileInputStreamFromMongoDB(cmsPage.getHtmlFileId());
        if (Objects.isNull(inputStream)) {
            log.error("[页面发布消费方] 下载页面失败, 流对象为null, fileId = [{}]", cmsPage.getHtmlFileId());
            return;
        }

        // 写入文件到本地
        CmsSite cmsSite = findSiteInfo(cmsPage.getSiteId());
        String pagePath = cmsSite.getSitePhysicalPath() + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(pagePath);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            log.error("[页面发布消费方] 文件未找到, 文件路径 = [{}]", pagePath);
        } catch (IOException e) {
            log.error("[页面发布消费方] 将文件写入服务器失败, 文件路径 = [{}]", pagePath);
        } finally {
            try {
                if (Objects.nonNull(fileOutputStream)) {
                    fileOutputStream.close();
                }
                inputStream.close();
            } catch (IOException e) {
                log.error("[页面发布消费方] 流对象关闭失败, 错误信息 = ", e);
            }

        }
    }

    /**
     * 根据站点ID获取站点信息
     *
     * @param siteId
     * @return
     */
    private CmsSite findSiteInfo(String siteId) {
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(siteId);
        return cmsSiteOptional.orElse(null);
    }

    /**
     * 从GridFS中获取文件 inputStream
     *
     * @param fileId 文件ID
     * @return 文件内容
     */
    private InputStream getFileInputStreamFromMongoDB(String fileId) {
        //根据HTML文件ID查询HTML文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (Objects.isNull(gridFSFile)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //打开GridFS下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        try {
            return gridFsResource.getInputStream();
        } catch (IOException ignored) {
        }
        return null;
    }

}
