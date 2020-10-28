package com.post.www.interfaces;

import com.post.www.application.CommentService;
import com.post.www.domain.Comment;
import com.post.www.interfaces.dto.CommentRequestDto;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Api(tags = {"2.CommentController"})
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @PostMapping("/comment")
    public ResponseEntity<?> create(
            @Valid @RequestBody CommentRequestDto requestDto
    ) throws URISyntaxException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);

        Comment comment = commentService.addComment(userIdx, requestDto);
        URI location = new URI("/comment/" + comment.getIdx());
        return ResponseEntity.created(location).body("{}");
    }

    @ApiOperation(value = "댓글 수정", notes = "해당 댓글을 수정합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @PatchMapping("/comment/{idx}")
    public String update(
            @PathVariable("idx") Long idx,
            @RequestBody CommentRequestDto requestDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);

        commentService.updateComment(idx, userIdx, requestDto);
        return "{}";
    }
}
