package com.itheima.hchat.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.itheima.hchat.mapper.TbFriendMapper;
import com.itheima.hchat.mapper.TbFriendReqMapper;
import com.itheima.hchat.mapper.TbUserMapper;
import com.itheima.hchat.mapper.stuct.TbUserStructMapper;
import com.itheima.hchat.pojo.*;
import com.itheima.hchat.pojo.vo.User.User;
import com.itheima.hchat.service.UserService;
import com.itheima.hchat.utils.FastDFSClient;
import com.itheima.hchat.utils.FileUtils;
import com.itheima.hchat.utils.IdWorker;
import com.itheima.hchat.utils.QRCodeUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author: mou
 * @date: 2019/10/13
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private IdWorker idWorker;
    private TbUserMapper userMapper;
    private TbUserStructMapper tbUserStructMapper;
    private QRCodeUtils qrCodeUtils;
    private Environment environment;
    private FastDFSClient fastDFSClient;
    private TbFriendMapper tbFriendMapper;
    private TbFriendReqMapper tbFriendReqMapper;

    @Override
    public List<TbUser> findAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public User login(String username, String password) {
        //封装查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //查询用户
        List<TbUser> users = userMapper.selectByExample(example);
        //输入的MD5加密,验证数据库与密码是否相同
        if (CollUtil.isNotEmpty(users)) {
            String encodingPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            if (encodingPassword.equals(users.get(0).getPassword())) {
                return tbUserStructMapper.tbUserToVo(users.get(0));
            }
        }
        return null;
    }

    @Override
    public void register(TbUser user) {
        //判断用户是否存在
        try {
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(user.getUsername());
            List<TbUser> users = userMapper.selectByExample(example);

            if (CollUtil.isNotEmpty(users)) {
                throw new RuntimeException("用户已存在");
            }
            //生成二维码
            String qrCodeStr = "hichat://" + user.getUsername();
            //生成临时目录,保存临时的二维码图片
            String tmpdir = environment.getProperty("hcat.tmpdir");
            String qrCodeFilePath = tmpdir + user.getUsername() + ".png";
            qrCodeUtils.createQRCode(qrCodeFilePath, qrCodeStr);
            //将临时保存的二维码,上传到fastDFS,返回url没有地址,需要手动拼接
            String url = fastDFSClient.uploadFile(FileUtils.fileToMultipart(qrCodeFilePath));
            //完整的url
            url = environment.getProperty("fdfs.httpurl" + url);
            //手动生成id
            user.setId(idWorker.nextId());
            //密码加密
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setPicNormal("");
            user.setPicSmall("");
            user.setNickname("");
            user.setQrcode(url);
            user.setCreatetime(DateUtil.date());

            userMapper.insert(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索好友
     *
     * @param userId         用户id
     * @param friendUsername 搜索的好友名称(需要添加的用户)
     * @return
     */
    @Override
    public User findByUsername(String userId, String friendUsername) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(friendUsername);
        List<TbUser> friend = userMapper.selectByExample(example);

        return tbUserStructMapper.tbUserToVo(friend.get(0));
    }

    /**
     * 添加好友时,检查是否允许添加好友
     */
    private void checkAllowToAadFriend(String userId, List<TbUser> friend) {
        //用户不能添加自己
        if (userId.equals(friend.get(0).getId())) {
            throw new RuntimeException("不能添加自己");
        }
        //不能重复添加好友
        TbFriendExample friendExample = new TbFriendExample();
        TbFriendExample.Criteria friendCriteria = friendExample.createCriteria();
        friendCriteria.andUseridEqualTo(userId);
        friendCriteria.andFriendsIdEqualTo(friend.get(0).getId());
        List<TbFriend> tbFriends = tbFriendMapper.selectByExample(friendExample);
        if (CollUtil.isNotEmpty(tbFriends)) {
            throw new RuntimeException("已经是你的好友了");
        }
        //判断是否已经提交好友申请
        TbFriendReqExample friendReqExample = new TbFriendReqExample();
        TbFriendReqExample.Criteria friendReqCriteria = friendReqExample.createCriteria();

        //当前用户发送的好友申请
        friendReqCriteria.andFromUseridEqualTo(friend.get(0).getId());
        friendReqCriteria.andToUseridEqualTo(friend.get(0).getId());
        //请求没有被处理
        friendReqCriteria.andStatusEqualTo(0);
        List<TbFriendReq> friendReqList = tbFriendReqMapper.selectByExample(friendReqExample);
        if (CollUtil.isNotEmpty(friendReqList)) {
            throw new RuntimeException("已经申请过了");
        }
    }
}
