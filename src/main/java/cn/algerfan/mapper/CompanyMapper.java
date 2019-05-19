package cn.algerfan.mapper;

import cn.algerfan.domain.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {
    /**
     * 添加
     * @param record
     * @return
     */
    int insert(Company record);

    /**
     * 根据id删除
     * @param companyId
     * @return
     */
    int deleteByPrimaryKey(Integer companyId);

    /**
     * 根据id查询
     * @param companyId
     * @return
     */
    Company selectByPrimaryKey(Integer companyId);

    /**
     * 全部更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Company record);

    /**
     * 根据公司模糊查询
     * @param keyword
     * @return
     */
    List<Company> select(@Param(value="keyword") String keyword);

    /**
     * 根据公司名精确查询
     * @param company
     * @return
     */
    Company selectByCompany(@Param(value="company") String company);

    /**
     * 根据公司简称精确查询
     * @param firm
     * @return
     */
    Company selectByFirm(@Param(value="firm") String firm);

    /**
     * 查询所有公司
     * @return
     */
    List<Company> selectAllCompany();

    /**
     * 通过工号查询
     * @param employeeId
     * @return
     */
    List<Company> findByEmployeeID(@Param(value="employeeId") String employeeId);

    /**
     * 选择添加
     * @param record
     * @return
     */
    int insertSelective(Company record);

    /**
     * 选择更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Company record);

}