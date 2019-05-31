package cn.algerfan.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  核保人DTO
 * </p>
 *
 * @author algerfan
 * @since 2019/5/28 21
 */
public class UnderwritingDTO implements Serializable {

    /**
     * 代理人公司
     */
    private String company;
    /**
     * 代理人昵称
     */
    private String nickname;
    /**
     * 代理人工号
     */
    private String employeeId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 疾病史介绍
     */
    private String introduce;
    /**
     * 结论
     */
    private String conclusion;
    /**
     * 提交时间
     */
    private Date submitTime;

    public UnderwritingDTO(String company, String nickname, String employeeId, String name,
                           String sex, String birthday, String introduce, String conclusion, Date submitTime) {
        this.company = company;
        this.nickname = nickname;
        this.employeeId = employeeId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.introduce = introduce;
        this.conclusion = conclusion;
        this.submitTime = submitTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "UnderwritingDTO{" +
                "company='" + company + '\'' +
                ", nickname='" + nickname + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", introduce='" + introduce + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }
}
