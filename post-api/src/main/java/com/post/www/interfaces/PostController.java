package com.post.www.interfaces;

import com.post.www.application.FileService;
import com.post.www.application.PostService;
import com.post.www.config.enums.PostStatus;
import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.interfaces.dto.PostListResponseDto;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostDetailResponseDto;
import com.post.www.interfaces.dto.PostSearchRequestDto;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

@Slf4j
@Api(tags = {"1.PostController"})
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @Value("${temp.path}")
    private String tempPath;

    @ApiOperation(value = "전체 게시글 조회", notes = "모든 게시글을 조회합니다.")
    @GetMapping("/posts")
    public Page<PostListResponseDto> list(
            @PageableDefault(sort = "idx", direction = Sort.Direction.DESC, size = 3) Pageable pageable,
            @ModelAttribute PostSearchRequestDto requestDto
    ) {
        return postService.getPosts(requestDto, pageable);
    }

    @ApiOperation(value = "게시글 상세 조회", notes = "해당 게시글의 모든 정보를 조회합니다.")
    @GetMapping("/posts/{idx}")
    public PostDetailResponseDto detail(@PathVariable("idx") Long idx) {
        return postService.getPost(idx);
    }

    @ApiOperation(value = "게시글 등록", notes = "새로운 게시글을 등록합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
    )
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @Valid @ModelAttribute PostRequestDto requestDto
    ) throws URISyntaxException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Claims claims = (Claims) authentication.getPrincipal();
        Long userIdx = claims.get("userId", Long.class);
        PostType type = requestDto.getType();
        String contents = requestDto.getContents();
        String title = requestDto.getTitle();
        PostStatus status = requestDto.getStatus();
        LocalDate localDate = LocalDate.now();
        String filePath = "";
        String fileName = "";

        Post post = postService.addPost(userIdx, title, contents, status, type);

        try {
            if (requestDto.getFile() != null) {
                filePath = tempPath + localDate + UUID.randomUUID().toString().replace("-", "");
                fileName = requestDto.getFile().getOriginalFilename();
                requestDto.getFile().transferTo(new File(filePath));
                fileService.addFile(post, "post", filePath, fileName);
            }
        } catch (IOException e) {
            throw e;
        }


        URI location = new URI("/posts/" + post.getIdx());
        return ResponseEntity.created(location).header("Access-Control-Allow-Origin","*").body("{}");
    }

    @ApiOperation(value = "게시글 수정", notes = "해당 게시글을 수정합니다.")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String", paramType = "header")
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
