package com.evil.scheme.Quip.views;

import com.evil.scheme.Quip.entities.accounts.Account;
import com.evil.scheme.Quip.entities.posts.Post;
import com.evil.scheme.Quip.entities.profiles.Profile;
import com.evil.scheme.Quip.exceptions.AuthException;
import com.evil.scheme.Quip.repositories.AccountRepository;
import com.evil.scheme.Quip.repositories.ProfileRepository;
import com.evil.scheme.Quip.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.evil.scheme.Quip.services.ProfileServiceImpl;
import com.evil.scheme.Quip.exceptions.ProfileNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "profile")
public class ProfileView {
    @Resource
    private AccountRepository accountRepository;

    @Resource
    private ProfileRepository profileRepository;

    @Resource
    private ProfileServiceImpl profileService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Profile getProfileByUsername(@PathVariable String username) throws AuthException {
        Profile p = this.profileRepository.findByUser(username);
        if (p != null)
            return p;
        else
            throw new AuthException("User not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Profile getProfile(@RequestHeader("Authorization") String token) throws AuthException {
        Account account = this.accountRepository
                .findByUsername(this.tokenProvider.getUsername(refactorToken(token)));
        Profile profile = this.profileRepository.findByUser(this.tokenProvider.getUsername(refactorToken(token)));
        if (profile != null)
            return profile;
        else
            throw new AuthException("User not found", HttpStatus.NOT_FOUND);       
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Profile updateProfile(@RequestHeader("Authorization") String token, @RequestBody Profile val) throws ProfileNotFoundException {
        Account account = this.accountRepository
                .findByUsername(this.tokenProvider.getUsername(refactorToken(token)));
        System.out.println(val);
        Profile profile = this.profileRepository.findByUser(this.tokenProvider.getUsername(refactorToken(token)));
        return this.profileService.update(val);
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public List<Post> getFreed(@RequestHeader("Authorization") String token) {
        Account account = this.accountRepository
                .findByUsername(this.tokenProvider.getUsername(refactorToken(token)));
        Profile profile = this.profileRepository.findByUser(this.tokenProvider.getUsername(refactorToken(token)));
        List<Post> posts = profile.getPosts();
        profile.getFriends().forEach(x -> {
            this.profileRepository.findByUser(x.getUsername()).getPosts()
                    .forEach(y -> {
                        posts.add(y);
                    });
        });
        return posts;
    }

    @RequestMapping(value = "/addFriend/{username}", method = RequestMethod.GET)
    public Profile addFriend(@RequestHeader("Authorization") String token, @PathVariable String username) throws ProfileNotFoundException{
        Account account = this.accountRepository
                .findByUsername(this.tokenProvider.getUsername(refactorToken(token)));
        Profile profile = this.profileRepository.findByUser(this.tokenProvider.getUsername(refactorToken(token)));
        Account friend = this.accountRepository.findByUsername(username);
        profile.getFriends().add(friend);
        return profileService.update(profile);
    }

    @RequestMapping(value = "/unFriend/{username}", method = RequestMethod.GET)
    public Profile unFriend(@RequestHeader("Authorization") String token, @PathVariable String username) throws ProfileNotFoundException{
        Account account = this.accountRepository
                .findByUsername(this.tokenProvider.getUsername(refactorToken(token)));
        Profile profile = this.profileRepository.findByUser(this.tokenProvider.getUsername(refactorToken(token)));
        Account friend = this.accountRepository.findByUsername(username);
        profile.getFriends().remove(friend);
        return profileService.update(profile);
    }

    public static String refactorToken (String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        } else if (bearerToken != null) {
            return bearerToken;
        }
        return null;
    }
}

