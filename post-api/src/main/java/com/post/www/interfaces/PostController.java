package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.domain.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Api(tags = {"1.Post"})
@CrossOrigin
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "전체 게시글 조회", notes = "모든 게시글을 조회합니다.")
    @GetMapping("/posts")
    public List<Post> list() {
        List<Post> posts = postService.getPosts();
        return posts;
    }

    @ApiOperation(value = "게시글 상세 조회", notes = "해당 게시글의 모든 정보를 조회합니다.")
    @GetMapping("/posts/{idx}")
    public Post detail(@PathVariable("idx") Long idx) {
        Post post = postService.getPost(idx);
        return post;
    }

    @ApiOperation(value = "게시글 등록", notes = "새로운 게시글을 등록합니다.")
    @PostMapping("/posts")
    public ResponseEntity<?> create(
            @Valid @RequestBody Post resource
    ) throws URISyntaxException {
        Post post = postService.addPost(
                Post.builder()
                        .user_idx(resource.getUser_idx())
                        .title(resource.getTitle())
                        .contents(resource.getContents())
                        .publishdate(resource.getPublishdate())
                        .build()
        );

        URI location = new URI("/posts/" + post.getIdx());
        return ResponseEntity.created(location).body("{}");
    }

    @ApiOperation(value = "게시글 수정", notes = "해당 게시글을 수정합니다.")
    @PatchMapping("/posts/{idx}")
    public String update(
            @PathVariable("idx") Long idx,
            @RequestBody Post resource
    ) {
        Long user_idx = resource.getUser_idx();
        String title = resource.getTitle();
        String contents = resource.getContents();
        String publisdate = resource.getPublishdate();

        postService.updatePost(idx, user_idx, title, contents, publisdate);
        return "{}";
    }

}
