package com.evil.scheme.Quip.views;

import com.evil.scheme.Quip.entities.posts.Post;
import com.evil.scheme.Quip.entities.profiles.Profile;
import com.evil.scheme.Quip.exceptions.PostNotFoundException;
import com.evil.scheme.Quip.exceptions.ProfileNotFoundException;
import com.evil.scheme.Quip.forms.PostForm;
import com.evil.scheme.Quip.services.PostServiceImpl;
import com.evil.scheme.Quip.services.ProfileServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "posts")
public class PostView {
    @Resource
    PostServiceImpl postService;
    @Resource
    ProfileServiceImpl profileService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Post add(@ModelAttribute PostForm post) throws ProfileNotFoundException {
        Profile profile = this.profileService.findById(post.getProfileId());
        Post retVal = this.postService.create(new Post(post.getTitle(), post.getDescription(), post.getMediaUrl()));
        profile.getPosts().add(retVal);
        this.profileService.update(profile);
        return retVal;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Post update(@ModelAttribute Post post) throws PostNotFoundException {
        return this.postService.update(post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Long id) throws PostNotFoundException {
        return this.postService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getById(@PathVariable Long id) {
        return this.postService.findById(id);
    }
}
