package com.mou.mapstuct.demo;

import com.mou.mapstuct.mapper.User2UserDto;
import com.mou.mapstuct.model.User;
import com.mou.mapstuct.model.UserDto;

import java.math.BigInteger;
import java.util.Date;

public class Demo {
    public static void main(String[] args) {
        User user = new User();
        user.setId(BigInteger.valueOf(1));
        user.setName("pc");
        user.setAge(25);
        user.setBirthdate(new Date());
        user.setWallet(1.3467f);

        UserDto userDto = User2UserDto.MAPPER.userToUserDto(user);
        System.out.println(userDto);
    }
}