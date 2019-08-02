package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.domain.Agent;
import cn.algerfan.mapper.CompanyMapper;
import cn.algerfan.mapper.UnderwritingMapper;
import cn.algerfan.service.AgentService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.FileUtil;
import cn.algerfan.util.openid.Aes;
import cn.algerfan.util.openid.Openid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 *  代理人服务实现类
 * </p>
 *
 * @author algerfan
 * @since 2019-04-13
 */
@Service
public class AgentServiceImpl extends BaseDao<Agent> implements AgentService {
    @Value("${filePath}")
    private String filePath;
    @Resource
    private AgentMapper agentMapper;
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private UnderwritingMapper underwritingMapper;
    @Autowired
    private JedisPool jedisPool;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> register(String employeeId, String company, String encryptedData, String iv, String code, HttpServletRequest request) {
        log.info("employeeId："+employeeId+"    company："+company+"   encryptedData: "+encryptedData+"  iv: "+iv+"  code: "+code);
        Map<String, Object> map = new HashMap<>(10);
        if (employeeId == null || employeeId.length() == 0 || company==null || "".equals(company)) {
            map.put("status", 0);
            map.put("msg", "参数不完整");
            return map;
        }
        if(employeeId.length()!=10) {
            map.put("status", 0);
            map.put("msg", "对不起，您所录入的工号非长城人寿北京分公司工号，无法进行注册");
            return map;
        }
        List<Company> byEmployeeID = companyMapper.findByEmployeeID(employeeId.substring(0, 4));
        if(byEmployeeID.size()==0) {
            map.put("status",0);
            map.put("msg","对不起，您所录入的工号非长城人寿北京分公司工号，无法进行注册");
            return map;
        }

        //登录凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = Aes.decrypt(encryptedData, String.valueOf(map1.get("session_key")), iv);
            if (null != result && result.length() > 0) {
                log.info("解密成功");
                String key = UUID.randomUUID().toString();
                Jedis jedis = jedisPool.getResource();
                try {
                    jedis.set(key, String.valueOf(map1.get("session_key")), "NX", "EX", 60*60*24*10);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedis.close();
                }
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<String, Object> userInfo = new HashMap<>(10);
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                log.info("userInfo: "+userInfo);
                Agent check = agentMapper.selectByOpenid(AesUtil.aesEncrypt(String.valueOf(map1.get("openid")), "lovewlgzs5201314"));
                if(check!=null) {
                    if(!check.getEmployeeId().equals(employeeId) || !check.getCompany().equals(company)) {
                        map.put("status", 0);
                        map.put("msg","登录失败，公司或工号不正确");
                        return map;
                    } else {
                        map.put("status", 1);
                        map.put("msg","您已注册，直接登录");
                        map.put("key",key);
                        return map;
                    }
                } else {
                    Agent agent1 = agentMapper.checkEmployeeID(employeeId);
                    if(agent1!=null) {
                        map.put("status",0);
                        map.put("msg","对不起，您所录入的工号已经使用，无法进行注册");
                        return map;
                    }
                    String openId = AesUtil.aesEncrypt(String.valueOf(userInfoJSON.get("openId")), "lovewlgzs5201314");
                    Agent agent = new Agent(String.valueOf(userInfoJSON.get("nickName")), String.valueOf(userInfoJSON.get("avatarUrl")),
                            openId, employeeId, company, byEmployeeID.get(0).getFirm());
                    agentMapper.insert(agent);
                    map.put("status", 1);
                    map.put("msg", "注册成功");
                    map.put("key",key);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }

    @Override
    public Map<String, Object> checkKey(String key, String encryptedData, String iv) {
        Map<String, Object> map = new HashMap<>(10);
        Jedis jedis = jedisPool.getResource();
        String sessionKey = "";
        try {
            if(jedis.get(key) == null) {
                map.put("status", 0);
                map.put("msg", "用户未登录");
                return map;
            }
            sessionKey = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = Aes.decrypt(encryptedData, sessionKey, iv);
            if (null != result && result.length() > 0) {
                log.info("解密成功");
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<String, Object> userInfo = new HashMap<>(10);
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                log.info("userInfo: "+userInfo);
                Agent check = agentMapper.selectByOpenid(AesUtil.aesEncrypt((String) userInfoJSON.get("openId"), "lovewlgzs5201314"));
                if(check==null) {
                    map.put("status", 0);
                    map.put("msg","登录失败");
                    return map;
                }
                map.put("status", 1);
                map.put("msg","用户已登录");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }

    @Override
    public Agent selectById(Integer agentId) {
        return agentMapper.selectByPrimaryKey(agentId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Integer agentId) {
        if(agentId == null || agentId == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        List<Underwriting> underwritingList = underwritingMapper.selectByAgentId(agent.getAgentId());
        for (Underwriting underwriting : underwritingList) {
            if(underwriting.getData() != null) {
                String newDir = filePath + underwriting.getData().substring(0, 23) + underwriting.getName();
                new FileUtil().deleteDir(new File(newDir));
            }
        }
        if(agentMapper.deleteByPrimaryKey(agentId) == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        underwritingMapper.deleteByAgentId(agentId);
        return new Result(ResultCodeEnum.DELETE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result update(Integer agentId, Agent agent) {
        if(agentId == null || agentId == 0 || agent.getEmployeeId() ==null || "".equals(agent.getEmployeeId()) ||
                agent.getFirm() == null || "".equals(agent.getFirm())) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        Company company = companyMapper.selectByFirm(agent.getFirm());
        if(company == null) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        agent.setCompany(company.getCompany());
        agent.setAgentId(agentId);
        if(agentMapper.updateByPrimaryKeySelective(agent) == 0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public PageInfo<Agent> select(String nickname, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(agentMapper.select(nickname));
    }

    @Override
    public void statistical(String keyword, HttpServletResponse response) throws IOException {
        //2017-05-06 至 2018-05-24
        String first = keyword.substring(0,10);
        String last = keyword.substring(13);
        List<Agent> agentList = agentMapper.selectAll();
        List<Agent> agentArrayList = new ArrayList<>(agentList);
        agentArrayList.sort((Agent a1, Agent a2) -> a1.getCompany().compareTo(a2.getCompany()));
        log.info("查询成功："+ agentArrayList);
        if(agentList.size()!=0) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultRowHeightInPoints(20);
            HSSFPrintSetup ps = sheet.getPrintSetup();
            ps.setLandscape(false); // 打印方向，true：横向，false：纵向
            ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
            sheet.setHorizontallyCenter(true);//设置打印页面为水平居中

            //设置要导出的文件的名字
            String fileName = "代理人信息统计.xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //新增数据行，并且设置单元格数据
            int rowNum = 1;
            String[] headers = {"代理人公司","代理人公司简称", "代理人昵称", "代理人工号"};
            //headers表示excel表中第一行的表头
            HSSFRow row = sheet.createRow(0);
            //设置行高
            row.setHeightInPoints(30);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            sheet.setColumnWidth(0, 19 * 256);
            sheet.setColumnWidth(1, 19 * 256);
            sheet.setColumnWidth(2, 19 * 256);
            sheet.setColumnWidth(3, 19 * 256);
            //其他表样式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            HSSFFont font = workbook.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 11);//设置字体大小
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置字体水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            style.setFont(font);
            //表头样式
            HSSFCellStyle style2 = workbook.createCellStyle();
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            HSSFFont font2 = workbook.createFont();//其他字体样式
            font2.setFontName("宋体");
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            font2.setFontHeightInPoints((short) 11);//设置字体大小
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置字体水平居中
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            style2.setFont(font2);

            //在excel表中添加表头
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellStyle(style2);
                cell.setCellValue(text);
            }
            //在表中存放查询到的数据放入对应的列
            HSSFCell cell;
            for (Agent agent : agentArrayList) {
                HSSFRow row1 = sheet.createRow(rowNum);
                //设置行高
                row1.setHeightInPoints(25);
                cell = row1.createCell(0);
                cell.setCellValue(agent.getCompany());
                cell.setCellStyle(style);
                cell = row1.createCell(1);
                cell.setCellValue(agent.getFirm());
                cell.setCellStyle(style);
                cell = row1.createCell(2);
                cell.setCellValue(agent.getNickname());
                cell.setCellStyle(style);
                cell = row1.createCell(3);
                cell.setCellValue(agent.getEmployeeId());
                cell.setCellStyle(style);
                rowNum++;
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }
    }

}
