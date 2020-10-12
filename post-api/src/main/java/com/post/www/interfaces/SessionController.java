package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.SessionRequestDto;
import com.post.www.interfaces.dto.SessionResponseDto;
import com.post.www.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;


@Api(tags = {"4.SessionController"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
public class SessionController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "토큰 생성", notes = "회원 정보를 인증하고 토큰을 발급합니다")
    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto requestDto
    ) throws URISyntaxException {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userService.authenticate(email, password);
        String accessToken = jwtUtil.createToken(user.getIdx(), user.getNickname());
        String url = "/session";
        return ResponseEntity.created(new URI(url))
                .body(SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
}
