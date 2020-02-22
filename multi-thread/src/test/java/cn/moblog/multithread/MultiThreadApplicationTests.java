package cn.moblog.multithread;

import cn.moblog.multithread.insertDemo.enums.UserSexEnum;
import cn.moblog.multithread.insertDemo.model.User;
import cn.moblog.multithread.insertDemo.service.UserService;
import cn.moblog.multithread.utils.ThreadExecuteUpdateService;
import cn.moblog.multithread.vo.ThreadExecuteUpdateVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class MultiThreadApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private ThreadExecuteUpdateService threadExecuteUpdateService;

    @Test
    void contextLoads() {
        userService.multiThread();
    }

    @Test
    void threadExecuteUpdateServiceTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString().replace("-", ""));
            user.setNickName(String.valueOf(i));
            user.setPassWord(String.valueOf(i));
            user.setUserName(String.valueOf(i));
            user.setUserSex(UserSexEnum.MAN);
            userList.add(user);
        }
        List<ThreadExecuteUpdateVO> vos = new ArrayList<>();
        ThreadExecuteUpdateVO<User> vo = new ThreadExecuteUpdateVO<>();
        vo.setClazz(User.class);
        vo.setExecuteList(userList);
        vos.add(vo);

        threadExecuteUpdateService.threadBatchInsert(vos);
    }

}
