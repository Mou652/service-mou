package cn.moblog.multithread.transaction;

/**
 * @author: mou
 * @date: 2020/2/21
 */
public class Demo {

    //saveQrCodeFileRecord(vo.getSource(), reqMsgId, vo.getOssObjName(), vo.getFileMd5(), QrCodeFileTypeEnum.CHECK_STOCK, true);
    //
    //// 执行异步盘点
    //// 事务提交后，执行消息发送
    //    delayer.executeAfterTransactionCommit(
    //            () -> {
    //    // 文件创建后，发送消息
    //    QrCodeCheckMqVO message = new QrCodeCheckMqVO(reqMsgId);
    //    aliwareMqProducer.send(RocketMqConstants.CODE_CHECK_NOTIFY_TAG, message);
    //    log.debug("Push romq To {} message:{}", RocketMqConstants.CODE_CHECK_NOTIFY_TAG, message);
    //}
    //    );
}
