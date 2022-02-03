package com.dheeraj.startup.entities.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestTest {
    @Test
    public void test() {
        UserRequest userRequest = UserRequest.builder().userEmail("knd@mail.com").build();
        assertThat(userRequest.getUserEmail()).isEqualTo("knd@mail.com");
    }
}
