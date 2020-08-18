package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.UserAddRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
