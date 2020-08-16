package com.post.www.application;

import com.post.www.domain.Comment;
import com.post.www.domain.CommentRepository;
import com.post.www.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments(Post post){
        List<Comment> comments = commentRepository.findAllByPost(post);;
        return comments;
    }

}
