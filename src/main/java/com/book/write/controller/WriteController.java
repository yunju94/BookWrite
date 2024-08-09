package com.book.write.controller;

import com.book.write.constant.Category;
import com.book.write.dto.NovelListDto;
import com.book.write.dto.WriteInfoDto;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.Member;
import com.book.write.entity.WriteDetail;
import com.book.write.entity.WriteInfo;
import com.book.write.service.MemberService;
import com.book.write.service.PurchanseService;
import com.book.write.service.WriteDetailService;
import com.book.write.service.WriteInfoService;
import jakarta.validation.Valid;
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
    private final WriteDetailService writeDetailService;
    private  final PurchanseService purchanseService;
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

        model.addAttribute("member", member);
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

    @GetMapping(value = "/write/InfoForm/update/{Id}")
    public String writeInfoFormUpdate(Model model,
                                      @PathVariable Long Id){


        WriteInfoDto writeInfoDto = writeInfoService.searchInfo(Id);

        model.addAttribute("writeInfoDto", writeInfoDto);

        return "write/InfoForm";
    }

    @PostMapping(value = "/write/InfoForm/update/complete")
    public  String updateComplete(@Valid WriteInfoDto writeInfoDto,
                                  @RequestParam("ImgFile") MultipartFile imgFile) throws Exception {

        if (!imgFile.isEmpty()){

            writeInfoService.updateWriteInfoFromDto(writeInfoDto,imgFile);
            return "redirect:/";

        }else {
            writeInfoService.updaInfoFromDto(writeInfoDto);

            return "redirect:/";
        }


    }

    @PostMapping(value = "/write/InfoForm")
    public String writeInfoFormPost(@Valid WriteInfoDto writeInfoDto,
                                    @RequestParam("ImgFile") MultipartFile imgFile,
                                    Model model) throws Exception {

        if (imgFile.isEmpty()){
            model.addAttribute("errorMessage","에러");
            return "write/InfoForm";

        }

        writeInfoService.save(writeInfoDto, imgFile);

        return "redirect:/";
    }



    @GetMapping(value = "/novel/{category}")
    public String NovelPage(@PathVariable Category category,
                            Optional<Integer> page, Model model){

        WriteInfoDto writeInfoDto = new WriteInfoDto();
        writeInfoDto.setCategory(category);

        Pageable pageable = PageRequest.of(page.orElse(0), 20);
        Page<NovelListDto> items = writeInfoService.getCategoryPage(writeInfoDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("writeInfoDto", writeInfoDto);
        model.addAttribute("pageable", pageable);
        model.addAttribute("maxPage", 20);
        return "write/Novel";
    }

    @GetMapping(value = "/novel/best")
    public String NovelPage(Optional<Integer> page, Model model){

        WriteInfoDto writeInfoDto = new WriteInfoDto();

        Pageable pageable = PageRequest.of(page.orElse(0), 20);
        Page<NovelListDto> items = writeInfoService.getBestPage(writeInfoDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("writeInfoDto", writeInfoDto);
        model.addAttribute("pageable", pageable);
        model.addAttribute("maxPage", 20);
        return "write/Best";
    }



    @GetMapping(value = "/writeNovel/{id}")
    public  String WriteInfoDetail(@PathVariable Long id, Model model, Principal principal){

        Member member = memberService.memberLoginId(principal.getName());

        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);

        List<WriteDetail> writeDetailList = writeDetailService.searchListDetail(writeInfo.getId());
        List<Integer> count = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i< writeDetailList.size(); i++){
            count.add(counter);
            counter++;
        }

        model.addAttribute("member", member);
        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("writeDetailList", writeDetailList);
        model.addAttribute("count", count);

        return "writeDetail/detailNovel";
    }




}
