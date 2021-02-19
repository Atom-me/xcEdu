package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.service.BaseService;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.config.CoursePublishConfig;
import com.xuecheng.manage_course.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author atom
 */
@Slf4j
@Service
public class CourseService extends BaseService {

    @Resource
    private CourseBaseRepository courseBaseRepository;

    @Resource
    private CoursePicRepository coursePicRepository;

    @Resource
    private TeachplanMapper teachplanMapper;

    @Resource
    private CoursePlanRepository coursePlanRepository;

    @Resource
    private CourseMarketRepository courseMarketRepository;

    @Resource
    private CmsPageClient cmsPageClient;

    @Resource
    private CoursePublishConfig coursePublishConfig;

    @Resource
    private CourseBaseService courseBaseService;

    @Resource
    private CoursePubRepository coursePubRepository;

    @Resource
    private TeachplanMediaRepository teachplanMediaRepository;

    @Resource
    private TeachplanMediaPubRepository teachplanMediaPubRepository;

    /**
     * 查询课程预览所需数据
     *
     * @param courseId 课程ID
     * @return CourseView
     */
    public CourseView getCourseView(String courseId) {
        CourseView result = new CourseView();

        // 查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        courseBaseOptional.ifPresent(result::setCourseBase);

        // 查询课程图片
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseId);
        coursePicOptional.ifPresent(result::setCoursePic);

        // 查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseId);
        courseMarketOptional.ifPresent(result::setCourseMarket);

        // 查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.findList(courseId);
        result.setTeachplanNode(teachplanNode);

        return result;
    }

    /**
     * 课程预览
     *
     * @param courseId 课程id
     * @return previewUrl
     */
    public String preview(String courseId) {
        //1。请求CMS添加页面
        //2。拼装页面预览URL
        //3。返回页面预览URL

        // 构造cmsPage信息
        CmsPage cmsPage = buildCmsPage(courseId);
        CmsPageResult save = cmsPageClient.save(cmsPage);

        if (save.isSuccess()) {
            return coursePublishConfig.getPreviewUrl() + save.getCmsPage().getPageId();
        }

        return null;
    }

    /**
     * 课程发布
     *
     * @param courseId 课程ID
     * @return 课程页面路径url
     */
    @Transactional
    public String publish(String courseId) {
        // 构造cmsPage信息
        CmsPage cmsPage = buildCmsPage(courseId);

        // 调用CMS一键发布接口 发布课程详情页面到服务器
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }

        // 更新课程状态为"已发布"
        /**
         * 制作中：202001
         * 已发布：202002
         * 已下线：202003
         */
        saveCoursePubState(courseId, "202002");

        // 更新课程索引
        saveCoursePub(courseId, coursePubRepository.findById(courseId).orElse(null));

        // 保存课程计划媒资到待索引表
        saveTeachplanMediaPub(courseId);

        return cmsPostPageResult.getPageUrl();
    }

    /**
     * 保存指定课程的课程计划媒资信息到索引表中
     *
     * @param id 课程ID
     */
    private void saveTeachplanMediaPub(String id) {
        // 查询课程媒资信息
        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(id);

        // 删除原有数据
        teachplanMediaPubRepository.deleteByCourseId(id);

        // 将课程计划媒资信息存储待索引表
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        teachplanMediaList.forEach(teachplanMedia -> {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPubList.add(teachplanMediaPub);
        });

        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);
    }

    /**
     * 保存课程信息
     *
     * @param id        课程ID
     * @param coursePub 课程信息
     * @return CoursePub
     */
    public CoursePub saveCoursePub(String id, CoursePub coursePub) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
        if (coursePubOptional.isPresent()) {
            coursePubNew = coursePubOptional.get();
        }
        if (Objects.isNull(coursePubNew)) {
            coursePubNew = new CoursePub();
        }

        BeanUtils.copyProperties(coursePub, coursePubNew);
        //设置主键
        coursePubNew.setId(id);
        //更新时间戳为最新时间
        coursePub.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePub.setPubTime(date);
        coursePubRepository.save(coursePub);
        return coursePub;

    }

    /**
     * 创建
     *
     * @param id
     * @return
     */
    private CoursePub createCoursePub(String id) {
        CoursePub coursePub = new CoursePub();
        coursePub.setId(id);

        //基础信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            BeanUtils.copyProperties(courseBase, coursePub);
        }
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if (picOptional.isPresent()) {
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }

        //课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if (marketOptional.isPresent()) {
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
        }

        //课程计划
        TeachplanNode teachplanNode = teachplanMapper.findList(id);
        //将课程计划转成json
        String teachPlanString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(teachPlanString);
        return coursePub;
    }

    /**
     * 更新课程状态
     * 状态值列表：
     * 制作中：202001
     * 已发布：202002
     * 已下线：202003
     *
     * @param courseId 课程ID
     * @param status   状态值
     * @return CourseBase
     */
    private CourseBase saveCoursePubState(String courseId, String status) {
        CourseBase courseBase = courseBaseService.findById(courseId);
        //更新发布状态
        courseBase.setStatus(status);
        return courseBaseRepository.save(courseBase);
    }


    /**
     * 使用课程ID构造Cms Page信息
     *
     * @param courseId 课程ID
     * @return CmsPage
     */
    private CmsPage buildCmsPage(String courseId) {
        // 查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if (!courseBaseOptional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_NOT_EXIST);
        }
        CourseBase courseBase = courseBaseOptional.get();

        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(coursePublishConfig.getSiteId());
        cmsPage.setTemplateId(coursePublishConfig.getTemplateId());
        cmsPage.setPageAliase(courseBase.getName());
        cmsPage.setPageName(courseBase.getId() + ".html");
        cmsPage.setPageWebPath(coursePublishConfig.getPageWebPath());
        cmsPage.setPagePhysicalPath(coursePublishConfig.getPagePhysicalPath());
        cmsPage.setDataUrl(coursePublishConfig.getDataUrlPre() + courseBase.getId());
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setPageType("0");

        return cmsPage;
    }

    /**
     * 保存课程计划关联媒资数据
     *
     * @param teachplanMedia 关联树数据
     * @return TeachplanMedia
     */
    public TeachplanMedia saveMedia(TeachplanMedia teachplanMedia) {
        isNullOrEmpty(teachplanMedia, CommonCode.PARAMS_ERROR);
        // 查询课程计划
        Teachplan teachplan = coursePlanRepository.findById(teachplanMedia.getTeachplanId()).orElse(null);
        isNullOrEmpty(teachplan, CourseCode.COURSE_MEDIS_TEACHPLAN_IS_NULL);

        // 只允许叶子节点选择视频
        String grade = teachplan.getGrade();
        if (StringUtils.isEmpty(grade) || !grade.equals("3")) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADE_ERROR);
        }
        TeachplanMedia media;

        Optional<TeachplanMedia> teachplanMediaOptional = teachplanMediaRepository.findById(teachplanMedia.getTeachplanId());
        media = teachplanMediaOptional.orElseGet(TeachplanMedia::new);

        //保存媒资信息与课程计划信息
        media.setTeachplanId(teachplanMedia.getTeachplanId());
        media.setCourseId(teachplanMedia.getCourseId());
        media.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        media.setMediaId(teachplanMedia.getMediaId());
        media.setMediaUrl(teachplanMedia.getMediaUrl());

        return teachplanMediaRepository.save(media);
    }
}
