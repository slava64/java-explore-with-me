package ru.practicum.explorewithme.users.mapper;

import ru.practicum.explorewithme.users.User;
import ru.practicum.explorewithme.users.dto.NewUserDto;
import ru.practicum.explorewithme.users.dto.UserDto;
import ru.practicum.explorewithme.users.dto.UserShortDto;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getEmail()
        );
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static List<UserDto> toUserDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(toUserDto(user));
        }
        return usersDto;
    }

    public static User toUser(NewUserDto newUserDto) {
        User user = new User();
        user.setName(newUserDto.getName());
        user.setEmail(newUserDto.getEmail());
        return user;
    }
}
