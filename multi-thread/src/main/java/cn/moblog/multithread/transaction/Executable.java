package cn.moblog.multithread.transaction;

/**
 * @author: defei
 * @date 2017/9/14
 */
@FunctionalInterface
public interface Executable {

	/**
	 * 回调代码块
	 */
	void exec();

}
