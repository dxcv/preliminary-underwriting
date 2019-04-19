package cn.algerfan.util.openid;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  获取用户信息
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 16
 */
public class Openid {
    private Logger log;

    public Map<String, Object> register(String encryptedData, String iv, String code) {
        Map<String, Object> map = new HashMap<>();
        //登录凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }

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
        String sr = new HttpRequest().sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        log.info("openid: "+openid);

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = new Aes().decrypt(encryptedData, session_key, iv);
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                log.info("解密成功");

                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                log.info("userInfo: "+userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }
}
