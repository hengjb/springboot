package com.demo.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.demo.model.FileBean;

import java.io.File;
import java.util.List;

public class ExcellToFile {

    public static void main(String[] args) {
        readExcell();
    }

    public static void readExcell() {
        String filePath = "D:/test/附件信息_2021021901.xlsx";
        ExcelReader reader = ExcelUtil.getReader(filePath);
        List<FileBean> fileBeans = reader.readAll(FileBean.class);

        for (FileBean fileBean : fileBeans) {
            getFile(fileBean);
        }
    }

    private static void getFile(FileBean fileBean) {
        String fileUrl = fileBean.getImageUrl();
        String custName = fileBean.getCustName();
        String fileName = fileBean.getImageName();
        String path = "D:/test/附件信息/" + custName + "/" + fileName; //+ "." + fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length());
        //将文件下载后保存在E盘，返回结果为下载文件大小
        long size = HttpUtil.downloadFile(fileUrl, new File(path));
        System.out.println("Download size: " + size);

    }

}
