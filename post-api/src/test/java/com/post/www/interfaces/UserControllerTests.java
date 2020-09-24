package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.config.enums.UserType;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.UserDetailResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    public void create() throws Exception {
        User mockUser = User.builder()
                .idx(3L)
                .type(UserType.CUSTORMER)
                .nickname("dlh1106")
                .name("도훈")
                .email("dlh1106@naver.com")
                .password("password")
                .build();

        given(userService.addUser(any()))
                .willReturn(mockUser);
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"CUSTORMER\",\"nickname\":\"dlh1106\",\"name\":\"도훈\",\"email\":\"dlh1106@naver.com\",\"password\":\"password\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/user/3"))
                .andExpect(content().string("{}"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));

        verify(userService).addUser(any());
    }

    @Test
    void detail() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";
        UserDetailResponseDto responseDto = new UserDetailResponseDto(
                User.builder()
                        .idx(5L)
                        .type(UserType.CUSTORMER)
                        .name("john")
                        .nickname("john")
                        .email("test@test.com")
                        .password("1234")
                        .build()
        );
        given(userService.getUser(1004L)).willReturn(responseDto);

        mvc.perform(get("/user")
                .header("Authorization", "Bearer " + token)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"john\"")))
                .andExpect(content().string(containsString("\"nickname\":\"john\"")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void update() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjYsIm5hbWUiOiLtm4giLCJpYXQiOjE2MDA5NDY5MTcsImV4cCI6MTYwMDk1NDExN30.eJ3LighNkf0wujYeVDyqPOxRAspvDWpBgiLSvN3vL-M";
//        {}
        MockMultipartFile multipartFile = new MockMultipartFile("photo", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        mvc.perform(multipart("/f-user")
                        .file(multipartFile)
//                .with(request -> {
//                    request.setMethod("PATCH");
//                    return request;
//                })
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .param("contents","ssss")
                        .param("nickname","ss")
                        .param("name","gnm")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));

        verify(userService).updateUser(any(), any(), any(), any(), any(), any());
    }
}