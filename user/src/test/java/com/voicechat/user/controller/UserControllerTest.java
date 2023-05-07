package com.voicechat.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicechat.user.application.impl.UserServiceImpl;
import com.voicechat.user.dto.SignupDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test()
    @DisplayName("유저 회원가입 테스트")
    public void signupUserTest() throws Exception {
        final var signupDtoReq = new SignupDto.SignupDtoReq(
                "n4oahdev@gmail.com",
                "aa123123",
                "n4oah"
        );
        final var objectMapper = new ObjectMapper();
        final var signupDtoReqJson = objectMapper.writeValueAsBytes(signupDtoReq);

        BDDMockito.willDoNothing().given(userService).signup(signupDtoReq);

        mockMvc.perform(
                MockMvcRequestBuilders.post(
                "/signup"
                ).content(signupDtoReqJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(userService).signup(signupDtoReq);
    }
}
