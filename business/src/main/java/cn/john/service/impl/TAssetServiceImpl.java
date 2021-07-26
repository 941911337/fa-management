package cn.john.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.john.common.Constants;
import cn.john.dao.TAssetMapper;
import cn.john.dto.*;
import cn.john.model.TAsset;
import cn.john.model.TDept;
import cn.john.model.TEmployee;
import cn.john.service.ITAssetService;
import cn.john.service.ITDeptService;
import cn.john.service.ITEmployeeService;
import cn.john.service.ITEnclosureService;
import cn.john.util.SysUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资产表  服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TAssetServiceImpl extends ServiceImpl<TAssetMapper, TAsset> implements ITAssetService {


    private final ITEmployeeService employeeService;

    private final ITDeptService deptService;

    private final ITEnclosureService enclosureService;


    public TAssetServiceImpl(ITEmployeeService employeeService, ITDeptService deptService, ITEnclosureService  enclosureService) {
        this.employeeService = employeeService;
        this.deptService = deptService;
        this.enclosureService = enclosureService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAsset(AssetVo vo) {
        boolean isUpdate = vo.getId() != null;
        //处理 雇员 和 部门
        TEmployee employee = new TEmployee();
        employee.setName(vo.getEmployee());
        employeeService.saveEmployee(employee, !isUpdate);
        TDept dept = new TDept();
        dept.setDeptName(vo.getDept());
        dept.setDeptCode(SysUtil.getUuid());
        deptService.saveDept(dept, false);
        //资产信息处理
        Long id = vo.getId();
        TAsset asset = new TAsset();
        BeanUtil.copyProperties(vo, asset);
        if (id != null) {
            baseMapper.updateById(asset);
        } else {
            baseMapper.insert(asset);
            id = asset.getId();
        }
        //附件处理
        if (CollectionUtil.isNotEmpty(vo.getEnclosureVoList())) {
            enclosureService.save(vo.getEnclosureVoList(), id);
        }
    }

    @Override
    public PageInfo getPage(AssetPageVo assetPageVo) {
        PageHelper.startPage(assetPageVo.getPageNum(), assetPageVo.getPageSize(),"created_time desc");
        List<TAsset> list =  baseMapper.selectList(new LambdaQueryWrapper<TAsset>()
                .eq(TAsset::getIsDel, Constants.IsDel.NO)
                .like(!StringUtils.isEmpty(assetPageVo.getAssetName()),TAsset::getName,assetPageVo.getAssetName())
                .like(!StringUtils.isEmpty(assetPageVo.getAssetCode()),TAsset::getCode,assetPageVo.getAssetCode())
                .like(assetPageVo.getUseStatus() != null,TAsset::getUseStatus,assetPageVo.getUseStatus()));
        List<AssetVo> resultList  = new ArrayList<>();
        Map<String,String> assetStatusDict = SysUtil.getDict("assetStatus");
        Map<Long,String> classDict = SysUtil.getClassDict();
        for (TAsset asset : list) {
            AssetVo assetVo = new AssetVo();
            BeanUtil.copyProperties(asset,assetVo);
            assetVo.setStatusName(assetStatusDict.get(assetVo.getUseStatus().toString()));
            assetVo.setClassName(classDict.get(assetVo.getClassId()));
            resultList.add(assetVo);
        }
        return PageInfo.of(resultList);
    }

    @Override
    public AssetVo detail(Long id) {
        AssetVo result = new AssetVo();
        TAsset asset  =  baseMapper.selectById(id);
        Map<String,String> assetStatusDict = SysUtil.getDict("assetStatus");
        Map<Long,String> classDict = SysUtil.getClassDict();
        if(asset != null){
            BeanUtil.copyProperties(asset,result);
            List<EnclosureVo> enclosureVoList = enclosureService.getEnclosureByRelId(id);
            result.setEnclosureVoList(enclosureVoList);
            result.setStatusName(assetStatusDict.get(result.getUseStatus().toString()));
            result.setClassName(classDict.get(result.getClassId()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(Long id) {
        TAsset asset  =  baseMapper.selectById(id);
        asset.setId(null);
        baseMapper.insert(asset);
        enclosureService.copy(id,asset.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(Long id) {
        baseMapper.deleteById(id);
        enclosureService.del(id);
    }

    @Override
    public List<AssetExportVo> getAllList(String assetName, String assetCode, Integer useStatus) {
        List<TAsset> list =  baseMapper.selectList(new LambdaQueryWrapper<TAsset>()
                .eq(TAsset::getIsDel, Constants.IsDel.NO)
                .like(!StringUtils.isEmpty(assetName),TAsset::getName,assetName)
                .like(!StringUtils.isEmpty(assetCode),TAsset::getCode,assetCode)
                .like(useStatus != null,TAsset::getUseStatus,useStatus));
        List<AssetExportVo> resultList  = new ArrayList<>();
        Map<String,String> assetStatusDict = SysUtil.getDict("assetStatus");
        Map<Long,String> classDict = SysUtil.getClassDict();
        for (TAsset asset : list) {
            AssetExportVo assetExportVo = new AssetExportVo();
            BeanUtil.copyProperties(asset,assetExportVo);
            assetExportVo.setStatusName(assetStatusDict.get(asset.getUseStatus().toString()));
            assetExportVo.setClassName(classDict.get(asset.getClassId()));
            assetExportVo.setCreateTime(LocalDateTimeUtil.formatNormal(asset.getCreatedTime()));
            assetExportVo.setBuyerTime(LocalDateTimeUtil.formatNormal(asset.getBuyTime()));
            resultList.add(assetExportVo);
        }
        return resultList;
    }
}
