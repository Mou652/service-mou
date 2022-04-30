package cn.mou.mybatis;

import cn.hutool.core.util.StrUtil;

import java.util.*;

public class SQLDemo {
    public static String sqlCommon="";
    //模拟写sql语句
    public static String querySQL(Map<String,Object> map,String tableName){
        //map若有参数查询则size从1开始算起
        if (map.isEmpty()||map.size()==0) {
         sqlCommon = "select * from "+tableName;
        } else {
            sqlCommon = "select * from "+tableName+" where ";
            //再进行拼接sql语句回去
            //如果只有一个则不用添加and 若有多个参数则再设计
            Set set = map.keySet();
            Iterator  iterable =  set.iterator();
            int index = 0;//默认下标为零，其作用是判断是否与map的最后一个参数相等从而去掉and
            while(iterable.hasNext()){
                String key = (String)iterable.next();
                Object value = map.get(key);
                //驼峰转下划线
                key=StrUtil.toUnderlineCase(key);
                //判断类型
                if (value instanceof String) {value="'"+value+"'";}
                index++;
                //仅有一个参数或是最后一个参数
                if ( ( index==1&&map.size()==1)||map.size()==index) {
                    sqlCommon += key + "="+value+" ";
                } else {
                    //Constants.SQL.ADD 和 and是一样的
                    //sqlCommon += key+"="+value+" "+ Constants.SQL.ADD +" ";
                    sqlCommon += key+"="+value+" "+ "and" +" ";
                }
            }
        }
        return sqlCommon;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Map<String,Object> map = new HashMap<String, Object>();
        //存放值和类型标识
        map.put("stuName","张三");
        map.put("classId",123456);
        map.put("age",23);
        String tableName="Student";
        System.out.println(querySQL(map,tableName).toString());
    }
}

