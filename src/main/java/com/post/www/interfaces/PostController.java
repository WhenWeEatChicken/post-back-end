package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/posts")
    public List<Post> list() {
        List<Post> posts = postService.getPosts();
        return posts;
    }

    @GetMapping("/posts/{idx}")
    public Post detail(@PathVariable("idx") Long idx) {
        Post post = postService.getPost(idx);
        return post;
    }

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
