package cn.algerfan.domain;

import java.io.Serializable;

/**
 * 代理人
 * @author AlgerFan
 */
public class Agent implements Serializable {

    private Integer agentId;
    /**
     * 代理人昵称
     */
    private String nickname;
    /**
     * 代理人头像
     */
    private String avatar;
    /**
     * 代理人openid
     */
    private String openid;
    /**
     * 代理人工号
     */
    private String employeeId;
    /**
     * 代理人公司
     */
    private String company;
    /**
     * 代理人公司简称
     */
    private String firm;

    public Agent() {}

    public Agent(String nickname, String avatar, String openid, String employeeId, String company, String firm) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.openid = openid;
        this.employeeId = employeeId;
        this.company = company;
        this.firm = firm;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentId=" + agentId +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", openid='" + openid + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", company='" + company + '\'' +
                ", firm='" + firm + '\'' +
                '}';
    }
}