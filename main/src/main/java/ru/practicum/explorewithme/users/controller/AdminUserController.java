package ru.practicum.explorewithme.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.users.dto.NewUserDto;
import ru.practicum.explorewithme.users.dto.UserDto;
import ru.practicum.explorewithme.users.service.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminUserController {
    private final AdminUserService userService;

    @GetMapping
    public List<UserDto> getList(
            @RequestParam(value = "ids", required = false) List<@Positive Long> ids,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size
            ) {
        log.info("Find all Users");
        return userService.getList(ids, from, size);
    }

    @PostMapping
    public UserDto createItem(@RequestBody @Valid NewUserDto newUserDto) {
        log.info("Create new user {}", newUserDto.toString());
        return userService.createItem(newUserDto);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") @Positive Long userId) {
        log.info("Delete user {}", userId);
        userService.deleteItem(userId);
    }
}
