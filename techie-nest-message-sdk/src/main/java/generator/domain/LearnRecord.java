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
 * @TableName learn_record
 */
@TableName(value ="learn_record")
@Data
public class LearnRecord implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 课程名称
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 最近学习时间
     */
    @TableField(value = "learn_date")
    private LocalDateTime learnDate;

    /**
     * 学习时长
     */
    @TableField(value = "learn_length")
    private Long learnLength;

    /**
     * 章节id
     */
    @TableField(value = "teach_plan_id")
    private Long teachPlanId;

    /**
     * 章节名称
     */
    @TableField(value = "teach_plan_name")
    private String teachPlanName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}