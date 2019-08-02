package cn.algerfan.service;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.Zip;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/8/2 09
 */
public interface ZipService {

    /**
     * 查询所有月份下载地址
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Zip> selectMonth(int pageNum, int pageSize);

    /**
     * 打包文件
     * @param zipId
     * @param url
     * @return
     */
    Result getZip(Integer zipId, String url);

    /**
     * 删除文件
     * @param zipId
     * @return
     */
    Result deleteZip(Integer zipId);
}
