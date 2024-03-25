package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.CourseTables;
import generator.service.CourseTablesService;
import generator.mapper.CourseTablesMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【course_tables】的数据库操作Service实现
* @createDate 2024-03-23 21:28:41
*/
@Service
public class CourseTablesServiceImpl extends ServiceImpl<CourseTablesMapper, CourseTables>
    implements CourseTablesService{

}




