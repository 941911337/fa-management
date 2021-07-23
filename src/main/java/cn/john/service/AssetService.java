package cn.john.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.john.dao.AssetMapper;
import cn.john.dto.*;
import cn.john.model.*;
import cn.john.utils.RedisUtil;
import cn.john.utils.SysUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author yanzhengwei
 * @Description AssetService
 * @Date 2021/7/18
 **/
@Slf4j
@Service
public class AssetService extends BaseService<AssetMapper> {

    private final EmployeeService employeeService;

    private final DeptService deptService;

    private final EnclosureService enclosureService;


    public AssetService(EmployeeService employeeService, DeptService deptService, EnclosureService enclosureService) {
        this.employeeService = employeeService;
        this.deptService = deptService;
        this.enclosureService = enclosureService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAsset(AssetVo vo){
        boolean isUpdate = vo.getId() != null;
        //处理 雇员 和 部门
        Employee employee = new Employee();
        employee.setName(vo.getEmployee());
        employeeService.saveEmployee(employee,!isUpdate);
        Dept dept = new Dept();
        dept.setDeptName(vo.getDept());
        dept.setDeptCode(SysUtil.getUuid());
        deptService.saveDept(dept,false);
        //资产信息处理
        Long id = vo.getId();
        Asset asset = new Asset();
        BeanUtil.copyProperties(vo,asset);
        if(id != null){
            updateInfo(asset);
            baseMapper.updateByPrimaryKeySelective(asset);
        }else{
            createInfo(asset);
            baseMapper.insertSelective(asset);
            id = asset.getId();
        }
        //附件处理
        if(CollectionUtil.isNotEmpty(vo.getEnclosureVoList())){
            enclosureService.save(vo.getEnclosureVoList(),id);
        }
    }


    public PageInfo getPage(AssetPageVo assetPageVo){
        AssetExample assetExample = new AssetExample();
        AssetExample.Criteria criteria = assetExample.createCriteria();
        criteria.andIsDelEqualTo(0);
        if(!StringUtils.isEmpty(assetPageVo.getAssetName())){
            criteria.andNameLike("%"+assetPageVo.getAssetName()+"%");
        }
        if(!StringUtils.isEmpty(assetPageVo.getAssetCode())){
            criteria.andCodeLike("%"+assetPageVo.getAssetCode()+"%");
        }
        if(assetPageVo.getUseStatus() != null ){
            criteria.andUseStatusEqualTo(assetPageVo.getUseStatus());
        }
        PageHelper.startPage(assetPageVo.getPageNo(), assetPageVo.getPageSize(),"created_time desc");
        List<Asset> list =  baseMapper.selectByExample(assetExample);
        List<AssetVo> resultList  = new ArrayList<>();
        List<Dict> dict = JSONUtil.toList(JSONUtil.toJsonStr(RedisUtil.hGet("dictMap","assetStatus")),Dict.class);
        Map<String,String> assetStatusDict = dict.stream().collect(Collectors.toMap(Dict::getValue, Dict::getLabel));
        List<ClassVo> classVoList = (List<ClassVo>)  RedisUtil.get("class");
        Map<Long,String> classDict = classVoList.stream().collect(Collectors.toMap(ClassVo::getId, ClassVo::getClassName));
        for (Asset asset : list) {
            AssetVo assetVo = new AssetVo();
            BeanUtil.copyProperties(asset,assetVo);
            assetVo.setStatusName(assetStatusDict.get(assetVo.getUseStatus().toString()));
            assetVo.setClassName(classDict.get(assetVo.getClassId()));
            resultList.add(assetVo);
        }
        return PageInfo.of(resultList);
    }


    public AssetVo detail(Long id){
        AssetVo result = new AssetVo();
        Asset asset  =  baseMapper.selectByPrimaryKey(id);
        List<Dict> dict = JSONUtil.toList(JSONUtil.toJsonStr(RedisUtil.hGet("dictMap","assetStatus")),Dict.class);
        Map<String,String> assetStatusDict = dict.stream().collect(Collectors.toMap(Dict::getValue, Dict::getLabel));
        List<ClassVo> classVoList = (List<ClassVo>)  RedisUtil.get("class");
        Map<Long,String> classDict = classVoList.stream().collect(Collectors.toMap(ClassVo::getId, ClassVo::getClassName));
        if(asset != null){
            BeanUtil.copyProperties(asset,result);
            List<EnclosureVo> enclosureVoList = enclosureService.getEnclosureByRelId(id);
            result.setEnclosureVoList(enclosureVoList);
            result.setStatusName(assetStatusDict.get(result.getUseStatus().toString()));
            result.setClassName(classDict.get(result.getClassId()));
        }
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    public void copy(Long id){
        Asset asset  =  baseMapper.selectByPrimaryKey(id);
        asset.setId(null);
        createInfo(asset);
        baseMapper.insertSelective(asset);
        enclosureService.copy(id,asset.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(Long id){
        Asset asset = new Asset();
        asset.setId(id);
        asset.setIsDel(1);
        updateInfo(asset);
        baseMapper.updateByPrimaryKeySelective(asset);
        enclosureService.del(id);
    }

    public List<AssetExportVo> getAllList(String assetName, String assetCode, Integer useStatus){
        AssetExample assetExample = new AssetExample();
        AssetExample.Criteria criteria = assetExample.createCriteria();
        criteria.andIsDelEqualTo(0);
        if(!StringUtils.isEmpty(assetName)){
            criteria.andNameLike("%"+assetName+"%");
        }
        if(!StringUtils.isEmpty(assetCode)){
            criteria.andCodeLike("%"+assetCode+"%");
        }
        if(useStatus != null ){
            criteria.andUseStatusEqualTo(useStatus);
        }
        List<Asset> list =  baseMapper.selectByExample(assetExample);
        List<AssetExportVo> resultList  = new ArrayList<>();
        List<Dict> dict = JSONUtil.toList(JSONUtil.toJsonStr(RedisUtil.hGet("dictMap","assetStatus")),Dict.class);
        Map<String,String> assetStatusDict = dict.stream().collect(Collectors.toMap(Dict::getValue, Dict::getLabel));
        List<ClassVo> classVoList = (List<ClassVo>)  RedisUtil.get("class");
        Map<Long,String> classDict = classVoList.stream().collect(Collectors.toMap(ClassVo::getId, ClassVo::getClassName));
        for (Asset asset : list) {
            AssetExportVo assetExportVo = new AssetExportVo();
            BeanUtil.copyProperties(asset,assetExportVo);
            assetExportVo.setStatusName(assetStatusDict.get(asset.getUseStatus().toString()));
            assetExportVo.setClassName(classDict.get(asset.getClassId()));
            assetExportVo.setCreateTime(DateUtil.formatDateTime(asset.getCreatedTime()));
            assetExportVo.setBuyerTime(DateUtil.formatDateTime(asset.getBuyTime()));
            resultList.add(assetExportVo);
        }
        return resultList;
    }
}
