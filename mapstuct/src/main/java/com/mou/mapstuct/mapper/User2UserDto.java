package com.mou.mapstuct.mapper;

import com.mou.mapstuct.model.User;
import com.mou.mapstuct.model.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 咸鱼
 * @date 2018/12/13 19:59
 */
@Mapper
public interface User2UserDto {
    User2UserDto MAPPER = Mappers.getMapper(User2UserDto.class);

    /**
     * POJO 转 DTO
     * 备注：只有属性名不一样的才需要单独写出来
     */
    @Mappings({
            @Mapping(target = "birth", source = "birthdate"),
            @Mapping(target = "birthformat", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(user.getBirthdate(),\"yyyy-MM-dd HH:mm:ss\"))")
    })
    UserDto userToUserDto(User user);

    /**
     * 集合转集合
     */
    List<UserDto> usersToUserDtos(List<User> users);

    /**
     * DTO  转 POJO
     */
    @InheritInverseConfiguration
    User userDtoToUser(UserDto userDto);
}