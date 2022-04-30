package com.mou.mybatis.config;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: mou
 * @date: 2021/12/11
 */
@Slf4j
public class MybatisSqlProcessInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // 从请求头获取当前登录用户id,匹配自己的创建渠道 拼接sql admin_id=xxx
        // 如果是管理员,在RequestExtentDataFilter先查询出自己的小组成员放入request, admin_id in =xxxxx
        // 添加注解,在线程添加修改标识(针对特定接口进行修改)
        String sql = boundSql.getSql();
        log.info("SQL增强插件,原始sql:{}", sql);
        if (sql != null) {
            String[] sqlSplit = sql.split("WHERE");
            sql = sqlSplit.length == 1 ? sqlSplit[0] + " WHERE name != '雍卓' " : sqlSplit[0] + " WHERE name != '雍卓' and" + sqlSplit[1];
        }
        log.info("SQL增强插件,增强后的SQL:{}", sql);
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(sql);
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {

    }
}
