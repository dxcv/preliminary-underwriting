package cn.algerfan.domain;

import java.io.Serializable;

public class Agent implements Serializable {

    private Integer agentId;
    private String employeeId;
    private String company;
    private String openid;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentId=" + agentId +
                ", employeeId='" + employeeId + '\'' +
                ", company='" + company + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}