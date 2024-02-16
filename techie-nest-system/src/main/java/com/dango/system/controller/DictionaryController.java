package com.dango.system.controller;

import com.dango.system.model.entity.Dictionary;
import com.dango.system.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-01-18
 */
@RestController
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService;

    @GetMapping("/dictionary/all")
    public List<Dictionary> queryAll() {
        return dictionaryService.queryAll();
    }

    @GetMapping("/dictionary/code/{code}")
    public Dictionary getByCode(@PathVariable String code) {
        return dictionaryService.getByCode(code);
    }
}
