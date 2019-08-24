package com.mou.poi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.mou.poi.model.StudentEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PoiController {

    @Test
    public void contextLoads() throws IOException {
        ExportParams params = new ExportParams();
        List<StudentEntity> list = new ArrayList();
        list.add(new StudentEntity("024", "牟江川", 1, new Date(), new Date()));
        list.add(new StudentEntity("025", "袁玥", 2, new Date(), new Date()));
        list.add(new StudentEntity("026", "雍卓斯基", 2, new Date(), new Date()));

        params.setHeight((short) 20);
        params.setTitle("计算机一班学生");
        params.setSheetName("数据源");

        Workbook sheets = ExcelExportUtil.exportExcel(params, StudentEntity.class, list);
        FileOutputStream stream = new FileOutputStream("/Users/mou/IdeaProjects/service-mou/poi/src/main/resources/excel/excel.xls");
        sheets.write(stream);
        sheets.close();
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
//                StudentEntity .class, list);
    }

}
