package com.mou.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.mou.poi.model.StudentEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PoiApplicationTests {

    @Test
    public void contextLoads() {
        ExportParams params = new ExportParams();
        List<StudentEntity> list = new ArrayList();
        list.add(new StudentEntity("024", "牟江川", 1, new Date(), new Date()));
        list.add(new StudentEntity("025", "袁玥", 2, new Date(), new Date()));
        list.add(new StudentEntity("026", "雍卓斯基", 2, new Date(), new Date()));

        params.setHeight((short) 20);
        params.setTitle("计算机一班学生");
        params.setSheetName("数据源");

        ExcelExportUtil.exportExcel(params, StudentEntity.class, list);

//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
//                StudentEntity .class, list);
    }

}
