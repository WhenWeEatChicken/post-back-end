package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostResponseDto;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Api(tags = {"1.PostController"})
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "전체 게시글 조회", notes = "모든 게시글을 조회합니다.")
    @GetMapping("/posts")
    public Page<PostResponseDto> list(
            @PageableDefault(sort = "idx", direction = Sort.Direction.DESC, size = 3) Pageable pageable
    ) {
        return postService.getPosts(pageable);
    }

    @ApiOperation(value = "게시글 상세 조회", notes = "해당 게시글의 모든 정보를 조회합니다.")
    @GetMapping("/posts/{idx}")
    public PostResponseDto detail(@PathVariable("idx") Long idx) {
        return postService.getPost(idx);
    }

    @ApiOperation(value = "게시글 등록", notes = "새로운 게시글을 등록합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization" , value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @PostMapping("/posts")
    public ResponseEntity<?> create(
            @Valid @RequestBody PostRequestDto resource
    ) throws URISyntaxException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);
        PostType type = resource.getType();
        String contents = resource.getContents();
        String title = resource.getTitle();
        String publishDate = resource.getPublishDate();
        Post post = postService.addPost(userIdx, title, contents, publishDate, type);

        URI location = new URI("/posts/" + post.getIdx());
        return ResponseEntity.created(location).body("{}");
    }

    @ApiOperation(value = "게시글 수정", notes = "해당 게시글을 수정합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization" , value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @PatchMapping("/posts/{idx}")
    public String update(
            @PathVariable("idx") Long idx,
            @RequestBody PostRequestDto resource
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        postService.updatePost(idx, resource);
        return "{}";
    }

}
