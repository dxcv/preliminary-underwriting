package cn.algerfan.util.openid;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/21 09
 */
public class Openid {

    public static Map<String,Object> session_key(String code) {
        Map<String,Object> map = new HashMap<>();
        //小程序唯一标识   (在微信小程序管理后台获取)
        String APP_ID = "wx1633b8cd0a523508";
        //小程序的 app secret (在微信小程序管理后台获取)
        String APP_SECRET = "0765456e314c6cb199ce97b7bb949a43";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        map.put("session_key",session_key);
        map.put("openid",openid);
        System.out.println("-----"+map);
        return map;
    }
}
