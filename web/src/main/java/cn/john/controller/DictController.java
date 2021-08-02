package cn.john.controller;

import cn.hutool.json.JSONUtil;
import cn.john.dto.JsonMessage;
import cn.john.log.annotation.SysOperateLog;
import cn.john.log.annotation.SysOperateMethodLog;
import cn.john.model.TDict;
import cn.john.service.ITDictService;
import cn.john.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author John Yan
 * @Description DictController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
@RequestMapping("/dict")
@SysOperateLog(module = "字典管理")
public class DictController {

    private final ITDictService dictService;

    public DictController(ITDictService dictService) {
        this.dictService = dictService;
    }


    @GetMapping("/getAllDict")
    @SysOperateMethodLog(method = "查询所有字典")
    public JsonMessage getAllDict() {
        return JsonMessage.success( RedisUtil.get("dict"));
    }


    @GetMapping("/getDict/{type}")
    @SysOperateMethodLog(method = "根据类型查询字典")
    public JsonMessage getDict(@PathVariable("type") String type) {
        return JsonMessage.success( RedisUtil.hGet("dictMap",type));
    }

    @GetMapping("/reloadDict")
    @SysOperateMethodLog(method = "重新加载字典")
    public JsonMessage reloadDict() {
        Map<String, List<TDict>> dictMap = dictService.getAllDict();
        RedisUtil.set("dict", JSONUtil.parse(dictMap));
        dictMap.forEach((k,v)->RedisUtil.hPut("dictMap",k, JSONUtil.parse(v)));
        return JsonMessage.success();
    }









}