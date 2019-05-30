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
}
