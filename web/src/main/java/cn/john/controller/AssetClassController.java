package cn.john.controller;

import cn.john.dto.ClassPageVo;
import cn.john.dto.ClassVo;
import cn.john.dto.JsonMessage;
import cn.john.log.annotation.SysOperateLog;
import cn.john.log.annotation.SysOperateMethodLog;
import cn.john.service.ITAssetClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author John Yan
 * @Description AssetClassController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
@RequestMapping("/assetClass")
@SysOperateLog(module = "资产分类管理")
public class AssetClassController {

    private final ITAssetClassService assetClassService;

    public AssetClassController(ITAssetClassService assetClassService) {
        this.assetClassService = assetClassService;
    }


    @PostMapping("/saveClass")
    @SysOperateMethodLog(method = "保存资产分类")
    public JsonMessage saveClass(@Valid @RequestBody ClassVo classVo, BindingResult bindingResult) {
        assetClassService.saveClass(classVo);
        return JsonMessage.success("成功");
    }


    @PostMapping("/getPage")
    @SysOperateMethodLog(method = "分页查询资产分类")
    public JsonMessage getPage(@Valid @RequestBody ClassPageVo classPageVo, BindingResult bindingResult) {
        return JsonMessage.success(assetClassService.getPage(classPageVo));
    }

    @DeleteMapping("/del/{id}")
    @SysOperateMethodLog(method = "删除资产分类")
    public JsonMessage del(@PathVariable("id") Long id) {
        assetClassService.del(id);
        return JsonMessage.success("成功");
    }

    @GetMapping("/getAllClass")
    @SysOperateMethodLog(method = "获取所有资产分类")
    public JsonMessage getAllClass() {
        return JsonMessage.success(assetClassService.getAllClass());
    }



}