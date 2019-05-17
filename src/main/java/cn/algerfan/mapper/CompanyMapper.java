package cn.algerfan.mapper;

import cn.algerfan.domain.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {
    int insert(Company record);

    int deleteByPrimaryKey(Integer companyId);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKey(Company record);

    List<Company> select(@Param(value="keyword") String keyword);

    Company selectByCompany(@Param(value="company") String company);

    Company selectByFirm(@Param(value="firm") String firm);

    List<Company> selectAllCompany();

    List<Company> findByEmployeeID(@Param(value="employeeId") String employeeId);

    int insertSelective(Company record);

    int updateByPrimaryKeySelective(Company record);

}