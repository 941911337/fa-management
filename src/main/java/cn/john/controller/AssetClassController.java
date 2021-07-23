package cn.john.controller;

import cn.john.dto.ClassPageVo;
import cn.john.dto.ClassVo;
import cn.john.dto.JsonMessage;
import cn.john.service.AssetClassService;
import cn.john.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author yanzhengwei
 * @Description AssetClassController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
@RequestMapping("/assetClass")
public class AssetClassController {

    @Autowired
    private AssetClassService assetClassService;



    @PostMapping("/saveClass")
    public JsonMessage saveClass(@Valid @RequestBody ClassVo classVo, BindingResult bindingResult) {
        JsonMessage message = SysUtil.paramValid(bindingResult);
        if (message != null) {
            return message;
        }
        assetClassService.saveClass(classVo);
        return JsonMessage.success("成功");
    }


    @PostMapping("/getPage")
    public JsonMessage getPage(@Valid @RequestBody ClassPageVo classPageVo, BindingResult bindingResult) {
        JsonMessage message = SysUtil.paramValid(bindingResult);
        if (message != null) {
            return message;
        }
        return JsonMessage.success(assetClassService.getPage(classPageVo));
    }

    @DeleteMapping("/del/{id}")
    public JsonMessage del(@PathVariable("id") Long id) {
        assetClassService.del(id);
        return JsonMessage.success("成功");
    }

    @GetMapping("/getAllClass")
    public JsonMessage getAllClass() {
        return JsonMessage.success(assetClassService.getAllClass());
    }



}