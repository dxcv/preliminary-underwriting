package cn.algerfan.domain;

import java.io.Serializable;

public class Underwriting implements Serializable {

    private Integer underwritingId;
    /**
     * 代理人id
     */
    private Integer agentId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    @Override
    public String toString() {
        return "Underwriting{" +
                "underwritingId=" + underwritingId +
                ", agentId=" + agentId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", introduce='" + introduce + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}