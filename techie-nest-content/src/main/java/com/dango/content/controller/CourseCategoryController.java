package com.dango.content.controller;

import com.dango.content.model.dto.CourseCategoryTreeResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-03-03
 */
@Slf4j
@RestController
@RequestMapping("/course-category")
public class CourseCategoryController {
    @GetMapping("/tree-nodes")
    public List<CourseCategoryTreeResp> queryTreeNodes() {
        return null;
    }
}
