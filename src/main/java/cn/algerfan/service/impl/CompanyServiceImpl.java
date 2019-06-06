package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import cn.algerfan.dto.CompanyDTO;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.CompanyMapper;
import cn.algerfan.service.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        if(company.getCompany() == null || "".equals(company.getCompany()) ||
                company.getFirm() == null || "".equals(company.getFirm()) ||
                company.getJobNumber() == null || "".equals(company.getJobNumber())) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        if(company.getJobNumber().length()!=4) {
            return new Result(-1,"添加失败，工号不符合规则");
        }
        if(companyMapper.selectByCompany(company.getCompany()) !=null) {
            return new Result(-1,"添加失败，该公司名已存在");
        }
        if(companyMapper.selectByFirm(company.getFirm()) !=null) {
            return new Result(-1,"添加失败，该公司简称已存在");
        }
        if(companyMapper.insert(company) == 0) {
            return new Result(ResultCodeEnum.UNSAVE);
        }
        return new Result(ResultCodeEnum.SAVE);
    }

    @Override
    public Result delete(Integer companyId) {
        if(companyId == null || companyId == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        if(companyMapper.deleteByPrimaryKey(companyId) == 0) {
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
        if(companyId == null || companyId == 0 || company.getCompany() == null || "".equals(company.getCompany()) ||
                company.getFirm() == null || "".equals(company.getFirm()) ||
                company.getJobNumber() == null || "".equals(company.getJobNumber())) {
            return new Result(ResultCodeEnum.UPDATEFAIL);
        }
        if(company.getJobNumber().length()!=4) {
            return new Result(-1,"添加失败，工号不符合规则");
        }
        company.setCompanyId(companyId);
        if(!companyMapper.selectByPrimaryKey(companyId).getCompany().equals(company.getCompany())) {
            if (companyMapper.selectByCompany(company.getCompany()) != null) {
                return new Result(-1, "修改失败，该公司名已存在");
            }
        }
        if(!companyMapper.selectByPrimaryKey(companyId).getFirm().equals(company.getFirm())) {
            if (companyMapper.selectByFirm(company.getFirm()) != null) {
                return new Result(-1, "修改失败，该公司简称已存在");
            }
        }
        if(companyMapper.updateByPrimaryKey(company) == 0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public PageInfo<Company> select(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(companyMapper.select(keyword));
    }

    @Override
    public Map<String, Object> selectAllCompany() {
        Map<String, Object> map = new HashMap<>(10);
        List<Company> companyList = companyMapper.selectAllCompany();
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        for (Company company : companyList) {
            companyDTOList.add(new CompanyDTO(company.getCompany(), company.getFirm()));
        }
        map.put("status", 1);
        map.put("msg", "查询成功");
        map.put("companyList", companyDTOList);
        return map;
    }

}
