package com.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.demo.model.ExcelBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcellToSql {

    private static Map<String, String> DICT_MAP = new HashMap<>();

    static {
        DICT_MAP.put("本人","SELF");
        DICT_MAP.put("父母","2");
        DICT_MAP.put("朋友","5");
        DICT_MAP.put("配偶","1");
        DICT_MAP.put("其他","8");
        DICT_MAP.put("子女","2");
        DICT_MAP.put("同事","4");
        DICT_MAP.put("工作证明人","7");
        DICT_MAP.put("无","8");
        DICT_MAP.put("客户本人","SELF");
        DICT_MAP.put("朋友、同学、同事","5");
        DICT_MAP.put("兄弟姐妹","RLTV");
        DICT_MAP.put("其他亲属","OTHR");
        DICT_MAP.put("自己","SELF");
        DICT_MAP.put("未知","8");
        DICT_MAP.put("","8");
    }

    public static void getSqlByPOI(String filePath, String slqPath) {
        try {
            InputStream in = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(in);
            in.close();
            StringBuilder sql = new StringBuilder();
            Sheet sheetAt = wb.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();

            List<ExcelBean> excelBean = getExcelBean(sheetAt, lastRowNum);
            apperdSql(sql, excelBean);

            File file = new File(slqPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = sql.toString().getBytes();
            OutputStream os = new FileOutputStream(slqPath);
            os.write(bytes);
            os.flush();
            os.close();
            System.out.println("sheets");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<ExcelBean> getExcelBean(Sheet sheetAt, int lastRowNum) {
        List<ExcelBean> list = new ArrayList<>();
        for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
            ExcelBean bean = new ExcelBean();    //创建一个单独Bean、
            System.out.println("rowNum = " + rowNum);
            Row row = sheetAt.getRow(rowNum);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    bean.setName(cell.getStringCellValue());
                }
                cell = row.getCell(1);
                if (cell != null) {
                    bean.setCardNo(cell.getStringCellValue());
                }
                cell = row.getCell(2);
                if (cell != null) {
                    bean.setCertNo(cell.getStringCellValue());
                }
                cell = row.getCell(3);
                if (cell != null) {
                    bean.setTelName(cell.getStringCellValue());
                }
                cell = row.getCell(4);
                if (cell != null) {
                    bean.setRelType(cell.getStringCellValue());
                }
                cell = row.getCell(5);
                if (cell != null) {
                    bean.setTelNo(cell.getStringCellValue());
                }
                cell = row.getCell(6);
                if (cell != null) {
                    bean.setCrtTime(cell.getStringCellValue());
                }
                cell = row.getCell(7);
                if (cell != null) {
                    bean.setRemark(cell.getStringCellValue());
                }
                cell = row.getCell(8);
                if (cell != null) {
                    bean.setCollector(cell.getStringCellValue());
                }
                cell = row.getCell(9);
                if (cell != null) {
                    bean.setCollectors(cell.getStringCellValue());
                }

            }
            list.add(bean);
        }
        return list;
    }

    private static void apperdSql(StringBuilder sql, List<ExcelBean> excelBean) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        for (ExcelBean bean : excelBean) {
            //参数1为终端ID
            //参数2为数据中心ID
            long id = snowflake.nextId();
            sql.append(String.format("insert into tbl_ccms_log_act_tel (`tel_log_id`,`case_id`,`cust_no`,`name`,`rel_with_cust`,`tel_no`,`tel_type`,`crt_time`,`upd_time`,`remark`,`collector`,`crt_user`,`upd_user`) " +
                            "values ('%s',(select case_id from tbl_ccms_biz_acct_account where cert_no = '%s' LIMIT 1),(select cust_no from tbl_ccms_biz_acct_account where cert_no = '%s' LIMIT 1),'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');\n",
                    id, bean.getCertNo(), bean.getCertNo(), bean.getName(), DICT_MAP.get(bean.getRelType()), bean.getTelNo(), "MB", bean.getCrtTime(), "2020-12-28", bean.getRemark(), bean.getCollector(), bean.getCollector(), "hengjb"));
        }
    }

    public static void getSqlByHutool(String filePath, String slqPath) {
        StringBuilder sql = new StringBuilder();
        ExcelReader reader = ExcelUtil.getReader(filePath);
        List<ExcelBean> excelBean = reader.readAll(ExcelBean.class);
        apperdSql(sql, excelBean);
        FileWriter writer = new FileWriter(slqPath);
        writer.write(sql.toString());
    }


    public static void main(String args[]) {
        String filePath = "D:/test/20200630.xlsx";    //指定本地的数据目录
        String slqPath = "D:/test/insert05.sql";    //指定生成文件目录
        long startTime = System.currentTimeMillis();
        getSqlByHutool(filePath, slqPath);
//        getSqlByPOI(filePath, slqPath);
        System.out.println(DICT_MAP.get(""));
        long endTime = System.currentTimeMillis();
        System.out.println("耗时为m:" + (endTime - startTime) / 1000);
    }
}
