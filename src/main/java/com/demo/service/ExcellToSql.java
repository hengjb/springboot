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
        for (ExcelBean bean : excelBean) {
            //参数1为终端ID
            //参数2为数据中心ID
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            long id = snowflake.nextId();
            sql.append(String.format("insert into tbl_ccms_log_act_tel ('tel_log_id','case_id','cust_no','name','rel_with_cust','tel_no','tel_type','crt_time','upd_time','remark','collector','crt_user','upd_user') " +
                            "values ('%s',(select case_id from tbl_ccms_biz_acct_account where cert_no = '%s'),(select cust_no from tbl_ccms_biz_acct_account where cert_no = '%s'),'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');\n",
                    id, bean.getCertNo(), bean.getCertNo(), bean.getName(), bean.getRelType(), bean.getTelNo(), "MB", bean.getCrtTime(), bean.getCrtTime(), bean.getRemark(), bean.getCollector(), bean.getCollector(), bean.getCollector()));
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
        String slqPath = "D:/test/insert01.sql";    //指定生成文件目录
        long startTime = System.currentTimeMillis();
        getSqlByHutool(filePath, slqPath);
//        getSqlByPOI(filePath, slqPath);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时为m:" + (endTime - startTime) / 1000);
    }
}
