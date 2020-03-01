package cn.moblog.multithread.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 全局ID生成器
 *
 * @author mou
 */
@Component
public class IdService {

	private static final int ID_LENGTH = 16;

	public static final int BINARY_LENGTH = 5;
	private Snowflake snowflake;

	public String nextStrId() {
		return String.valueOf(nextId());
	}

	public IdService() {
		String hostAddress;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		String[] ipArray = hostAddress.split("\\.");

		String workerIdBin = Integer.toBinaryString(Long.valueOf(ipArray[2]).intValue());
		String dataCenterIdBin = Integer.toBinaryString(Long.valueOf(ipArray[3]).intValue());

		long workerId = binaryToInt(workerIdBin);
		long dataCenterId = binaryToInt(dataCenterIdBin);

		this.snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
	}

	private Long binaryToInt(String binaryNum) {
		// 如果二进制长度大于5，拆分
		if (binaryNum.length() > BINARY_LENGTH) {
			binaryNum = StringUtils.substring(binaryNum, 3);
		}
		return NumberUtil.binaryToLong(binaryNum);
	}

	/**
	 * 生成16位长度的id,移除超出长度的机器码
	 *
	 * @return id
	 */
	public Long nextId() {
		//
		long id = snowflake.nextId();
		String idStr = String.valueOf(id);
		if (idStr.length() > ID_LENGTH) {
			//id长度超过16,移除掉部分机器码
			int delIdLength = idStr.length() - ID_LENGTH;
			String beginIdStr = idStr.substring(0, 4);
			String endIdStr = idStr.substring(beginIdStr.length() + delIdLength);
			idStr = beginIdStr.concat(endIdStr);
			return Long.valueOf(idStr);
		}
		return id;
		//long id = snowflake.nextId();
		//String workerIdStr = String.valueOf(snowflake.getWorkerId(id));
		//long dateTime = snowflake.getGenerateDateTime(id);
		//String idStr;
		//if (workerIdStr.length() > 2 || workerIdStr.length() <= 0) {
		//	idStr = String.valueOf(RandomUtil.randomInt(100, 999)).concat(String.valueOf(dateTime));
		//} else {
		//	idStr = workerIdStr.concat(String.valueOf(RandomUtil.randomInt(9))).concat(String.valueOf(dateTime));
		//}
		//return Long.parseLong(idStr);
	}
}
