package cn.john.controller;

import cn.john.dto.ChangePasswordVo;
import cn.john.dto.JsonMessage;
import cn.john.dto.UserPageVo;
import cn.john.dto.UserVo;
import cn.john.log.annotation.SysOperateLog;
import cn.john.log.annotation.SysOperateMethodLog;
import cn.john.model.TAccount;
import cn.john.service.ITAccountService;
import cn.john.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author John Yan
 * @Description AccountController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
@RequestMapping("/account")
@SysOperateLog(module = "账号管理")
public class AccountController {

    private final ITAccountService accountService;

    public AccountController(ITAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/getCurrentLogin")
    @SysOperateMethodLog(method = "获取当前账号信息")
    public JsonMessage getCurrentLogin() {
        TAccount account = SysUtil.getUser();
        account.setPassword("");
        account.setAdmin("admin".equals(account.getAccount()));
        return JsonMessage.success(account);
    }

    @PostMapping("/changePassword")
    @SysOperateMethodLog(method = "修改账号密码")
    public JsonMessage changePassword(@Valid @RequestBody ChangePasswordVo changePasswordVo, BindingResult bindingResult) {
        accountService.changePassword(changePasswordVo);
        SysUtil.logout();
        return JsonMessage.success("修改成功,请重新登陆");
    }


    @PostMapping("/saveAccount")
    @SysOperateMethodLog(method = "新增账号")
    public JsonMessage saveAccount(@Valid @RequestBody UserVo userVo, BindingResult bindingResult) {
        accountService.saveUser(userVo);
        return JsonMessage.success("成功");
    }


    @PostMapping("/getPage")
    @SysOperateMethodLog(method = "分页获取账号列表")
    public JsonMessage getPage(@Valid @RequestBody UserPageVo userPageVo, BindingResult bindingResult) {
        return JsonMessage.success(accountService.getPage(userPageVo));
    }

    @DeleteMapping("/del/{id}")
    @SysOperateMethodLog(method = "删除账号")
    public JsonMessage del(@PathVariable("id") Long id) {
        accountService.del(id);
        return JsonMessage.success("成功");
    }

    @GetMapping("/getDetail/{id}")
    @SysOperateMethodLog(method = "获取账号详情")
    public JsonMessage getDetail(@PathVariable("id") Long id) {
        return JsonMessage.success(accountService.getDetail(id));
    }

}