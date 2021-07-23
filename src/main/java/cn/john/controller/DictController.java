package cn.john.controller;

import cn.hutool.json.JSONUtil;
import cn.john.dto.JsonMessage;
import cn.john.model.Dict;
import cn.john.service.DictService;
import cn.john.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author yanzhengwei
 * @Description LoginController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;


    @GetMapping("/getAllDict")
    public JsonMessage getAllDict() {
        return JsonMessage.success( RedisUtil.get("dict"));
    }


    @GetMapping("/getDict/{type}")
    public JsonMessage getDict(@PathVariable("type") String type) {
        return JsonMessage.success( RedisUtil.hGet("dictMap",type));
    }

    @GetMapping("/reloadDict")
    public JsonMessage reloadDict() {
        Map<String, List<Dict>> dictMap = dictService.getAllDict();
        RedisUtil.set("dict", JSONUtil.parse(dictMap));
        dictMap.forEach((k,v)->RedisUtil.hPut("dictMap",k, JSONUtil.parse(v)));
        return JsonMessage.success("成功");
    }









}