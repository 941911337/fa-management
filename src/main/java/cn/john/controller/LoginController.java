package cn.john.controller;

import cn.john.common.Constants;
import cn.john.dto.JsonMessage;
import cn.john.dto.LoginVo;
import cn.john.model.Account;
import cn.john.service.AccountService;
import cn.john.utils.RedisUtil;
import cn.john.utils.SysUtil;
import cn.john.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author yanzhengwei
 * @Description LoginController
 * @Date 2021/7/14
 **/
@Slf4j
@RestController
public class LoginController {


    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public JsonMessage login(@Valid @RequestBody LoginVo loginVo, BindingResult bindingResult) {
        JsonMessage message = SysUtil.paramValid(bindingResult);
        if (message != null) {
            return message;
        }
        log.info("loginVo:{}",loginVo);
        Account account = accountService.getAccount(loginVo);
        Map<String, Object> chaim = new HashMap<>(4);
        chaim.put("username", loginVo.getUsername());
        chaim.put("id", account.getId());
        String clientId = UUID.randomUUID().toString();
        chaim.put("clientId", clientId);
        String jwtToken = TokenUtil.createJwt(loginVo.getUsername(), chaim,Constants.TOKEN_EXPIRE_TIME);
        RedisUtil.hPut(Constants.LOGIN_CACHE_NAMESPACE +account.getId(),"account",account);
        RedisUtil.hPut(Constants.LOGIN_CACHE_NAMESPACE +account.getId(),"clientId",clientId);
        return JsonMessage.success(jwtToken);
    }

    @GetMapping("/logout")
    public JsonMessage logout() {
        SysUtil.logout();
        return JsonMessage.success("已退出");
    }


}