package cn.john.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.john.dao.EnclosureMapper;
import cn.john.dto.EnclosureVo;
import cn.john.model.Enclosure;
import cn.john.model.EnclosureExample;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yanzhengwei
 * @Description DeptService
 * @Date 2021/7/18
 **/
@Slf4j
@Service
public class EnclosureService extends  BaseService<EnclosureMapper> {

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
            Enclosure enclosure = new Enclosure();
            BeanUtil.copyProperties(enclosureVo,enclosure);
            if (relId != null){
                enclosure.setRelId(relId);
            }
            createInfo(enclosure);
            baseMapper.insertSelective(enclosure);
        }


    }

    public List<EnclosureVo> getEnclosureByRelId(Long relId){
        return baseMapper.getEnclosureByRelId(relId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void copy(Long oldRelId,Long newRelId){
         baseMapper.copy(oldRelId,newRelId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(Long relId){
        baseMapper.delBatch(relId,null);
    }

    public List<Enclosure> getNeedBackUp(){
        PageHelper.startPage(1,20,false);
        EnclosureExample enclosureExample = new EnclosureExample();
        enclosureExample.createCriteria().andIsDelEqualTo(0).andRevisionIsNull();
        List<Enclosure> needBackUp = baseMapper.selectByExampleWithBLOBs(enclosureExample);
        return  needBackUp;
    }


    public void markDownLoad(Long id){
        Enclosure enclosure = new Enclosure();
        enclosure.setId(id);
        enclosure.setRevision(1);
        updateInfo(enclosure);
        baseMapper.updateByPrimaryKeySelective(enclosure);
    }

}
