package cn.algerfan.domain;

/**
 *  zip文件
 * @author algerfan
 */
public class Zip {
    private Integer zipId;

    /**
     * 月份
     */
    private String month;

    /**
     * 下载链接
     */
    private String download;

    /**
     * 是否压缩
     */
    private String compress;

    public Zip(){}

    public Integer getZipId() {
        return zipId;
    }

    public void setZipId(Integer zipId) {
        this.zipId = zipId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download == null ? null : download.trim();
    }

    public String getCompress() {
        return compress;
    }

    public void setCompress(String compress) {
        this.compress = compress == null ? null : compress.trim();
    }

}