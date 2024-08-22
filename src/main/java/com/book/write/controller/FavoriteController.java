package com.book.write.controller;

import com.book.write.dto.SessionUser;
import com.book.write.entity.Favorite;
import com.book.write.entity.Member;
import com.book.write.entity.WriteInfo;
import com.book.write.service.FavoriteService;
import com.book.write.service.MemberService;
import com.book.write.service.WriteInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private  final WriteInfoService writeInfoService;
    private  final FavoriteService favoriteService;
    private final MemberService memberService;


    private final HttpSession httpSession;


    private String getEmailFromPrincipalOrSession(Principal principal) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            return user.getEmail();
        }
        return principal.getName();
    }


    @PostMapping(value = "/favorite/{InfoId}")
    public @ResponseBody ResponseEntity favoritesetting(@PathVariable Long InfoId){
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(InfoId);
        List<Favorite> favorite = favoriteService.searchfromWrteInfoId(writeInfo.getId());
        int count= 0;
        if (!favorite.isEmpty()){
            count = favorite.size();
        }



       return new ResponseEntity(count, HttpStatus.OK) ;
    }

    @PostMapping(value = "/favorite/plus/{InfoId}")
    public @ResponseBody ResponseEntity favoritesetting(@PathVariable Long InfoId,

                                                        Principal principal){

        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(InfoId);
        String LoginId = getEmailFromPrincipalOrSession(principal);
        Member member = memberService.memberLoginId(LoginId);
       favoriteService.saveFav(member, writeInfo);
        List<Favorite> favorite = favoriteService.searchfromWrteInfoId(writeInfo.getId());
        int count= 0;
        if (!favorite.isEmpty()){
            count = favorite.size();
        }

        return new ResponseEntity(count, HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/favorite/minus/{InfoId}")
    public @ResponseBody ResponseEntity favoriteDelete(@PathVariable Long InfoId,
                                                        Principal principal){

        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(InfoId);
        String LoginId = getEmailFromPrincipalOrSession(principal);
        Member member = memberService.memberLoginId(LoginId);

        favoriteService.DeleteFav(member, writeInfo);
        List<Favorite> favorite = favoriteService.searchfromWrteInfoId(writeInfo.getId());
        int count= 0;
        if (!favorite.isEmpty()){
            count = favorite.size();
        }

        return new ResponseEntity(count, HttpStatus.OK) ;
    }

    @GetMapping(value = {"/favorite/list", "/favorite/list/{page}"})
    public String favoriteList(Principal principal, Model model,
                               @PathVariable("page") Optional<Integer> page){
        String loginId = getEmailFromPrincipalOrSession(principal);
        Member member = memberService.memberLoginId(loginId);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Favorite> favoriteList = favoriteService.searchfromMember(member.getId(), pageable);


        model.addAttribute("favoriteList", favoriteList);
        model.addAttribute("pageable", pageable);
        model.addAttribute("maxPage", 10);

        return "favorite/List";
    }


}
