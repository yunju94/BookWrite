package com.book.write.controller;

import com.book.write.constant.Category;
import com.book.write.dto.WriteInfoDto;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.Member;
import com.book.write.entity.WriteInfo;
import com.book.write.service.MemberService;
import com.book.write.service.WriteInfoService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WriteController {

    private final MemberService memberService;
    private  final WriteInfoService writeInfoService;

    @GetMapping(value = {"/write", "/write/{page}"})
    public String writePage(Model model, Principal principal,
                            @PathVariable("page") Optional<Integer> page,
                            WriteInfoSerchDto writeInfoSerchDto){
        if (principal == null){
            return "member/login";
        }

        String Id = principal.getName();
        Member member = memberService.SearchIdtoName(Id);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<WriteInfo> writeInfo = writeInfoService.getMyWritePage(writeInfoSerchDto, member.getId(), pageable);

        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("writeInfoSerchDto", writeInfoSerchDto);
        model.addAttribute("maxPage", 5);


        return "write/list";
    }

    @GetMapping(value = "/write/InfoForm")
    public String writeInfoForm(Model model, Principal principal){

        Member member = memberService.SearchIdtoName(principal.getName());
        WriteInfoDto writeInfoDto = new WriteInfoDto();
        writeInfoDto.setMember(member);

        model.addAttribute("writeInfoDto", writeInfoDto);
        return "write/InfoForm";
    }

    @PostMapping(value = "/write/InfoForm")
    public String writeInfoFormPost(@Valid WriteInfoDto writeInfoDto,
                                    @RequestParam("ImgFile") MultipartFile imgFile) throws Exception {

        writeInfoService.save(writeInfoDto, imgFile);

        return "redirect:/";
    }

    @GetMapping(value = "/fantasy")
    public String fantasyPage(Model model){
        List<WriteInfo> writeInfo=writeInfoService.AllSearch();
        List<WriteInfo> F = new ArrayList<>();

        for (int i = 0 ; i < writeInfo.size() ; i++){
            if (writeInfo.get(i).getCategory().equals(Category.Fantasy)){
                F.add(writeInfo.get(i));
            }
        }
        model.addAttribute("Fantasy", F);
        return "write/fantasy";
    }
    @GetMapping(value = "/fantasyRomance")
    public String fantasyRomancePage(Model model){

        List<WriteInfo> writeInfo=writeInfoService.AllSearch();
        List<WriteInfo> RF = new ArrayList<>();

        for (int i = 0 ; i < writeInfo.size() ; i++){
            if (writeInfo.get(i).getCategory().equals(Category.RomanceFantasy)){
                RF.add(writeInfo.get(i));
            }
        }
        model.addAttribute("RomanceFantasy", RF);


        return "write/fantasyRomance";
    }

    @GetMapping(value = "/romance")
    public String romancePage(Model model){

        List<WriteInfo> writeInfo=writeInfoService.AllSearch();
        List<WriteInfo> R = new ArrayList<>();

        for (int i = 0 ; i < writeInfo.size() ; i++){
            if (writeInfo.get(i).getCategory().equals(Category.Romance)){
                R.add(writeInfo.get(i));
            }
        }
        model.addAttribute("Romance", R);


        return "write/romance";
    }

    @GetMapping(value = "/etc")
    public String etcPage(Model model){

        List<WriteInfo> writeInfo=writeInfoService.AllSearch();
        List<WriteInfo> etc = new ArrayList<>();

        for (int i = 0 ; i < writeInfo.size() ; i++){
            if (writeInfo.get(i).getCategory().equals(Category.etc)){
                etc.add(writeInfo.get(i));
            }
        }
        model.addAttribute("etc", etc);


        return "write/etc";
    }


    @GetMapping(value = "/writeNovel/{id}")
    public  String WriteInfoDetail(@PathVariable Long id, Model model){
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);
        System.out.println("writeInfo: "+writeInfo);

        model.addAttribute("writeInfo", writeInfo);
        return "writeDetail/detailNovel";
    }




}
