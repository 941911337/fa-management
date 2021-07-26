package cn.john.controller;

import cn.john.dto.ChangePasswordVo;
import cn.john.dto.JsonMessage;
import cn.john.dto.UserPageVo;
import cn.john.dto.UserVo;
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
public class AccountController {

    private final ITAccountService accountService;

    public AccountController(ITAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/getCurrentLogin")
    public JsonMessage getCurrentLogin() {
        TAccount account = SysUtil.getUser();
        account.setPassword("");
        account.setAdmin("admin".equals(account.getAccount()));
        return JsonMessage.success(account);
    }

    @PostMapping("/changePassword")
    public JsonMessage changePassword(@Valid @RequestBody ChangePasswordVo changePasswordVo, BindingResult bindingResult) {
        
        accountService.changePassword(changePasswordVo);
        SysUtil.logout();
        return JsonMessage.success("修改成功,请重新登陆");
    }


    @PostMapping("/saveAccount")
    public JsonMessage saveAccount(@Valid @RequestBody UserVo userVo, BindingResult bindingResult) {
        
        accountService.saveUser(userVo);
        return JsonMessage.success("成功");
    }


    @PostMapping("/getPage")
    public JsonMessage getPage(@Valid @RequestBody UserPageVo userPageVo, BindingResult bindingResult) {
        
        return JsonMessage.success(accountService.getPage(userPageVo));
    }

    @DeleteMapping("/del/{id}")
    public JsonMessage del(@PathVariable("id") Long id) {
        accountService.del(id);
        return JsonMessage.success("成功");
    }

    @GetMapping("/getDetail/{id}")
    public JsonMessage getDetail(@PathVariable("id") Long id) {
        return JsonMessage.success(accountService.getDetail(id));
    }

}