package com.grass.football.Blog.controllers;

import com.grass.football.Blog.model.Post;
import com.grass.football.Blog.repository.PostRepository;
import com.grass.football.Blog.servise.PlayrsServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PlayrsServise playrsServise;


    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    /*@PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam("file") MultipartFile file, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post= new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }*/

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("anons") String anons, @RequestParam("full_text") String full_text, Model model){
        playrsServise.saveProductToDB(file,title,anons,full_text);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value="id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res= new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-details";
    }
    @GetMapping("/blog/{id}edit")
    public String blogEdit(@PathVariable(value="id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res= new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-edit";
    }
    @PostMapping("/blog/{id}edit")
    public String blogPostUbdate(@RequestParam("file") MultipartFile file,@PathVariable(value="id") long id, @RequestParam String title,@RequestParam String anons,@RequestParam String full_text, Model model) {
        playrsServise.editProductToDB(id,file,title,anons,full_text);

        return "redirect:/blog";
    }

    /*@PostMapping("/blog/{id}edit")
    public String blogPostUbdate(@RequestParam("file") MultipartFile file,@PathVariable(value="id") long id, @RequestParam String title,@RequestParam String anons,@RequestParam String full_text, Model model) {
        Post post= postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }*/
    @PostMapping("/blog/{id}remove")
    public String blogPostDelete(@PathVariable(value="id") long id,Model model) {
        Post post= postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
