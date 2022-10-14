package ru.practicum.explorewithme.users.service;

import ru.practicum.explorewithme.users.dto.NewUserDto;
import ru.practicum.explorewithme.users.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getList(List<Long> ids, Integer from, Integer size);

    UserDto createItem(NewUserDto newUserDto);

    void deleteItem(Long userId);
}
