// package cn.mou.excel;
//
// import cn.hutool.core.io.FileUtil;
// import cn.hutool.core.io.IoUtil;
// import cn.hutool.core.util.StrUtil;
// import com.cjc.dev.common.bean.DevServiceException;
// import org.apache.commons.compress.utils.Lists;
// import org.apache.poi.hssf.util.HSSFColor;
// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.*;
// import org.springframework.util.ResourceUtils;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;
// import org.springframework.web.multipart.MultipartFile;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.*;
// import java.lang.reflect.Field;
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
// import java.nio.charset.StandardCharsets;
// import java.util.*;
//
// import static cn.hutool.core.lang.Validator.isNotEmpty;
// import static javax.xml.bind.JAXBIntrospector.getValue;
//
//
// /**
//  * Excel工具类
//  *
//  * @author: mou
//  * @date: 2019-08-16
//  */
// public class ExcelUtils {
//
//     /**
//      * 导出excel
//      *
//      * @param map      表头和属性的Map集合,其中Map中Key为Excel列的名称，Value为反射类的属性
//      * @param list     要输出的对象集合
//      * @param fileName 输出文件名称
//      */
//     public static void exportExcel(Map<String, String> map, List<?> list, String fileName) {
//         //只导出前一万条数据
//         if (list.size() > 10000) {
//             list = list.subList(0, 9999);
//         }
//         //创建单元格并设置单元格内容
//         //返回的集合
//         Set<String> keySet = map.keySet();
//         Iterator<String> it = keySet.iterator();
//         XSSFWorkbook workbook = new XSSFWorkbook();
//         // 建立新的sheet对象（excel的表单）
//         XSSFSheet sheet = workbook.createSheet("数据源");
//         // 设置默认列宽为15
//         sheet.setDefaultColumnWidth(15);
//
//         //创建表头样式
//         XSSFCellStyle style2 = workbook.createCellStyle();
//         style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
//         style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//         style2.setBorderBottom(BorderStyle.THIN);
//         style2.setBorderLeft(BorderStyle.THIN);
//         style2.setBorderRight(BorderStyle.THIN);
//         style2.setBorderTop(BorderStyle.THIN);
//         style2.setAlignment(HorizontalAlignment.CENTER);
//         style2.setVerticalAlignment(VerticalAlignment.CENTER);
//         XSSFFont font2 = workbook.createFont();
//         font2.setFontHeightInPoints((short) 12);
//         style2.setFont(font2);
//
//         //创建数据行样式
//         //暂时注释,有需要在使用
//         XSSFCellStyle style3 = workbook.createCellStyle();
//         style3.setBorderBottom(BorderStyle.THIN);
//         style3.setBorderLeft(BorderStyle.THIN);
//         style3.setBorderRight(BorderStyle.THIN);
//         style3.setBorderTop(BorderStyle.THIN);
//         style3.setAlignment(HorizontalAlignment.CENTER);
//         style3.setVerticalAlignment(VerticalAlignment.CENTER);
//
//         //错误原因字段样式
//         XSSFCellStyle style4 = workbook.createCellStyle();
//         style4.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
//         style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//         style4.setBorderBottom(BorderStyle.THIN);
//         style4.setBorderLeft(BorderStyle.THIN);
//         style4.setBorderRight(BorderStyle.THIN);
//         style4.setBorderTop(BorderStyle.THIN);
//         style4.setAlignment(HorizontalAlignment.CENTER);
//         style4.setVerticalAlignment(VerticalAlignment.CENTER);
//         XSSFFont font4 = workbook.createFont();
//         font4.setFontHeightInPoints((short) 12);
//         font4.setColor(XSSFFont.COLOR_RED);
//         style4.setFont(font4);
//
//         //写入表头
//         //参数为行索引(excel的行)，可以是0～65535之间的任何一个
//         XSSFRow row = sheet.createRow(0);
//         Map<String, String> attrMap = new HashMap<String, String>();
//         XSSFCell cell;
//         int index = 0;
//         while (it.hasNext()) {
//             String key = it.next();
//             cell = row.createCell(index);
//             cell.setCellValue(key);
//             if ("错误原因".equals(key)) {
//                 cell.setCellStyle(style4);
//             } else {
//                 cell.setCellStyle(style2);
//             }
//             attrMap.put(Integer.toString(index++), map.get(key));
//         }
//
//         //写入数据行
//         for (int i = 0; i < list.size(); i++) {
//             row = sheet.createRow(i + 1);
//             for (int j = 0; j < map.size(); j++) {
//                 //调用getter获取要写入单元格的数据值
//
//                 Object value = getter(list.get(i), attrMap.get(Integer.toString(j)));
//                 cell = row.createCell(j);
//                 cell.setCellValue(value != null ? value.toString() : null);
//                 cell.setCellStyle(style3);
//             }
//         }
//
//         //设置响应格式
//         ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//         HttpServletRequest request = servletRequestAttributes.getRequest();
//         HttpServletResponse response = servletRequestAttributes.getResponse();
//         fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//         response.setCharacterEncoding("UTF-8");
//         // 设置contentType为excel格式
//         String agent = request.getHeader("USER-AGENT").toLowerCase();
//
//         response.setContentType("application/vnd.ms-excel;charset=utf-8");
//         try {
//             response.setHeader("Content-Disposition", "Attachment;Filename=" + fileName + ".xlsx");
//         } catch (Exception e) {
//             response.setHeader("Content-Disposition", "Attachment;Filename=" + fileName + ".xlsx");
//         }
//         ServletOutputStream out = null;
//         // 输出Excel文件
//         try {
//             response.flushBuffer();
//             out = response.getOutputStream();
//             workbook.write(out);
//             out.flush();
//             out.close();
//             workbook.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
//     /**
//      * 下载模板
//      */
//     public static void downTemplate(String fileName, InputStream inputStream) {
//         XSSFWorkbook workbook = new XSSFWorkbook();
//         //设置响应格式
//         ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//         HttpServletRequest request = servletRequestAttributes.getRequest();
//         HttpServletResponse response = servletRequestAttributes.getResponse();
//         fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//         response.setCharacterEncoding("UTF-8");
//         // 设置contentType为excel格式
//         String agent = request.getHeader("USER-AGENT").toLowerCase();
//
//         response.setContentType("application/vnd.ms-excel;charset=utf-8");
//         response.setHeader("Content-Disposition", "Attachment;Filename=" + fileName);
//
//         // 输出Excel文件
//         try {
//             ServletOutputStream out = response.getOutputStream();
//             IoUtil.copy(inputStream, out);
//             out.flush();
//             out.close();
//             workbook.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
//     /**
//      * 上传excel
//      *
//      * @param file     file
//      * @param tenantId 租户id
//      * @param fileName 保存的文件名称
//      * @return boolean
//      */
//     public static boolean uploadExcel(MultipartFile file, String tenantId, String fileName) {
//         BufferedInputStream in;
//         BufferedOutputStream out;
//         //文件是否大于规定大小
//         if (file.getSize() / 1048576 >= 5 && file.getSize() % 1048576 != 0) {
//             throw new DevServiceException("文件大小大于5M");
//         }
//
//         //文件名
//         String originalFilename = file.getOriginalFilename();
//         if (!checkExcelFileName(originalFilename)) {
//             throw new DevServiceException("文件类型错误,请传入excel类型文件,后缀为'.xls'或者'.xlsx'");
//         }
//         try {
//             //获取输入流
//             in = new BufferedInputStream(file.getInputStream());
//             //获取上传的路径
//             String filePath = getUploadPath(fileName, tenantId);
//             //获取输出流
//             out = FileUtil.getOutputStream(filePath);
//             //上传至指定路径
//             IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
//             //关闭流
//             out.close();
//             in.close();
//             //关闭流
//             return true;
//         } catch (IOException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 解析excel
//      * 默认从第三行开始解析数据
//      *
//      * @param file 接受的文件流
//      * @param obj  po对象
//      * @return 数据集合
//      */
//     public static List<Map<Object, Object>> resolver(MultipartFile file, Object obj, Integer beginLine) {
//
//         List<Map<Object, Object>> mapList = new LinkedList<>();
//         //反射获取所有字段
//         List<Object> fields = getFields(obj);
//         try {
//             Workbook workbook = WorkbookFactory.create(file.getInputStream());
//             //获取所有工作表
//             int numberOfSheets = workbook.getNumberOfSheets();
//             for (int i = 0; i < numberOfSheets; i++) {
//                 Sheet sheet = workbook.getSheetAt(i);
//
//                 //从第二行开始获取数据
//                 //for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
//                 for (int rowNum = beginLine; rowNum <= sheet.getLastRowNum(); rowNum++) {
//                     Row row = sheet.getRow(rowNum);
//                     Map<Object, Object> map = new LinkedHashMap<>();
//
//                     //从第一列获取数据
//                     for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
//                         Cell cell = row.getCell(cellNum);
//                         if (CellType.NUMERIC.equals(cell.getCellType())) {
//                             map.put(fields.get(cellNum), new DataFormatter().formatCellValue(cell));
//                         } else {
//                             map.put(fields.get(cellNum), getValue(cell));
//                         }
//                     }
//                     mapList.add(map);
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return mapList;
//     }
//
//     /**
//      * 解析excel
//      *
//      * @param file         file
//      * @param listObj      list对象
//      * @param disMatchList 未设置匹配的字段
//      * @param beginLine    开始解析行
//      * @return list
//      */
//     public static List<Map<Object, Object>> resolver(File file, List<Object> listObj, List<String> disMatchList, Integer beginLine) {
//
//         List<Map<Object, Object>> mapList = new LinkedList<Map<Object, Object>>();
//         try {
//             Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
//             //获取所有工作表
//             int numberOfSheets = workbook.getNumberOfSheets();
//             for (int i = 0; i < numberOfSheets; i++) {
//                 Sheet sheet = workbook.getSheetAt(i);
//                 for (int rowNum = beginLine; rowNum <= sheet.getLastRowNum(); rowNum++) {
//                     Row row = sheet.getRow(rowNum);
//                     Map<Object, Object> map = new LinkedHashMap<Object, Object>();
//                     //从第一列获取数据
//                     for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
//                         Cell cell = row.getCell(cellNum);
//                         String field = (String) listObj.get(cellNum);
//                         //设置未匹配的字段为""
//                         if (disMatchList.contains(field)) {
//                             map.put(field, "");
//                             continue;
//                         }
//                         if (isNotEmpty(cell)) {
//                             //设置匹配字段
//                             if (CellType.NUMERIC.equals(cell.getCellType())) {
//                                 map.put(field, new DataFormatter().formatCellValue(cell));
//                             } else {
//                                 map.put(field, String.valueOf(getValue(cell)));
//                             }
//                         } else {
//                             //如果单元格为空,设置为空字符串
//                             map.put(field, "");
//                         }
//                     }
//                     mapList.add(map);
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return mapList;
//     }
//
//     /**
//      * 将传进来的表头和表头对应的属性存进Map集合，表头字段为key,属性为value
//      *
//      * @return Map<String, String> 转换好的Map集合
//      * 把传进指定格式的字符串解析到Map中
//      * 形如: String keyValue = "手机名称:phoneName,颜色:color,售价:price";
//      */
//     public static Map<String, String> getMap(String keyValue) {
//         Map<String, String> map = new LinkedHashMap<>();
//         if (keyValue != null) {
//             String[] str = keyValue.split(",");
//             for (String element : str) {
//                 String[] str2 = element.split(":");
//                 map.put(str2[0], str2[1]);
//             }
//         }
//         return map;
//     }
//
//     /**
//      * 获取所有属性的字段名称
//      *
//      * @param object 传入的对象
//      */
//     public static List<Object> getFields(Object object) {
//         Field[] fields = object.getClass().getDeclaredFields();
//         ArrayList<Object> fieldList = Lists.newArrayList();
//         for (Field field : fields) {
//             field.setAccessible(true);
//             fieldList.add(field.getName());
//         }
//         return fieldList;
//     }
//
//     /**
//      * @param obj      jsonObject
//      * @param attrName 属性名
//      */
//     private static Object getter(Object obj, String attrName) {
//         try {
//             //获取反射的方法名
//             Method method = obj.getClass().getMethod("get" + toUpperCaseFirstOne(attrName));
//             //进行反射并获取返回值
//             return method.invoke(obj);
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//
//     /**
//      * @param obj      反射类对象
//      * @param attrName 属性名
//      */
//     /*private static Object getter(Object obj, String attrName) {
//         try {
//             //获取反射的方法名
//             Method method = obj.getClass().getMethod("get" + toUpperCaseFirstOne(attrName));
//             //进行反射并获取返回值
//             return method.invoke(obj);
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//     }*/
//
//     /**
//      * 首字母转大写
//      *
//      * @param str 需要转换1字符串
//      * @return 转换后的字符串
//      */
//     private static String toUpperCaseFirstOne(String str) {
//         if (Character.isUpperCase(str.charAt(0))) {
//             return str;
//         } else {
//             return Character.toUpperCase(str.charAt(0)) +
//                     str.substring(1);
//         }
//     }
//
//     /**
//      * 获取excel第一列 列名  表头
//      */
//     public static List<String> getFirstLineList(File file) {
//         List<String> list = new LinkedList<>();
//         //Map<String, String> fieldMap = new LinkedHashMap<>();
//         try {
//             FileInputStream fileInput = new FileInputStream(file);
//             Workbook workbook = WorkbookFactory.create(fileInput);
//             //反射获取所有字段
//             int numberOfSheets = workbook.getNumberOfSheets();
//             for (int i = 0; i < numberOfSheets; i++) {
//                 Sheet sheet = workbook.getSheetAt(i);
//                 Row row = sheet.getRow(i);
//                 for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
//                     //从第一列获取列名
//                     Cell cell = row.getCell(cellNum);
//                     if (!"null".equals(String.valueOf(getValue(cell))) && !"".equals(String.valueOf(getValue(cell)))) {
//                         list.add(String.valueOf(getValue(cell)));
//                         //fieldMap.put(String.valueOf(fields.get(cellNum)), String.valueOf(getValue(cell)));
//                     }
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return list;
//     }
//
//     /**
//      * 获取excel存放路径
//      *
//      * @param tenantId 租户id,excel存放文件的名称为对应的租户id
//      * @return 路径
//      */
//     public static String getPath(String tenantId, String fileName) {
//         String path;
//         try {
//             path = getFilePath(fileName, tenantId);
//         } catch (Exception e) {
//             throw new RuntimeException("读取excel文件失败");
//         }
//         return path;
//     }
//
//     /**
//      * 将Map转换为JavaBean
//      *
//      * @param map 被转换的map
//      * @param obj 转换后的对象类型
//      * @return
//      */
//     public static Object mapToBean(Map<Object, Object> map, Object obj) throws NoSuchMethodException, SecurityException,
//             IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//         // 获取JavaBean中的所有属性
//         Field[] field = obj.getClass().getDeclaredFields();
//         for (Field fi : field) {
//             // 判断key值是否存在
//             if (map.containsKey(fi.getName())) {
//                 // 获取key的value值
//                 String value = map.get(fi.getName()).toString();
//                 // 将属性的第一个字母转换为大写
//                 String frist = fi.getName().substring(0, 1).toUpperCase();
//                 // 属性封装set方法
//                 String setter = "set" + frist + fi.getName().substring(1);
//                 // 获取当前属性类型
//                 Class<?> type = fi.getType();
//                 // 获取JavaBean的方法,并设置类型
//                 Method method = obj.getClass().getMethod(setter, type);
//                 // 判断属性为double类型
//                 if ("java.lang.String".equals(type.getName())) {
//                     // 调用当前Javabean中set方法，并传入指定类型参数
//                     method.invoke(obj, value);
//                 } else if ("java.lang.Integer".equals(type.getName())) {
//                     method.invoke(obj, Integer.parseInt(value));
//                 } else if ("double".equals(type.getName())) {
//                     method.invoke(obj, Double.valueOf(value));
//                 } else if ("char".equals(type.getName())) {
//                     method.invoke(obj, value);
//                 } else if ("java.lang.Object".equals(type.getName())) {
//                     method.invoke(obj, value);
//                 }
//             }
//         }
//         return obj;
//     }
//
//     /**
//      * 将List<Map<String,Object>>转换成List<javaBean>
//      *
//      * @param listm
//      * @param obj
//      */
//     public static Object ListMapToListBean(List<Map<Object, Object>> listm, Object obj)
//             throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
//             InvocationTargetException {
//         List<Object> list = new ArrayList<Object>();
//         // 循环遍历出map对象
//         for (Map<Object, Object> m : listm) {
//             // 调用将map转换为JavaBean的方法
//             Object objs = mapToBean(m, obj);
//             // 添加进list集合
//             list.add(objs);
//         }
//         return list;
//     }
//
//     /***
//      * 驼峰命名转为下划线命名
//      *
//      * @param str 驼峰命名的字符串
//      */
//
//     public static String humpToUnderline(String str) {
//         StringBuilder sb = new StringBuilder(str);
//         int temp = 0;
//         if (!str.contains("_")) {
//             for (int i = 0; i < str.length(); i++) {
//                 if (i > 0 && Character.isUpperCase(str.charAt(i))) {
//                     sb.insert(i + temp, "_");
//                     temp += 1;
//                 }
//             }
//         }
//         return sb.toString().toLowerCase();
//     }
//
//     /**
//      * 下载模板
//      *
//      * @param map               导出字段
//      * @param excelTemplateName 导出文件名称
//      * @param sign              需要标红的字段
//      */
//     public static void downloadExcel(Map<String, String> map, String excelTemplateName, String sign) {
//         //创建单元格并设置单元格内容
//         //返回的集合
//         Set<String> keySet = map.keySet();
//         Iterator<String> it = keySet.iterator();
//         XSSFWorkbook workbook = new XSSFWorkbook();
//         // 建立新的sheet对象（excel的表单）
//         XSSFSheet sheet = workbook.createSheet("数据源");
//         // 设置默认列宽为15
//         sheet.setDefaultColumnWidth(15);
//
//         //创建表头样式
//         XSSFCellStyle style2 = workbook.createCellStyle();
//         style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
//         style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//         style2.setBorderBottom(BorderStyle.THIN);
//         style2.setBorderLeft(BorderStyle.THIN);
//         style2.setBorderRight(BorderStyle.THIN);
//         style2.setBorderTop(BorderStyle.THIN);
//         style2.setAlignment(HorizontalAlignment.CENTER);
//         style2.setVerticalAlignment(VerticalAlignment.CENTER);
//         XSSFFont font2 = workbook.createFont();
//         font2.setFontHeightInPoints((short) 12);
//         style2.setFont(font2);
//
//         //标红字段样式
//         XSSFCellStyle style3 = workbook.createCellStyle();
//         style3.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
//         style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//         style3.setBorderBottom(BorderStyle.THIN);
//         style3.setBorderLeft(BorderStyle.THIN);
//         style3.setBorderRight(BorderStyle.THIN);
//         style3.setBorderTop(BorderStyle.THIN);
//         style3.setAlignment(HorizontalAlignment.CENTER);
//         style3.setVerticalAlignment(VerticalAlignment.CENTER);
//         XSSFFont font3 = workbook.createFont();
//         font3.setFontHeightInPoints((short) 12);
//         font3.setColor(XSSFFont.COLOR_RED);
//         style3.setFont(font3);
//
//         //写入表头
//         //参数为行索引(excel的行)，可以是0～65535之间的任何一个
//         XSSFRow row = sheet.createRow(0);
//         Map<String, String> attrMap = new HashMap<>();
//         XSSFCell cell;
//         int index = 0;
//         while (it.hasNext()) {
//             String key = it.next();
//             cell = row.createCell(index);
//             cell.setCellValue(key);
//             if (StrUtil.isNotBlank(sign) && sign.contains(key)) {
//                 //必填的单元格标记为红色字体
//                 cell.setCellStyle(style3);
//             } else {
//                 cell.setCellStyle(style2);
//             }
//             attrMap.put(Integer.toString(index++), map.get(key));
//         }
//
//         //设置响应格式
//         ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//         HttpServletRequest request = servletRequestAttributes.getRequest();
//         HttpServletResponse response = servletRequestAttributes.getResponse();
//         response.setCharacterEncoding("UTF-8");
//         excelTemplateName = new String(excelTemplateName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//         response.setContentType("application/vnd.ms-excel;charset=utf-8");
//         try {
//             response.setHeader("Content-Disposition", "Attachment;Filename=" + excelTemplateName + ".xlsx");
//         } catch (Exception e) {
//             response.setHeader("Content-Disposition", "Attachment;Filename=" + excelTemplateName + ".xlsx");
//         }
//         ServletOutputStream out;
//         // 输出Excel文件
//         try {
//             response.flushBuffer();
//             out = response.getOutputStream();
//             workbook.write(out);
//             out.flush();
//             out.close();
//             workbook.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
//     /**
//      * 去除首尾指定字符
//      *
//      * @param str     字符串
//      * @param element 指定字符
//      * @return
//      */
//     public static String trimFirstAndLastChar(String str, String element) {
//         boolean beginIndexFlag;
//         boolean endIndexFlag;
//         do {
//             int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
//             int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
//             str = str.substring(beginIndex, endIndex);
//             beginIndexFlag = (str.indexOf(element) == 0);
//             endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
//         } while (beginIndexFlag || endIndexFlag);
//         return str;
//     }
//
//     /**
//      * 校验文件格式
//      *
//      * @param originalFilename 文件原始名
//      * @return boolean
//      */
//     public static boolean checkExcelFileName(String originalFilename) {
//         //文件的后缀名
//         String suf = null;
//         //校验文件格式
//         if (originalFilename != null) {
//             //文件的后缀
//             suf = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//         }
//         if (suf == null) {
//             return false;
//         }
//         return "xls".equalsIgnoreCase(suf) || "xlsx".equalsIgnoreCase(suf);
//     }
//
//     /**
//      * 获取上传的路径
//      *
//      * @param originalFilename 原始文件名
//      * @param tenantId         租户id
//      * @return str
//      */
//     public static String getUploadPath(String originalFilename, String tenantId) throws IOException {
//         StringBuilder stringBuilder = new StringBuilder();
//         String location = ResourceUtils.getURL("jar").getPath();
//         //创建一个文件夹
//         File file = new File(stringBuilder
//                 .append(location, 0, location.indexOf("jar"))
//                 .append(tenantId).toString());
//         if (!file.exists()) {
//             file.mkdirs();
//         }
//         location = stringBuilder
//                 .append("/")
//                 .append(originalFilename).toString();
//         return location;
//     }
//
//     /**
//      * 获取系统字段和表头字段和已经匹配的关系  共用
//      *
//      * @param returnMap         返回的map
//      * @param matchFieldList    已经匹配的关系字段列表
//      * @param dataBaseFieldList 数据库中的字段
//      * @param firstLineStrList  获取表头字段
//      */
//     public static void getMatchList(Map<String, Object> returnMap, List<Map<String, Object>> matchFieldList, List<Map<String, Object>> dataBaseFieldList, List<String> firstLineStrList) {
//         returnMap.put("excelField", firstLineStrList);
//         for (Map<String, Object> dbFieldMap : dataBaseFieldList) {
//             //数据库字段中文名
//             String columnName = String.valueOf(dbFieldMap.get("columnName"));
//             for (String excelField : firstLineStrList) {
//                 if (columnName.equals(excelField)) {
//                     dbFieldMap.put("configure", excelField);
//                     matchFieldList.add(dbFieldMap);
//                 }
//             }
//         }
//         returnMap.put("matchFieldList", matchFieldList);
//     }
//
//     /**
//      * 获取excel解析的字段列表
//      *
//      * @param matchFieldList 匹配字段列表
//      * @param file           excel文件
//      * @param beginLine      解析开始行需要改 要手动传入 标题行
//      * @param fields         反射获取的字段列表
//      * @return List<Map < Object, Object>>
//      */
//     public static List<Map<Object, Object>> getExcelResolverFieldList(List<Map<String, Object>> matchFieldList, File file, Integer beginLine, List<Object> fields) {
//         //获取未设置匹配的列表
//         List<String> disMatchList = new ArrayList<String>(fields.size());
//         //已经匹配的列表
//         List<String> matchList = new ArrayList<String>(matchFieldList.size());
//         for (Map<String, Object> matchFieldMap : matchFieldList) {
//             //取出column的值放入已经matchList
//             String column = (String) matchFieldMap.get("column");
//             matchList.add(column);
//         }
//         for (Object field : fields) {
//             String fieldStr = String.valueOf(field);
//             if (!matchList.contains(fieldStr)) {
//                 //加入未匹配的列表
//                 disMatchList.add(fieldStr);
//             }
//         }
//         return resolver(file, fields, disMatchList, beginLine);
//     }
//
//     /**
//      * 获取file路径
//      *
//      * @param tenantId 租户id
//      * @param fileName 文件名  固定
//      * @return str
//      * @throws Exception 异常
//      */
//     private static String getFilePath(String fileName, String tenantId) throws FileNotFoundException {
//         StringBuilder stringBuilder = new StringBuilder();
//         String location;
//         location = ResourceUtils.getURL("jar").getPath();
//         location = stringBuilder
//                 .append(location, 0, location.indexOf("jar"))
//                 .append(tenantId).append("/").append(fileName).toString();
//         return location;
//     }
// }
