package com.post.www.application;

import com.post.www.application.exception.EmailExistedException;
import com.post.www.application.exception.EmailNotExistedException;
import com.post.www.application.exception.PasswordWrongException;
import com.post.www.application.exception.UserNotExistedException;
import com.post.www.domain.User;
import com.post.www.domain.UserRepository;
import com.post.www.interfaces.dto.UserAddRequestDto;
import com.post.www.interfaces.dto.UserDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepostory;

    private final PasswordEncoder passwordEncoder;

    public User addUser(UserAddRequestDto requestDto) {
        Optional<User> existed = userRepostory.findByEmail(requestDto.getEmail());
        if (existed.isPresent()) {
            throw new EmailExistedException(requestDto.getEmail());
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = User.builder()
                .type(requestDto.getType())
                .nickname(requestDto.getNickname())
                .name(requestDto.getName())
                .password(encodePassword)
                .email(requestDto.getEmail())
                .comments(requestDto.getComments())
                .contents(requestDto.getContents())
                .build();

        return userRepostory.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepostory.findByEmail(email).orElseThrow(() ->
                new EmailNotExistedException(email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }
        return user;
    }

    public UserDetailResponseDto getUser(Long userIdx) {
        User user = userRepostory.findByIdx(userIdx).orElseThrow(() ->
                new UserNotExistedException(userIdx));
        return new UserDetailResponseDto(user);
    }

    @Transactional
    public User updateUser(Long userIdx, String nickname, String name, String comments, String contents, String filePath) {
        User user = userRepostory.findByIdx(userIdx).orElseThrow(() ->
                new UserNotExistedException(userIdx));
        user.updateUser(nickname, name, comments, contents, filePath);
        return user;
    }
}
