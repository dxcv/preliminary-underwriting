package cn.algerfan.domain;

public class Company {

    private Integer companyId;
    /**
     * 公司名
     */
    private String company;
    /**
     * 公司简称
     */
    private String firm;
    /**
    工号规则
     */
    private String jobNumber;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm == null ? null : firm.trim();
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", company='" + company + '\'' +
                ", firm='" + firm + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                '}';
    }
}