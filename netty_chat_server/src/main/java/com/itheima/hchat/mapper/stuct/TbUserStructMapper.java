package com.itheima.hchat.mapper.stuct;

import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.vo.User.User;
import org.mapstruct.Mapper;

/**
 * @author 咸鱼
 * @date 2018/12/13 19:59
 */
@Mapper(componentModel = "spring")
public interface TbUserStructMapper {

//    /**
//     * POJO 转 DTO
//     * 备注：只有属性名不一样的才需要单独写出来
//     */
//    @Mappings({
//            @Mapping(target = "birth", source = "birthdate"),
//            @Mapping(target = "birthformat", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(user.getBirthdate(),\"yyyy-MM-dd HH:mm:ss\"))")
//    })
//    UserDto userToUserDto(User user);
//
//    /**
//     * 集合转集合
//     */
//    List<UserDto> usersToUserDtos(List<User> users);
//
//    /**
//     * DTO  转 POJO
//     */
//    @InheritInverseConfiguration
//    User userDtoToUser(UserDto userDto);

    User tbUserToVo(TbUser user);
}