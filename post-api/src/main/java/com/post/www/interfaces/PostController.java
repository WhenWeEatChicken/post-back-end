package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.domain.Post;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
        Page<PostResponseDto> posts = postService.getPosts(pageable);
        return posts;
    }

    @ApiOperation(value = "게시글 상세 조회", notes = "해당 게시글의 모든 정보를 조회합니다.")
    @GetMapping("/posts/{idx}")
    public PostResponseDto detail(@PathVariable("idx") Long idx) {
        PostResponseDto responseDto = postService.getPost(idx);
        return responseDto;
    }

    @ApiOperation(value = "게시글 등록", notes = "새로운 게시글을 등록합니다.")
    @PostMapping("/posts")
    public ResponseEntity<?> create(
            @Valid @RequestBody PostRequestDto resource
    ) throws URISyntaxException {
        Post post = postService.addPost(resource);

        URI location = new URI("/posts/" + post.getIdx());
        return ResponseEntity.created(location).body("{}");
    }

    @ApiOperation(value = "게시글 수정", notes = "해당 게시글을 수정합니다.")
    @PatchMapping("/posts/{idx}")
    public String update(
            @PathVariable("idx") Long idx,
            @RequestBody PostRequestDto resource
    ) {

        postService.updatePost(idx, resource);
        return "{}";
    }

}
