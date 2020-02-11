package com.mou.demo;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mou.demo.support.BladeCodeGenerator;

import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author mou
 */
public class CodeGenerator {

	/**
	 * 不用看代码,直接run
	 * 注意: 默认包名是脚手架micro-services
	 */
	public static void main(String[] args) {
		BladeCodeGenerator generator = new BladeCodeGenerator();
		generator.setServiceName(SERVICE_NAME);
		generator.setPackageName(PACKAGE_NAME);
		generator.setTablePrefix(TABLE_PREFIX);
		generator.setIncludeTables(scanner("表名，多个表名用逗号分割").split(","));
		generator.setPackageDir(scanner("后端代码生成地址"));
		generator.setExcludeTables(EXCLUDE_TABLES);
		generator.run();
	}

	/**
	 * 代码生成的模块名
	 */
	public static String CODE_NAME = "test" ;

	/**
	 * 代码所在服务名
	 */
	public static String SERVICE_NAME = "blade-workbench" ;

	/**
	 * 代码生成的包名
	 */
	public static String PACKAGE_NAME = "tech.scedu.micro.template.demo" ;

	/**
	 * 需要去掉的表前缀
	 */
	public static String[] TABLE_PREFIX = {""};

	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	public static String[] EXCLUDE_TABLES = {};

	/**
	 * 后端代码生成地址(改成本地路径)
	 */
	public static String PACKAGE_DIR = "/Users/mou/Downloads/0206任务-修改代码生成模板/" ;

	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		tip.replaceAll("，",",");
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		System.out.println(help.append("请输入" + tip + "："));
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}
}
