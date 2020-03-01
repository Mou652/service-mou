package cn.moblog.multithread;

import cn.moblog.multithread.insertDemo.enums.UserSexEnum;
import cn.moblog.multithread.insertDemo.model.User;
import cn.moblog.multithread.insertDemo.service.UserService;
import cn.moblog.multithread.utils.IdService;
import cn.moblog.multithread.utils.thread.ThreadExecuteUpdateService;
import cn.moblog.multithread.vo.PlatformAppsUserService;
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
        // userService.multiThread();
        userService.multiThread2();
    }

    @Test
    void threadExecuteUpdateServiceTest() {
        List<User> userList = getUserList();
        List<ThreadExecuteUpdateVO> vos = new ArrayList<>();
        ThreadExecuteUpdateVO<User> vo = new ThreadExecuteUpdateVO<>();
        vo.setClazz(User.class);
        vo.setExecuteList(userList);
        vos.add(vo);

        threadExecuteUpdateService.threadBatchInsert(vos);
    }

    @Test
    void strInsert() {
        userService.strInsert();
    }

    private List<User> getUserList() {
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
        return userList;
    }

    @Test
    void threadExecuteUpdateServiceTest2() {
        List<PlatformAppsUserService> userList = getUserList2();
        List<ThreadExecuteUpdateVO> vos = new ArrayList<>();
        ThreadExecuteUpdateVO<PlatformAppsUserService> vo = new ThreadExecuteUpdateVO<>();
        vo.setClazz(PlatformAppsUserService.class);
        vo.setExecuteList(userList);
        vos.add(vo);

        threadExecuteUpdateService.threadBatchInsert(vos);
    }

    private List<PlatformAppsUserService> getUserList2() {
        List<PlatformAppsUserService> userList = new ArrayList<>();
        IdService idService = new IdService();
        for (int i = 0; i < 5000000; i++) {
            PlatformAppsUserService user = new PlatformAppsUserService();
            user.setId(idService.nextId());
            user.setIsDeleted(0);
            user.setUserId(idService.nextId());
            user.setServiceId("2020022611441341a2250badd34f2fbc");
            userList.add(user);
        }
        return userList;
    }
}
