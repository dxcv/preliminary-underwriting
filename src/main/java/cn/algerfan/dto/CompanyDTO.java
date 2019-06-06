package cn.algerfan.dto;

/**
 * 公司
 * @author AlgerFan
 */
public class CompanyDTO {

    /**
     * 公司名
     */
    private String company;
    /**
     * 公司简称
     */
    private String firm;

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

    public CompanyDTO(String company, String firm) {
        this.company = company;
        this.firm = firm;
    }

    @Override
    public String toString() {
        return "Company{" +
                ", company='" + company + '\'' +
                ", firm='" + firm + '\'' +
                '}';
    }
}