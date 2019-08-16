package com.mou.excel.api;

import com.mou.excel.model.PhoneModel;
import com.mou.excel.utils.ExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: mou
 * @date: 2019-08-16
 */
@RestController
public class ExcelApiController {

    String keyValue = "手机名称:phoneName,颜色:color,售价:price";

    @RequestMapping("/export")
    public void export() {
        String title = "excel导出测试";
        String fileName = "牟江川_excel导出测试";
        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setPhoneName("iphone");
        phoneModel.setColor("红色");
        phoneModel.setPrice(8888);
        List<PhoneModel> list = new ArrayList<>();
        list.add(phoneModel);
        try {
            ExcelUtils.exportExcel(title, ExcelUtils.getMap(keyValue), list, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/accept")
    public void accept(@RequestParam("file") MultipartFile file) {

        PhoneModel phoneModel = new PhoneModel();
        List<Map<Object, Object>> resolver = ExcelUtils.resolver(file, phoneModel);
        System.out.println(resolver);
    }
}
