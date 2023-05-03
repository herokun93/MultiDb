package com.multi.demo.repository.social;

import com.multi.demo.entity.social.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
