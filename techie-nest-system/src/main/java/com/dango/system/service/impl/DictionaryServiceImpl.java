package com.dango.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.system.model.entity.Dictionary;
import com.dango.system.service.DictionaryService;
import com.dango.system.mapper.DictionaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.List;

/**
* @author dango
* @description 针对表【dictionary(数据字典)】的数据库操作Service实现
* @createDate 2024-01-18 14:35:34
*/
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary>
    implements DictionaryService {
    @Override
    public List<Dictionary> queryAll() {
        return this.list();
    }

    @Override
    public Dictionary getByCode(String code) {
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dictionary::getCode, code);

        return this.getOne(queryWrapper);
    }
}




