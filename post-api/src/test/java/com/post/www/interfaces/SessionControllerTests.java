package com.post.www.interfaces;

import com.post.www.application.EmailNotExistedException;
import com.post.www.application.PasswordWrongException;
import com.post.www.application.UserService;
import com.post.www.domain.User;
import com.post.www.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.NotEmpty;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        Long id = 1004L;
        String name = "dlh1106";
        String email = "test@test.com";
        String password = "1234";

        User mockUser = User.builder().idx(id).nickname(name).build();

        given(userService.authenticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id, name))
                .willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\",\"password\":\"1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")));
        verify(userService).authenticate(eq(email),eq(password));
    }

    @Test
    public void createWithPasswordWrong() throws Exception {
        given(userService.authenticate("test@test.com","x"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());
        verify(userService).authenticate(eq("test@test.com"),eq("x"));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("x@test.com","1234"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@test.com\",\"password\":\"1234\"}"))
                .andExpect(status().isBadRequest());
        verify(userService).authenticate(eq("x@test.com"),eq("1234"));
    }
}