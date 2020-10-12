package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.UserAddRequestDto;
import com.post.www.interfaces.dto.UserDetailResponseDto;
import com.post.www.interfaces.dto.UserUpdateRequestDto;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.UUID;

@Api(tags = {"3.UserController"})
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Value("${temp.path}")
    private String tempPath;

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
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @GetMapping("/user")
    public UserDetailResponseDto detail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);
        return userService.getUser(userIdx);
    }

    @ApiOperation(value = "회원 정보 수정", notes = "현재 로그인된 회원정보를 수정합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @PostMapping(value = "/f-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @ModelAttribute @Valid UserUpdateRequestDto requestDto
            ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);
        String nickname = requestDto.getNickname();
        String name = requestDto.getName();
        String comments = requestDto.getComments();
        String contents = requestDto.getContents();

        LocalDate localDate = LocalDate.now();
        String filePath = "";
        try {
            if(requestDto.getPhoto() != null){
                filePath = tempPath + localDate + UUID.randomUUID().toString().replace("-", "");
                requestDto.getPhoto().transferTo(new File(filePath));
            }
        } catch (IOException e) {
            throw e;
        }
        userService.updateUser(userIdx, nickname, name, comments, contents, filePath);
        return ResponseEntity.ok().body("{}");
    }
}