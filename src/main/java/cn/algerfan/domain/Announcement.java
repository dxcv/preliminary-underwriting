package cn.algerfan.domain;

import java.util.Date;

public class Announcement {

    private Integer announcementId;
    /**
     * 公告类型
     */
    private String type;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date date;

    public Announcement() {}

    public Announcement(String type, String content, Date date) {
        this.type = type;
        this.content = content;
        this.date = date;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "announcementId=" + announcementId +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}