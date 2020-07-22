package com.post.www.application;

import com.post.www.domain.Post;
import com.post.www.domain.PostNotFoundException;
import com.post.www.domain.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public Post getPost(Long idx) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));
        return post;
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }
}
