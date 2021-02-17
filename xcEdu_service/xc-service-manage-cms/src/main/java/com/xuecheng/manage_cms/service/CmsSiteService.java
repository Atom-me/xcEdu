package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author atom
 */
@Slf4j
@Service
public class CmsSiteService {

    @Resource
    private CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult findAll() {
        List<CmsSite> cmsSiteList = cmsSiteRepository.findAll();
        QueryResult<CmsSite> queryResult = new QueryResult<>(cmsSiteList, cmsSiteList.size());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 分页查询页面列表
     *
     * @param page 当前页码
     * @param size 每页记录数
     */
    public QueryResponseResult findList(int page, int size) {
        if (page <= 0) {
            page = 1;
        }
        // 页码从0开始
        page = page - 1;
        // 查询
        Page<CmsSite> siteResult = cmsSiteRepository.findAll(PageRequest.of(page, size));
        QueryResult<CmsSite> cmsSiteQueryResult = new QueryResult<>();
        cmsSiteQueryResult.setList(siteResult.getContent());
        cmsSiteQueryResult.setTotal(siteResult.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, cmsSiteQueryResult);
    }

    /**
     * 新增站点
     *
     * @param cmsSite 站点
     */
    public CmsSite add(CmsSite cmsSite) {
        return cmsSiteRepository.insert(cmsSite);
    }

    /**
     * 按ID查询站点
     *
     * @param siteId 站点ID
     */
    public CmsSite findBySiteId(String siteId) {
        Optional<CmsSite> cmsSite = cmsSiteRepository.findById(siteId);
        return cmsSite.orElse(null);
    }

    /**
     * 编辑站点
     *
     * @param cmsSite 站点
     */
    public CmsSite edit(CmsSite cmsSite) {
        // 查询
        if (Objects.nonNull(cmsSite) && StringUtils.isNotBlank(cmsSite.getSiteId())) {
            Optional<CmsSite> optionalCmsSite = cmsSiteRepository.findById(cmsSite.getSiteId());
            if (optionalCmsSite.isPresent()) {
                CmsSite one = optionalCmsSite.get();
                // 执行更新
//                one.setSiteCreateTime(cmsSite.getSiteCreateTime());
//                one.setSiteDomain(cmsSite.getSiteDomain());
//                one.setSiteName(cmsSite.getSiteName());
//                one.setSitePort(cmsSite.getSitePort());
//                one.setSiteWebPath(cmsSite.getSiteWebPath());
//                one.setSitePhysicalPath(cmsSite.getSitePhysicalPath());
                BeanUtils.copyProperties(cmsSite,one);
                // 保存
                return cmsSiteRepository.save(one);
            }
        }

        return null;
    }

    /**
     * 删除指定ID的站点
     *
     * @param siteId 站点ID
     */
    public void deleteById(String siteId) {
        // 查询
        cmsSiteRepository.deleteById(siteId);
    }
}
