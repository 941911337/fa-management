package cn.john.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.john.common.Constants;
import cn.john.dao.TAssetClassMapper;
import cn.john.dto.ClassPageVo;
import cn.john.dto.ClassVo;
import cn.john.exception.BusinessException;
import cn.john.model.TAssetClass;
import cn.john.service.ITAssetClassService;
import cn.john.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 资产类别表  服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TAssetClassServiceImpl extends ServiceImpl<TAssetClassMapper, TAssetClass> implements ITAssetClassService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveClass(ClassVo classVo) {
        long count = baseMapper.selectCount(new LambdaQueryWrapper<TAssetClass>()
                .eq(TAssetClass::getClassName,classVo.getClassName())
                .eq(TAssetClass::getIsDel, Constants.IsDel.NO)
                .ne(classVo.getId() != null,TAssetClass::getId,classVo.getId())
                .last(Constants.LIMIT_ONE));
        if(count > 0){
            throw new BusinessException("类别已存在错误") ;
        }
        TAssetClass assetClass = new TAssetClass();
        BeanUtil.copyProperties(classVo,assetClass);
        //更新流程
        if(classVo.getId() != null){
            baseMapper.updateById(assetClass);
        }else{
            baseMapper.insert(assetClass);
        }
        RedisUtil.del("class");
        initCache();
    }

    @Override
    public PageInfo getPage(ClassPageVo classPageVo) {
        PageHelper.startPage(classPageVo.getPageNum(), classPageVo.getPageSize(),"created_time desc");
        List<TAssetClass> list =  baseMapper.selectList(new LambdaQueryWrapper<TAssetClass>()
                .eq(TAssetClass::getIsDel,Constants.IsDel.NO)
                .like(!StringUtils.isEmpty(classPageVo.getClassName()),TAssetClass::getClassName,classPageVo.getClassName()));
        return PageInfo.of(list);
    }

    @Override
    public void del(Long id) {
        TAssetClass assetClass = new TAssetClass();
        assetClass.setId(id);
        assetClass.setIsDel(1);
        baseMapper.updateById(assetClass);
        RedisUtil.del("class");
        initCache();
    }

    @Override
    public List<ClassVo> getAllClass() {
        List<ClassVo> classVoList = (List<ClassVo>) RedisUtil.get("class");
        if(CollectionUtil.isEmpty(classVoList)){
            classVoList = baseMapper.getAllClass();
            RedisUtil.set("class", (Serializable) classVoList);
        }
        return classVoList;
    }

    @Override
    public void initCache() {
        List<ClassVo> classVoList = baseMapper.getAllClass();
        RedisUtil.set("class", (Serializable) classVoList);
    }
}
