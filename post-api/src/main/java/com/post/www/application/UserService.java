package com.post.www.application;

import com.post.www.domain.User;
import com.post.www.domain.UserRepository;
import com.post.www.interfaces.dto.UserAddRequestDto;
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
                .nickname(requestDto.getNickname())
                .name(requestDto.getName())
                .password(encodePassword)
                .email(requestDto.getEmail())
                .photo(requestDto.getPhoto())
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
}
