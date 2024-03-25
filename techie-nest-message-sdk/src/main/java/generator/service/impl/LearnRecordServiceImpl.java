package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.LearnRecord;
import generator.service.LearnRecordService;
import generator.mapper.LearnRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【learn_record】的数据库操作Service实现
* @createDate 2024-03-23 21:28:41
*/
@Service
public class LearnRecordServiceImpl extends ServiceImpl<LearnRecordMapper, LearnRecord>
    implements LearnRecordService{

}




