package ru.practicum.explorewithme.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.users.User;
import ru.practicum.explorewithme.users.mapper.UserMapper;
import ru.practicum.explorewithme.users.repository.UserRepository;
import ru.practicum.explorewithme.users.dto.NewUserDto;
import ru.practicum.explorewithme.users.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getList(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of((int) from / size, size);
        List<User> userList = userRepository.findByIdIn(ids, pageable);
        return UserMapper.toUserDto(userList);
    }

    @Override
    public UserDto createItem(NewUserDto newUserDto) {
        User savedUser = userRepository.save(UserMapper.toUser(newUserDto));
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public void deleteItem(Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Пользователь %d не найден", userId))
        );
        userRepository.deleteById(userId);
    }
}
