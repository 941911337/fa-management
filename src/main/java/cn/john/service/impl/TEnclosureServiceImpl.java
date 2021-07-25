package cn.john.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.john.common.Constants;
import cn.john.dao.TEnclosureMapper;
import cn.john.dto.EnclosureVo;
import cn.john.model.TEnclosure;
import cn.john.service.ITEnclosureService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 附件表  服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TEnclosureServiceImpl extends ServiceImpl<TEnclosureMapper, TEnclosure> implements ITEnclosureService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<EnclosureVo> list,Long relId){
        List<EnclosureVo> noId = new ArrayList<>();
        //编辑 存在Id的附件不操作 不存在的 删除原有的文件 新增新附件
        if (relId != null){
            List<Long> hasId = new ArrayList<>();
            for (EnclosureVo enclosureVo : list) {
                if(enclosureVo.getId() != null){
                    hasId.add(enclosureVo.getId());
                }else{
                    noId.add(enclosureVo);
                }
            }
            baseMapper.delBatch(relId,hasId);
        }else{
            noId = list;
        }
        for (EnclosureVo enclosureVo : noId) {
            TEnclosure enclosure = new TEnclosure();
            BeanUtil.copyProperties(enclosureVo,enclosure);
            if (relId != null){
                enclosure.setRelId(relId);
            }
            baseMapper.insert(enclosure);
        }

    }

    @Override
    public List<EnclosureVo> getEnclosureByRelId(Long relId){
        return baseMapper.getEnclosureByRelId(relId);
    }


    @Override
    public void copy(Long oldRelId,Long newRelId){
        baseMapper.copy(oldRelId,newRelId);
    }


    @Override
    public void del(Long relId){
        baseMapper.delBatch(relId,null);
    }


    @Override
    public List<TEnclosure> getNeedBackUp() {
        PageHelper.startPage(1,20,false);
        List<TEnclosure> needBackUp = baseMapper.selectList(new LambdaQueryWrapper<TEnclosure>()
                .eq(TEnclosure::getIsDel, Constants.IsDel.NO)
                .isNull(TEnclosure::getRevision));
        return  needBackUp;
    }

    @Override
    public void markDownLoad(Long id) {
        TEnclosure enclosure = new TEnclosure();
        enclosure.setId(id);
        enclosure.setRevision(1);
        baseMapper.updateById(enclosure);
    }
}
