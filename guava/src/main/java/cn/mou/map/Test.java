package cn.mou.map;

import java.util.function.Consumer;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		String name = "";
		String name1 = "12345";
		
		validInput(name, inputStr ->  
				System.out.println(inputStr.isEmpty() ? "名字不能为空":"名字正常"));
		validInput(name1, inputStr ->
				System.out.println(inputStr.isEmpty() ? "名字不能为空":"名字正常"));
		
	}
	public static void validInput(String name, Consumer<String> function) {
		function.accept(name);
	}
}