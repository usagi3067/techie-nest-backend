package com.dango.content.controller;

import com.dango.content.mapper.LecturerMapper;
import com.dango.content.model.dto.LecturerBalanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dango
 * @description
 * @date 2024-04-05
 */
@Api(tags = "讲师端相关接口")
@Controller
@RestController
@ResponseBody
public class lecturerController {

    @Autowired
    private LecturerMapper lecturerMapper;

    @ApiOperation("余额更新")
    @PostMapping("/lecturer")
    public void updateBalance(@RequestBody LecturerBalanceDto lecturerBalanceDto) {
        lecturerMapper.incrementBalance(lecturerBalanceDto.getLecturerId(), lecturerBalanceDto.getPrice());
    }
}
