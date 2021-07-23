package cn.john.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.john.dao.AssetClassMapper;
import cn.john.dto.ClassPageVo;
import cn.john.dto.ClassVo;
import cn.john.exception.BusinessException;
import cn.john.model.AssetClass;
import cn.john.model.AssetClassExample;
import cn.john.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yanzhengwei
 * @Description AssetClassService
 * @Date 2021/7/17
 **/
@Slf4j
@Service
public class AssetClassService extends BaseService<AssetClassMapper>{


    /**
     * 新增/更新分类
     * @param classVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveClass(ClassVo classVo){
        AssetClassExample assetClassExample = new AssetClassExample();
        AssetClassExample.Criteria criteria = assetClassExample.createCriteria();
        criteria.andClassNameEqualTo(classVo.getClassName()).andIsDelEqualTo(0);
        if(classVo.getId() != null){
            criteria.andIdNotEqualTo(classVo.getId());
        }
        long count = baseMapper.countByExample(assetClassExample);
        if(count > 0){
            throw new BusinessException("类别已存在错误") ;
        }
        AssetClass assetClass = new AssetClass();
        BeanUtil.copyProperties(classVo,assetClass);
        //更新流程
        if(classVo.getId() != null){
            updateInfo(assetClass);
            baseMapper.updateByPrimaryKeySelective(assetClass);
        }else{
            createInfo(assetClass);
            baseMapper.insertSelective(assetClass);
        }
        RedisUtil.del("class");
        initCache();
    }


    public PageInfo getPage(ClassPageVo classPageVo){
        AssetClassExample assetClassExample = new AssetClassExample();
        AssetClassExample.Criteria criteria = assetClassExample.createCriteria();
        criteria.andIsDelEqualTo(0);
        if(!StringUtils.isEmpty(classPageVo.getClassName())){
            criteria.andClassNameLike("%"+classPageVo.getClassName()+"%");
        }
        PageHelper.startPage(classPageVo.getPageNo(), classPageVo.getPageSize(),"created_time desc");
        List<AssetClass> list =  baseMapper.selectByExample(assetClassExample);
        return PageInfo.of(list);

    }

    public void del(Long id){
        AssetClass assetClass = new AssetClass();
        assetClass.setId(id);
        assetClass.setIsDel(1);
        updateInfo(assetClass);
        baseMapper.updateByPrimaryKeySelective(assetClass);
        RedisUtil.del("class");
        initCache();
    }

    public List<ClassVo> getAllClass(){
        List<ClassVo> classVoList = (List<ClassVo>) RedisUtil.get("class");
        if(CollectionUtil.isEmpty(classVoList)){
            classVoList = baseMapper.getAllClass();
            RedisUtil.set("class", (Serializable) classVoList);
        }
        return classVoList;
    }

    public void initCache(){
        List<ClassVo> classVoList = baseMapper.getAllClass();
        RedisUtil.set("class", (Serializable) classVoList);
    }
}
