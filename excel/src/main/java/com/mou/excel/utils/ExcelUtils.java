package com.mou.excel.utils;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static javax.xml.bind.JAXBIntrospector.getValue;

/**
 * Excel工具类
 *
 * @author: mou
 * @date: 2019-08-16
 */
public class ExcelUtils {

    /**
     * 导出excel
     *
     * @param titleText 标题栏内容
     * @param map       表头和属性的Map集合,其中Map中Key为Excel列的名称，Value为反射类的属性
     * @param list      要输出的对象集合
     * @param fileName  输出文件名称
     */
    public static void exportExcel(String titleText, Map<String, String> map, List<?> list, String fileName) {
        //创建单元格并设置单元格内容
        //返回的集合
        Set<String> keySet = map.keySet();
        Iterator<String> it = keySet.iterator();
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        XSSFSheet sheet = workbook.createSheet("数据源");
        // 设置默认列宽为15
        sheet.setDefaultColumnWidth(15);
        // 合并标题栏单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, keySet.size() - 1));
        //创建标题栏样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.VIOLET.getIndex());
        font.setFontHeightInPoints((short) 18);
        style.setFont(font);

        //创建表头样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 12);
        style2.setFont(font2);

        //创建数据行样式
        //暂时注释,有需要在使用
//        XSSFCellStyle style3 = workbook.createCellStyle();
//        style3.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
//        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style3.setBorderBottom(BorderStyle.THIN);
//        style3.setBorderLeft(BorderStyle.THIN);
//        style3.setBorderRight(BorderStyle.THIN);
//        style3.setBorderTop(BorderStyle.THIN);
//        style3.setAlignment(HorizontalAlignment.CENTER);
//        style3.setVerticalAlignment(VerticalAlignment.CENTER);

        // 写入标题行
        XSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(25);
        XSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);
        XSSFRichTextString textTitle = new XSSFRichTextString(titleText);
        cell.setCellValue(textTitle);

        //写入表头
        //参数为行索引(excel的行)，可以是0～65535之间的任何一个
        row = sheet.createRow(1);
        Map<String, String> attrMap = new HashMap<>();
        int index = 0;
        while (it.hasNext()) {
            String key = it.next().toString();
            cell = row.createCell(index);
            cell.setCellValue(key);
            cell.setCellStyle(style2);
            attrMap.put(Integer.toString(index++), map.get(key).toString());
        }

        //写入数据行
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            for (int j = 0; j < map.size(); j++) {
                //调用getter获取要写入单元格的数据值
                Object value = getter(list.get(i), attrMap.get(Integer.toString(j)));
                cell = row.createCell(j);
                cell.setCellValue(value.toString());
//                cell.setCellStyle(style3);
            }
        }

        //设置响应格式
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setCharacterEncoding("UTF-8");
        // 设置contentType为excel格式
        String agent = request.getHeader("USER-AGENT").toLowerCase();

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "Attachment;Filename=" + fileName + ".xlsx");
        ServletOutputStream out = null;
        // 输出Excel文件
        try {
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析excel
     *
     * @param file 接受的文件流
     * @param obj  po对象
     * @return 数据集合
     */
    public static List<Map<Object, Object>> resolver(MultipartFile file, Object obj) {

        List<Map<Object, Object>> mapList = new LinkedList<>();
        List<Object> fields = getFields(obj);
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            //获取所有工作表
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                //从第三行开始获取数据
                for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    Map<Object, Object> map = new LinkedHashMap<>();

                    //从第一列获取数据
                    for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        if (CellType.NUMERIC.equals(cell.getCellType())) {
                            map.put(fields.get(cellNum), new DataFormatter().formatCellValue(cell));
                        } else {
                            map.put(fields.get(cellNum), getValue(cell));
                        }
                    }
                    mapList.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    /**
     * getMap:(将传进来的表头和表头对应的属性存进Map集合，表头字段为key,属性为value)
     *
     * @return Map<String, String> 转换好的Map集合
     * 把传进指定格式的字符串解析到Map中
     * 形如: String keyValue = "手机名称:phoneName,颜色:color,售价:price";
     */
    public static Map<String, String> getMap(String keyValue) {
        Map<String, String> map = new LinkedHashMap<>();
        if (keyValue != null) {
            String[] str = keyValue.split(",");
            for (String element : str) {
                String[] str2 = element.split(":");
                map.put(str2[0], str2[1]);
            }
        }
        return map;
    }

    /**
     * 获取所有属性的字段名称
     *
     * @param object 传入的对象
     */
    public static List<Object> getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        ArrayList<Object> fieldList = Lists.newArrayList();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldList.add(field.getName());
        }
        return fieldList;
    }

    /**
     * @param obj      反射类对象
     * @param attrName 属性名
     */
    private static Object getter(Object obj, String attrName) {
        try {
            //获取反射的方法名
            Method method = obj.getClass().getMethod("get" + toUpperCaseFirstOne(attrName));
            Object value = new Object();
            //进行反射并获取返回值
            value = method.invoke(obj);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 首字母转大写
     *
     * @param str 需要转换1字符串
     * @return 转换后的字符串
     */
    private static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        } else {
            return Character.toUpperCase(str.charAt(0)) +
                    str.substring(1);
        }
    }
}
