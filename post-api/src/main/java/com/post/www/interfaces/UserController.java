package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.UserAddRequestDto;
import com.post.www.interfaces.dto.UserDetailResponseDto;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Api(tags = {"3.UserController"})
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 가입", notes = "회원을 등록합니다.")
    @PostMapping("/user")
    public ResponseEntity<?> create(
            @Valid @RequestBody UserAddRequestDto requestDto
    ) throws URISyntaxException {
        User user = userService.addUser(requestDto);
        String url = "/user/" + user.getIdx();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @ApiOperation(value = "회원 조회", notes = "현재 로그인된 회원을 조회합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization" , value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @GetMapping("/user")
    public UserDetailResponseDto detail(
            Authentication authentication
    ) {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);
        return userService.getUser(userIdx);
    }
}