package com.onpu.web.integration.service.implementation;

import com.onpu.web.integration.annotation.IT;
import com.onpu.web.service.implementation.UserServiceImpl;
import com.onpu.web.store.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserServiceImplIT {

    private static final String USER_ID = "1";

    private final UserServiceImpl userService;

    @Test
    void findById() {
    }

    @Test
    void create(){
    }

}