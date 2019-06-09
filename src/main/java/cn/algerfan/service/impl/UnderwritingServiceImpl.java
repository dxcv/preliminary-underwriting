package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.dto.UnderwritingDTO;
import cn.algerfan.dto.UnderwritingTime;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.mapper.UnderwritingMapper;
import cn.algerfan.service.UnderwritingService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.CheckUtil;
import cn.algerfan.util.openid.HttpRequest;
import cn.algerfan.util.openid.Openid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 核保人实现类
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 08
 */
@Service
public class UnderwritingServiceImpl extends BaseDao<Underwriting> implements UnderwritingService {
    @Value("${filePath}")
    private String filePath;

    @Resource
    private UnderwritingMapper underwritingMapper;
    @Resource
    private AgentMapper agentMapper;

    @Override
    public Map<String,Object> insert(String formId, Underwriting underwriting, String encryptedData, String iv, String code) {
        log.info("encryptedData: "+encryptedData+"  iv: "+iv+"  code: "+code);
        log.info("underwriting: "+underwriting);
        Map<String,Object> map = new HashMap<>(10);
        //formId ==null || formId.equals("") ||
        if (underwriting.getName() == null || "".equals(underwriting.getName()) ||
                underwriting.getSex() == null || "".equals(underwriting.getSex()) ||
                underwriting.getBirthday() == null ||
                underwriting.getIntroduce() == null || "".equals(underwriting.getIntroduce())) {
            map.put("status",0);
            map.put("msg","添加失败，信息不全！");
            return map;
        }
        //用户凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);

        Agent check = null;
        try {
            check = agentMapper.selectByOpenid(AesUtil.aesEncrypt(String.valueOf(map1.get("openid")), "lovewlgzs5201314"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(check==null) {
            map.put("status", 0);
            map.put("msg","添加失败，该代理人不存在");
            log.info("添加失败，该代理人不存在");
            return map;
        }
        Underwriting underwriting1 = underwritingMapper.selectByAgentIdAndPhone(check.getAgentId(),underwriting.getPhone());
        if(underwriting1 != null) {
            underwriting.setUpdateTime(new Date());
            underwriting.setAgentId(check.getAgentId());
            underwriting.setUnderwritingId(underwriting1.getUnderwritingId());
            underwritingMapper.updateByPrimaryKeySelective(underwriting);
            map.put("status", 1);
            map.put("msg","该核保人已存在，已更新资料");
            log.info("该核保人已存在，已更新资料");
            return map;
        }
        underwriting.setSubmitTime(new Date());
        underwriting.setAgentId(check.getAgentId());
        underwritingMapper.insert(underwriting);
        map.put("status",1);
        map.put("msg","添加成功");
        log.info("添加成功");
        return map;
    }

    @Override
    public Map<String, Object> upload(String phone, MultipartFile[] multipartFiles, String encryptedData, String iv, String code) {
        Map<String,Object> map = new HashMap<>();
        //用户凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || "".equals(encryptedData) || "".equals(iv)) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);
        Agent check = null;
        try {
            check = agentMapper.selectByOpenid(AesUtil.aesEncrypt(String.valueOf(map1.get("openid")), "lovewlgzs5201314"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(check==null) {
            map.put("status", 0);
            map.put("msg","添加失败，该代理人不存在");
            log.info("添加失败，该代理人不存在");
            return map;
        }
        log.info(check.getAgentId()+"------------"+phone);
        Underwriting underwriting = underwritingMapper.selectByAgentIdAndPhone(check.getAgentId(),phone);
        log.info(underwriting);
        if(underwriting == null) {
            map.put("status", 0);
            map.put("msg","添加失败，该核保人不存在");
            log.info("添加失败，该核保人不存在");
            return map;
        }
        StringBuilder data = new StringBuilder();
        if (multipartFiles.length != 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                String fileName = multipartFile.getOriginalFilename();
                CheckUtil checkUtil = new CheckUtil();
                if (fileName != null && checkUtil.verify(fileName)) {
                    String realName;
                    String fileNameExtension = fileName.substring(fileName.indexOf("."));
                    // 生成实际存储的真实文件名
                    realName = UUID.randomUUID().toString() + fileNameExtension;
                    // 自定义的上传目录
                    String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
                    String year = strNow[0];
                    String month = strNow[1];
                    String day = strNow[2];
                    String path = "/uploadData/" + year + "/" + month + "/" + day + "/" + underwriting.getName();
                    if (!saveFile(multipartFile, path, realName)) {
                        map.put("status", 0);
                        map.put("msg", "文件上传失败");
                        log.info("文件上传失败");
                        return map;
                    }
                    if (underwriting.getData() == null || "".equals(underwriting.getData())) {
                        data.append(path).append("/").append(realName);
                    } else {
                        data.append(underwriting.getData()).append(",").append(path).append("/").append(realName);
                    }
                } else {
                    map.put("status", 0);
                    map.put("msg", "文件格式不正确");
                    log.info("文件格式不正确");
                    return map;
                }
            }
        }
        log.info("上传文件："+data);
        underwriting.setData(String.valueOf(data));
        underwritingMapper.updateByPrimaryKey(underwriting);
        map.put("status",1);
        map.put("msg","添加成功");
        log.info("添加成功");
        return map;
    }

    /**
     * 文件写入
     * @param file
     * @param path
     * @param realName
     */
    private boolean saveFile(MultipartFile file, String path, String realName) {
        try {
            File fileDr = new File(filePath + path);
            if(!fileDr.exists()&&!fileDr.isDirectory()) {
                boolean mkdirs = fileDr.mkdirs();
                if(!mkdirs) {
                    return false;
                }
            }
            if (!file.isEmpty()) {
                File saveFile = new File(filePath + path + "/" +realName);
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                BufferedOutputStream out = new BufferedOutputStream(outputStream);
                out.write(file.getBytes());
                out.flush();
                out.close();
                outputStream.close();
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String,Object> findUnderwriting(String encryptedData, String iv, String code) {
        Map<String,Object> map = new HashMap<>();
        //登录凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);

        String password = null;
        try {
            password = AesUtil.aesEncrypt(String.valueOf(map1.get("openid")), "lovewlgzs5201314");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Agent agent = agentMapper.selectByOpenid(password);
        if(agent==null) {
            map.put("status",0);
            map.put("msg","添加失败，非长城人寿北京分公司代理人");
            return map;
        }
        List<Underwriting> underwritingList =  underwritingMapper.selectByAgentId(agent.getAgentId());
        log.info("查询成功: "+underwritingList);
        map.put("status",1);
        map.put("msg","查询成功");
        map.put("list",underwritingList);
        return map;
    }

    @Override
    public PageInfo<UnderwritingTime> select(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> select = underwritingMapper.select(keyword);
        List<UnderwritingTime> underwritingTimes = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Underwriting underwriting : select) {
            underwritingTimes.add(new UnderwritingTime(underwriting.getUnderwritingId(), underwriting.getName(),
                    underwriting.getSex(), underwriting.getBirthday(), underwriting.getPhone(),
                    formatter.format(underwriting.getSubmitTime())));
        }
        return new PageInfo<>(underwritingTimes);
    }

    @Override
    public Underwriting selectById(Integer underwritingId) {
        return underwritingMapper.selectByPrimaryKey(underwritingId);
    }

    @Override
    public PageInfo<UnderwritingTime> selectHistory(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> select = underwritingMapper.selectHistory(keyword);
        List<UnderwritingTime> underwritingTimes = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Underwriting underwriting : select) {
            underwritingTimes.add(new UnderwritingTime(underwriting.getUnderwritingId(), underwriting.getName(),
                    underwriting.getSex(), underwriting.getBirthday(), underwriting.getPhone(),
                    underwriting.getIntroduce(),underwriting.getConclusion(),formatter.format(underwriting.getUpdateTime())));
        }
        return new PageInfo<>(underwritingTimes);
    }

    @Override
    public PageInfo<UnderwritingTime> selectByDate(String keyword, int pageNum, int pageSize) {
        //2017-05-06 至 2018-05-24yyyy-MM-dd HH:mm:ss
        String first = keyword.substring(0,10)+" 00:00:00";
        String last = keyword.substring(13)+" 00:00:00";
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> underwritingList = underwritingMapper.selectByDate(first,last);
        List<UnderwritingTime> underwritingTimes = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Underwriting underwriting : underwritingList) {
            underwritingTimes.add(new UnderwritingTime(underwriting.getUnderwritingId(), underwriting.getName(),
                    underwriting.getSex(), underwriting.getBirthday(), underwriting.getPhone(),
                    formatter.format(underwriting.getUpdateTime())));
        }
        return new PageInfo<>(underwritingTimes);
    }

    @Override
    public void statistical(String keyword, Integer type, HttpServletResponse response) throws IOException {
        //2017-05-06 至 2018-05-24
        String first = keyword.substring(0,10);
        String last = keyword.substring(13);
        List<Underwriting> underwritingList = underwritingMapper.selectAll();
        List<Underwriting> underwritingArrayList = new ArrayList<>();
        for (Underwriting underwriting : underwritingList) {
            //处理核保人的提交时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(underwriting.getSubmitTime());
            if(type == 1) {
                if (underwriting.getConclusion() == null && first.compareTo(dateString)<=0 && last.compareTo(dateString)>=0) {
                    underwritingArrayList.add(underwriting);
                }
            }
            if(type == 2) {
                if (underwriting.getConclusion() != null && first.compareTo(dateString)<=0 && last.compareTo(dateString)>=0) {
                    underwritingArrayList.add(underwriting);
                }
            }
        }
        if(underwritingArrayList.size()!=0) {
            List<Integer> agentIds = new ArrayList<>();
            for (Underwriting underwriting : underwritingArrayList) {
                agentIds.add(underwriting.getAgentId());
            }
            List<Agent> agentList = agentMapper.selectByAgentIds(agentIds);
            List<Agent> agentList1 = new ArrayList<>();
            for (Integer agentId : agentIds) {
                for (Agent agent : agentList) {
                    if (agentId.equals(agent.getAgentId())) {
                        agentList1.add(agent);
                    }
                }
            }
            List<UnderwritingDTO> underwritingDTOList = new ArrayList<>();
            for (int i = 0; i < underwritingArrayList.size(); i++) {
                underwritingDTOList.add(new UnderwritingDTO(agentList1.get(i).getCompany(),agentList1.get(i).getNickname(),
                        agentList1.get(i).getEmployeeId(),underwritingArrayList.get(i).getName(),underwritingArrayList.get(i).getSex(),
                        underwritingArrayList.get(i).getBirthday(),underwritingArrayList.get(i).getIntroduce(),
                        underwritingArrayList.get(i).getConclusion(),underwritingArrayList.get(i).getSubmitTime()));
            }
            underwritingDTOList.sort((UnderwritingDTO h1, UnderwritingDTO h2) -> h1.getCompany().compareTo(h2.getCompany()));
            underwritingDTOList.sort((UnderwritingDTO h1, UnderwritingDTO h2) -> h1.getNickname().compareTo(h2.getNickname()));
            log.info("查询成功："+ System.currentTimeMillis() +underwritingDTOList);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultRowHeightInPoints(20);
            HSSFPrintSetup ps = sheet.getPrintSetup();
            ps.setLandscape(false); // 打印方向，true：横向，false：纵向
            ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
            sheet.setHorizontallyCenter(true);//设置打印页面为水平居中

            //设置要导出的文件的名字
            String fileName = first+"至"+last+"工作量统计.xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //新增数据行，并且设置单元格数据
            int rowNum = 1;
            String[] headers = {"代理人公司", "代理人昵称", "代理人工号",
                    "姓名", "性别", "出生日期", "疾病史介绍", "结论", "提交时间"};
            //headers表示excel表中第一行的表头
            HSSFRow row = sheet.createRow(0);
            //设置行高
            row.setHeightInPoints(30);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            sheet.setColumnWidth(0, 19 * 256);
            sheet.setColumnWidth(1, 19 * 256);
            sheet.setColumnWidth(2, 19 * 256);
            sheet.setColumnWidth(3, 19 * 256);
            sheet.setColumnWidth(4, 19 * 256);
            sheet.setColumnWidth(5, 19 * 256);
            sheet.setColumnWidth(6, 19 * 256);
            sheet.setColumnWidth(7, 19 * 256);
            sheet.setColumnWidth(8, 19 * 256);
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
            for (UnderwritingDTO underwritingDTOS : underwritingDTOList) {
                HSSFRow row1 = sheet.createRow(rowNum);
                //设置行高
                row1.setHeightInPoints(25);
                cell = row1.createCell(0);
                cell.setCellValue(underwritingDTOS.getCompany());
                cell.setCellStyle(style);
                cell = row1.createCell(1);
                cell.setCellValue(underwritingDTOS.getNickname());
                cell.setCellStyle(style);
                cell = row1.createCell(2);
                cell.setCellValue(underwritingDTOS.getEmployeeId());
                cell.setCellStyle(style);
                cell = row1.createCell(3);
                cell.setCellValue(underwritingDTOS.getName());
                cell.setCellStyle(style);
                cell = row1.createCell(4);
                cell.setCellValue(underwritingDTOS.getSex());
                cell.setCellStyle(style);
                cell = row1.createCell(5);
                cell.setCellValue(underwritingDTOS.getBirthday());
                cell.setCellStyle(style);
                cell = row1.createCell(6);
                cell.setCellValue(underwritingDTOS.getIntroduce());
                cell.setCellStyle(style);
                cell = row1.createCell(7);
                cell.setCellValue(underwritingDTOS.getConclusion());
                cell.setCellStyle(style);
                cell = row1.createCell(8);
                cell.setCellValue(underwritingDTOS.getSubmitTime());
                cell.setCellStyle(style);
                rowNum++;
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }
    }

    @Override
    public Result send(Integer underwritingId, String auditResult, String note) {
        if(underwritingId==null ||underwritingId==0 || auditResult==null || "".equals(auditResult) ||
                note == null || "".equals(note)) {
            return new Result(0,"发送失败，信息不完整！");
        }
        Underwriting underwriting = underwritingMapper.selectByPrimaryKey(underwritingId);
        if(underwriting.getConclusion() != null) {
            return new Result(0,"发送失败，该核保人已经发送过通知");
        }
        Agent agent = agentMapper.selectByPrimaryKey(underwriting.getAgentId());
        if(agent == null) {
            return new Result(0,"发送失败，该代理人不存在");
        }
        String openid = null;
        try {
            openid = AesUtil.aesDecrypt(agent.getOpenid(), "lovewlgzs5201314");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        //代理人openid
        jsonObject.put("touser", openid);
        //消息模版id
        jsonObject.put("template_id", "1BTR4gR2KAcGX_LSXRS-7InGCIpezSEc3d5Te1qFT5k");
        //表单id
        jsonObject.put("form_id", underwriting.getFormId());
        String templateContent = "{'keyword1':{'value':'测试'},'keyword2':{'value':'" + auditResult +
                "'},'keyword3':{'value':'" + note + "'}}";
        jsonObject.put("data", templateContent);
        jsonObject.put("emphasis_keyword","keyword2.DATA");
        String string = HttpRequest.sendPost(jsonObject);
        log.info(string);
        String status = "ok";
        if(string.contains(status)) {
            underwriting.setConclusion("审核结果："+ auditResult+ "，备注：" +note);
            underwriting.setUpdateTime(new Date());
            underwritingMapper.updateByPrimaryKeySelective(underwriting);
        }
        return new Result(1,"发送成功！");
    }

}
