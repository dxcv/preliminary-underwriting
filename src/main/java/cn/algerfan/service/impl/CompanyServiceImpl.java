package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.CompanyMapper;
import cn.algerfan.service.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/18 11
 */
@Service
public class CompanyServiceImpl  extends BaseDao<Company> implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Result insert(Company company) {
        if(company.getCompany() == null || company.getCompany().equals("") ||
                company.getFirm() == null || company.getFirm().equals("") ||
                company.getJobNumber() == null || company.getJobNumber().equals("")) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        if(companyMapper.selectByCompany(company.getCompany()) !=null) {
            return new Result(-1,"添加失败，该公司已存在");
        }
        if(companyMapper.insert(company) == 0) {
            return new Result(ResultCodeEnum.UNSAVE);
        }
        return new Result(ResultCodeEnum.SAVE);
    }

    @Override
    public Result delete(Integer companyId) {
        if(companyId == null || companyId == 0) return new Result(ResultCodeEnum.UNDELETE);
        if(companyMapper.deleteByPrimaryKey(companyId) == 1) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        return new Result(ResultCodeEnum.DELETE);
    }

    @Override
    public Company toUpdate(Integer companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Override
    public Result update(Integer companyId, Company company) {
        if(companyId == null || companyId == 0 || company.getCompany() == null || company.getCompany().equals("") ||
                company.getFirm() == null || company.getFirm().equals("") ||
                company.getJobNumber() == null || company.getJobNumber().equals("")) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        company.setCompanyId(companyId);
        if(companyMapper.selectByCompany(company.getCompany()) !=null) {
            return new Result(-1,"修改失败，该公司名已存在");
        }
        if(companyMapper.updateByPrimaryKey(company) == 0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public PageInfo<Company> select(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Company> companyList = companyMapper.select(keyword);
        return new PageInfo<>(companyList);
    }

    @Override
    public Map<String, Object> selectAllCompany() {
        Map<String, Object> map = new HashMap<>();
        List<Company> companyList = companyMapper.selectAllCompany();
        map.put("status", 1);
        map.put("msg", "查询成功");
        map.put("companyList", companyList);
        return map;
    }

}
