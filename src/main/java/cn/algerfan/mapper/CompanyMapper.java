package cn.algerfan.mapper;

import cn.algerfan.domain.Company;

import java.util.List;

public interface CompanyMapper {
    int insert(Company record);

    int deleteByPrimaryKey(Integer companyId);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKey(Company record);

    List<Company> select(String keyword);

    Company selectByCompany(String company);

    List<Company> selectAllCompany();

    List<Company> findByEmployeeID(String employeeId);

    int insertSelective(Company record);

    int updateByPrimaryKeySelective(Company record);

}