package cn.algerfan.service;

import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/18 11
 */
public interface CompanyService {

    Result insert(Company company);

    Result delete(Integer companyId);

    Company toUpdate(Integer companyId);

    Result update(Integer companyId, Company company);

    PageInfo<Company> select(String keyword, int pageNum, int pageSize);

    Map<String, Object> selectAllCompany();
}
