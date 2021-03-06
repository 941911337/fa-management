package cn.john.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.john.dto.AssetExportVo;
import cn.john.dto.AssetPageVo;
import cn.john.dto.AssetVo;
import cn.john.dto.JsonMessage;
import cn.john.log.annotation.SysOperateLog;
import cn.john.log.annotation.SysOperateMethodLog;
import cn.john.service.ITAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @Author John Yan
 * @Description AssetController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
@RequestMapping("/asset")
@SysOperateLog(module = "资产管理")
public class AssetController {

    private final ITAssetService assetService;

    public AssetController(ITAssetService assetService) {
        this.assetService = assetService;
    }


    @PostMapping("/saveAsset")
    @SysOperateMethodLog(method = "保存资产")
    public JsonMessage saveAsset(@Valid @RequestBody AssetVo assetVo, BindingResult bindingResult) {
        assetService.saveAsset(assetVo);
        return JsonMessage.success("成功");
    }


    @PostMapping("/getPage")
    @SysOperateMethodLog(method = "分页获取资产")
    public JsonMessage getPage(@Valid @RequestBody AssetPageVo assetPageVo, BindingResult bindingResult) {
        return JsonMessage.success(assetService.getPage(assetPageVo));
    }

    @DeleteMapping("/del/{id}")
    @SysOperateMethodLog(method = "删除资产")
    public JsonMessage del(@PathVariable("id") Long id) {
        assetService.del(id);
        return JsonMessage.success("成功");
    }

    @GetMapping("/detail/{id}")
    @SysOperateMethodLog(method = "获取资产详情")
    public JsonMessage detail(@PathVariable("id") Long id) {
        return JsonMessage.success(assetService.detail(id));
    }

    @GetMapping("/copy/{id}")
    @SysOperateMethodLog(method = "复制资产")
    public JsonMessage copy(@PathVariable("id") Long id) {
        assetService.copy(id);
        return JsonMessage.success("成功");
    }

    @GetMapping("/export")
    @SysOperateMethodLog(method = "导出资产")
    public void export(@RequestParam String assetName, @RequestParam String assetCode,
                       @RequestParam Integer useStatus, HttpServletResponse response) throws IOException {
        List<AssetExportVo> list = assetService.getAllList(assetName,assetCode,useStatus);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("name", "资产名称");
        writer.addHeaderAlias("code", "资产编号");
        writer.addHeaderAlias("statusName", "使用状态");
        writer.addHeaderAlias("className", "类别名称");
        writer.addHeaderAlias("specifications", "规格型号");
        writer.addHeaderAlias("quantity", "数量");
        writer.addHeaderAlias("dept", "使用部门");
        writer.addHeaderAlias("employee", "使用人");
        writer.addHeaderAlias("area", "存放区域");
        writer.addHeaderAlias("buyerTime", "购入时间");
        writer.addHeaderAlias("createTime", "入库时间");
        writer.addHeaderAlias("remark", "备注");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename="+ DateUtil.today()+".xls");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }




}