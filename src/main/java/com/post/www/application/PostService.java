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
    public PostService(PostRepository postRepository) {
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

    @Transactional
    public Post updatePost(Long idx, Long user_idx, String title, String contents, String publisdate) {
        Post post = postRepository.findById(idx).orElse(null);
        if(post != null){
            if(user_idx != null) post.setUser_idx(user_idx);
            if(title != null && !title.isEmpty()) post.setTitle(title);
            if(contents != null && !contents.isEmpty()) post.setContents(contents);
            if(publisdate != null) post.setPublishdate(publisdate);
        }
        return post;
    }
}
