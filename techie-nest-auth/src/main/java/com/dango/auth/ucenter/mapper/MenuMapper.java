package com.dango.auth.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dango.auth.ucenter.model.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT	* FROM menu WHERE id IN (SELECT menu_id FROM permission WHERE role_id IN ( SELECT role_id FROM user_role WHERE user_id = #{userId} ))")
    List<Menu> selectPermissionByUserId(@Param("userId") String userId);
}
