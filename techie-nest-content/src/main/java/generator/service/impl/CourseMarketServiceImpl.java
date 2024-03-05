package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.model.dto.CourseMarket;
import generator.service.CourseMarketService;
import com.dango.content.mapper.CourseMarketMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【course_market(课程营销信息)】的数据库操作Service实现
* @createDate 2024-03-04 23:44:24
*/
@Service
public class CourseMarketServiceImpl extends ServiceImpl<CourseMarketMapper, CourseMarket>
    implements CourseMarketService{

}




