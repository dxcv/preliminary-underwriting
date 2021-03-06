package cn.algerfan.enums;

/**
 * 枚举
 * @author AlgerFan
 */
public enum ResultCodeEnum {

    SAVEFAIL(0,"添加失败，信息不完整"),
    UPDATEFAIL(0,"修改失败，信息不完整"),
    SUCCESS(1,"成功"),//通过
    FAIL(0,"失败"), //失败
    UNKNOWNFAIL(0,"错误，请刷新后重试"),
    SAVE(1,"添加成功"),
    UNSAVE(0,"添加失败"),
    UPDATE(1,"修改成功"),
    UNUPDATE(0,"修改失败"),
    FIND(1,"查询成功"),
    UNFIND(0,"查询失败"),
    DELETE(1,"删除成功"),
    UNDELETE(0,"删除失败"),
    ;

    private int code;

    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
