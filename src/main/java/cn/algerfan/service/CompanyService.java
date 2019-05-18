package cn.algerfan.service;

import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 *  公司服务类
 * </p>
 *
 * @author algerfan
 * @since 2019/4/18 11
 */
public interface CompanyService {

    /**
     * 后台添加
     * @param company
     * @return
     */
    Result insert(Company company);

    /**
     * 后台删除
     * @param companyId
     * @return
     */
    Result delete(Integer companyId);

    /**
     * 后台根据id查询
     * @param companyId
     * @return
     */
    Company toUpdate(Integer companyId);

    /**
     * 后台更新
     * @param companyId
     * @param company
     * @return
     */
    Result update(Integer companyId, Company company);

    /**
     * 后台根据公司名模糊查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Company> select(String keyword, int pageNum, int pageSize);

    /**
     * 小程序查询所有公司
     * @return
     */
    Map<String, Object> selectAllCompany();
}
