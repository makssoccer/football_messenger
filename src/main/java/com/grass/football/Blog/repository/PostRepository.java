package com.grass.football.Blog.repository;

import com.grass.football.Blog.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Long> {
}
