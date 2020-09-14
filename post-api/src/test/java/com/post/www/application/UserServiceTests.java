package com.post.www.application;

import com.post.www.config.enums.UserType;
import com.post.www.domain.User;
import com.post.www.domain.UserRepository;
import com.post.www.interfaces.dto.UserAddRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepostory;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addUser() {
        UserAddRequestDto requestDto = UserAddRequestDto.builder()
                .nickname("dlh1106")
                .type(UserType.CUSTORMER)
                .name("도훈")
                .password("pass")
                .email("dlh1106@naver.com")
                .photo("")
                .comments("")
                .contents("")
                .build();
        userService.addUser(requestDto);
        verify(userRepostory).save(any());
    }


    @Test
    public void addUserWithExistedEmail() {
        UserAddRequestDto requestDto = UserAddRequestDto.builder()
                .nickname("dlh1106")
                .type(UserType.CUSTORMER)
                .name("도훈")
                .password("pass")
                .email("dlh1106@naver.com")
                .photo("")
                .comments("")
                .contents("")
                .build();

        User user = User.builder()
                .build();
        given(userRepostory.findByEmail(any())).willReturn(Optional.of(user));
        assertThatThrownBy(() -> {
            userService.addUser(requestDto);
        }).isInstanceOf(EmailExistedException.class);

        verify(userRepostory, never()).save(any());
    }

    @Test
    public void authenticate() {
        String email = "test@test.com";
        String password = "1234";

        User mockUser = User.builder()
                .email(email)
                .build();
        given(userRepostory.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void authenticateWithNotExistedEmail() {
        String email = "x@test.com";
        String password = "1234";

        given(userRepostory.findByEmail(email))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.authenticate(email,password)).isInstanceOf(EmailNotExistedException.class);
    }

    @Test
    public void authenticateWithWrongPassword() {
        String email = "test@test.com";
        String password = "x";

        User mockUser = User.builder().email(email).build();

        given(userRepostory.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThatThrownBy(() -> userService.authenticate(email, password)).isInstanceOf(PasswordWrongException.class);
    }
}