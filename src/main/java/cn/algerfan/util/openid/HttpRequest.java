package cn.algerfan.util.openid;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  发送GET和POST请求
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 11
 */

public class HttpRequest {

    /*public static void main(String[] args) {
        //发送 GET 请求
        String s=HttpRequest.sendGet("http://v.qq.com/x/cover/kvehb7okfxqstmc.html?vid=e01957zem6o", "");
        System.out.println(s);

        //发送 POST 请求
        String sr=HttpRequest.sendPost("http://www.toutiao.com/stream/widget/local_weather/data/?city=%E4%B8%8A%E6%B5%B7", "");
        JSONObject json = JSONObject.fromObject(sr);
        System.out.println(json.get("data"));
    }*/

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param json 请求参数，json形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(JSONObject json) {
        //小程序唯一标识   (在微信小程序管理后台获取)
        String APP_ID = "wx4fe7f4518ff07ed8";
        //小程序的 app secret (在微信小程序管理后台获取)
        String APP_SECRET = "f4028cbcb8bb75aeffc3091578f157e7";
        //授权（必填）
        String grant_type = "client_credential";
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "grant_type=" + grant_type + "&appid=" + APP_ID + "&secret=" + APP_SECRET;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        //解析相应内容（转换成json对象）
        JSONObject json1 = JSONObject.fromObject(sr);
        //获取小程序全局唯一后台接口调用凭据access_token
        String access_token = json1.get("access_token").toString();

        HttpClient client = HttpClientBuilder.create().build();
        String URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send" +
                "?access_token="+access_token;
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result;
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);
            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                strber.append(line).append("\n");
            inStream.close();

            result = strber.toString();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return "请求服务器成功，做相应处理-->"+result;
            } else {
                return "请求服务端失败-->"+result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
