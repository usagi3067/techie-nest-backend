package com.dango.auth.ucenter.service;

import com.dango.auth.ucenter.model.dto.AuthParamsDto;
import com.dango.auth.ucenter.model.dto.UserExt;

/**
 * @author dango
 * @description
 * @date 2024-03-22
 */
public interface AuthService {

    /**
     * 认证方法
     * @param authParamsDto 认证参数
     * @return 认证用户信息
     */
    UserExt execute(AuthParamsDto authParamsDto);
}
