package cn.algerfan.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 核保人
 * @author AlgerFan
 */
public class Underwriting implements Serializable {

    private Integer underwritingId;
    /**
     * 代理人id
     */
    private Integer agentId;
    /**
     * 提交formId
     */
    private String formId;
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
     * 手机号
     */
    private String phone;
    /**
     * 疾病史介绍
     */
    private String introduce;
    /**
     * 上传资料
     */
    private String data;
    /**
     * 结论
     */
    private String conclusion;
    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Underwriting() {}

    public Underwriting(Integer agentId, String formId, String name, String sex, String birthday, String phone, String introduce,
                        String data, String conclusion, Date submitTime, Date updateTime) {
        this.agentId = agentId;
        this.formId = formId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.introduce = introduce;
        this.data = data;
        this.conclusion = conclusion;
        this.submitTime = submitTime;
        this.updateTime = updateTime;
    }

    public Integer getUnderwritingId() {
        return underwritingId;
    }

    public void setUnderwritingId(Integer underwritingId) {
        this.underwritingId = underwritingId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Underwriting{" +
                "underwritingId=" + underwritingId +
                ", agentId=" + agentId +
                ", formId='" + formId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", introduce='" + introduce + '\'' +
                ", data='" + data + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", submitTime=" + submitTime +
                ", updateTime=" + updateTime +
                '}';
    }

}