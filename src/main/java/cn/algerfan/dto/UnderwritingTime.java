package cn.algerfan.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 核保人
 * @author AlgerFan
 */
public class UnderwritingTime implements Serializable {

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
    private String time;

    /**
     * 查询核保人代办  没有的参数：代理人id、提交formId、上传资料、手机号、疾病史介绍、结论
     * @param underwritingId
     * @param nickname
     * @param employeeId
     * @param name
     * @param sex
     * @param birthday
     * @param time
     */
    public UnderwritingTime(Integer underwritingId, String nickname, String employeeId, String name,
                            String sex, String birthday, String time) {
        this.underwritingId = underwritingId;
        this.nickname = nickname;
        this.employeeId = employeeId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.time = time;
    }

    /**
     * 查询核保人历史  没有的参数：代理人id、提交formId、上传资料、手机号、疾病史介绍
     * @param underwritingId
     * @param nickname
     * @param employeeId
     * @param name
     * @param sex
     * @param birthday
     * @param conclusion
     * @param time
     */
    public UnderwritingTime(Integer underwritingId, String nickname, String employeeId, String name, String sex, String birthday,
                            String conclusion, String time) {
        this.underwritingId = underwritingId;
        this.nickname = nickname;
        this.employeeId = employeeId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.conclusion = conclusion;
        this.time = time;
    }

    /**
     * 查询代办核保人详情  没有的参数：代理人id、提交formId、上传资料、结论
     * @param underwritingId
     * @param nickname
     * @param employeeId
     * @param name
     * @param sex
     * @param birthday
     * @param phone
     * @param introduce
     * @param time
     */
    public UnderwritingTime(Integer underwritingId, String nickname, String employeeId, String name, String sex,
                            String birthday, String phone, String introduce, String time) {
        this.underwritingId = underwritingId;
        this.nickname = nickname;
        this.employeeId = employeeId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.introduce = introduce;
        this.time = time;
    }

    /**
     * 查询历史核保人详情  没有的参数：代理人id、提交formId、上传资料
     * @param underwritingId
     * @param nickname
     * @param employeeId
     * @param name
     * @param sex
     * @param birthday
     * @param phone
     * @param introduce
     * @param conclusion
     * @param time
     */
    public UnderwritingTime(Integer underwritingId, String nickname, String employeeId, String name, String sex,
                            String birthday, String phone, String introduce, String conclusion, String time) {
        this.underwritingId = underwritingId;
        this.nickname = nickname;
        this.employeeId = employeeId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.introduce = introduce;
        this.conclusion = conclusion;
        this.time = time;
    }

    public Integer getUnderwritingId() {
        return underwritingId;
    }

    public void setUnderwritingId(Integer underwritingId) {
        this.underwritingId = underwritingId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String conclusion) {
        this.introduce = conclusion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "UnderwritingTime{" +
                "underwritingId=" + underwritingId +
                ", agentId=" + agentId +
                ", formId='" + formId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", introduce='" + introduce + '\'' +
                ", data='" + data + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}