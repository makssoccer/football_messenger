package com.grass.football.Blog.servise;
import com.grass.football.Blog.model.Post;
import com.grass.football.Blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class PlayrsServise {
    @Autowired
    private  PostRepository postRepository;


    public  void saveProductToDB(MultipartFile file, String title, String anons, String full_text)
    {
        Post post=new Post();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            post.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTitle(title);
        postRepository.save(post);

    }
    public  void editProductToDB(long id, MultipartFile file, String title, String anons, String full_text)
    {

        Post post= postRepository.findById(id).orElseThrow();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }

        try {
            post.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e)
        {

            e.printStackTrace();
        }

        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTitle(title);
        postRepository.save(post);

    }
}

