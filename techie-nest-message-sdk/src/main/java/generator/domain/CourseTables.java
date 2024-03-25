package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName course_tables
 */
@TableName(value ="course_tables")
@Data
public class CourseTables implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 选课记录id
     */
    @TableField(value = "choose_course_id")
    private Long chooseCourseId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 机构id
     */
    @TableField(value = "company_id")
    private Long companyId;

    /**
     * 课程名称
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 课程类型
     */
    @TableField(value = "course_type")
    private String courseType;

    /**
     * 添加时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 开始服务时间
     */
    @TableField(value = "valid_time_start")
    private LocalDateTime validTimeStart;

    /**
     * 到期时间
     */
    @TableField(value = "valid_time_end")
    private LocalDateTime validTimeEnd;

    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    private LocalDateTime updateDate;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}