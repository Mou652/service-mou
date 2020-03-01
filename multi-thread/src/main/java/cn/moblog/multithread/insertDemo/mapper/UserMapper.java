package cn.moblog.multithread.insertDemo.mapper;


import cn.moblog.multithread.insertDemo.model.User;
import cn.moblog.multithread.vo.PlatformAppsUserService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
	
	List<User> getAll();
	
	User getOne(Long id);

	void insert(User user);

	void update(User user);

	void delete(Long id);

	void insertBatch(List<User> list);

	void insertBatchPlatform(List<PlatformAppsUserService> list);

	void strInsert(@Param("sql") String sql);
}