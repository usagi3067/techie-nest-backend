package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.media.model.entity.MediaFiles;
import generator.service.MediaFilesService;
import com.dango.media.mapper.MediaFilesMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【media_files(媒资信息)】的数据库操作Service实现
* @createDate 2024-04-04 16:24:01
*/
@Service
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles>
    implements MediaFilesService{

}




