package com.dango.system.service;

import com.dango.system.model.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author dango
* @description 针对表【dictionary(数据字典)】的数据库操作Service
* @createDate 2024-01-18 14:35:34
*/
public interface DictionaryService extends IService<Dictionary> {
    /**
     * 查询所有数据字典内容
     * @return
     */
    List<Dictionary> queryAll();

    /**
     * 根据code查询数据字典
     * @param code -- String 数据字典Code
     * @return
     */
    Dictionary getByCode(String code);
}
