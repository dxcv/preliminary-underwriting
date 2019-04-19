package cn.algerfan.service;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  核保人服务类
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 08
 */
public interface UnderwritingService {

    Result insert(Underwriting underwriting, MultipartFile[] myFileNames);

    Result findUnderwriting(String openid);

    PageInfo<Underwriting> select(String keyword, int pageNum, int pageSize);
}
