package com.book.write.controller;

import com.book.write.constant.Category;
import com.book.write.dto.*;
import com.book.write.entity.*;
import com.book.write.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.book.write.constant.Role.ADMIN;
import static com.book.write.constant.Role.BLACK;

@Controller
@RequiredArgsConstructor
public class WriteController {

    private final MemberService memberService;
    private  final WriteInfoService writeInfoService;
    private final WriteDetailService writeDetailService;
    private  final PurchanseService purchanseService;
    private  final BoardService boardService;
    private  final  FavoriteService favoriteService;

    private final HttpSession httpSession;

    private String getEmailFromPrincipalOrSession(Principal principal) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            return user.getEmail();
        }
        return principal.getName();
    }


    @GetMapping(value = {"/write", "/write/{page}"})
    public String writePage(Model model, Principal principal,
                            @PathVariable("page") Optional<Integer> page,
                            WriteInfoSerchDto writeInfoSerchDto){
        if (principal == null){
            return "member/login";
        }


        String Id =  getEmailFromPrincipalOrSession(principal);
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
        String getName=getEmailFromPrincipalOrSession(principal);
        Member member = memberService.SearchIdtoName(getName);
        if (member.getRole()==BLACK){
            return "write/error";
        }
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
    public  String updateComplete(@Valid WriteInfoDto writeInfoDto, BindingResult bindingResult,
                                  @RequestParam("ImgFile") MultipartFile imgFile, Model model) throws Exception {
        if (bindingResult.hasErrors()){
            return "write/InfoForm";
        }
        if (imgFile.isEmpty() && writeInfoDto.getId() ==null){
            model.addAttribute("errorMessage", "상품 이미지는 필수 입력 값입니다.");
            return  "write/InfoForm";
        }
        try{
            writeInfoService.updateWriteInfoFromDto(writeInfoDto,imgFile);
            return "redirect:/";

        }catch (Exception e){
            model.addAttribute("errorMessage", " 수정 중 에러가 발생했습니다");
            return  "write/InfoForm";

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


    @DeleteMapping(value = "/write/InfoForm/Delete/{id}")
    public ResponseEntity<String> deleteInfo(@PathVariable Long id) {
        try {
            writeInfoService.deleteWriteDetail(id);
            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("삭제 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = {"/novel/{category}", "/novel/{category}/{search}",
            "/novel/{category}/{orderByFront}/{orderByBack}",
            "/novel/{category}/{search}/{page}"})
    public String NovelPage( @PathVariable("category") Category category,
                             @PathVariable("search") Optional<String> search,
                             @PathVariable("orderByFront") Optional<String> orderByFront,
                             @PathVariable("orderByBack") Optional<String> orderByBack,
                             @PathVariable("page") Optional<Integer> page, Model model){


        WriteInfoDto writeInfoDto = new WriteInfoDto();
        writeInfoDto.setCategory(category);
        search.ifPresent(writeInfoDto::setSearch);//search가 비어있지 않으면 dto에 값을 넣는다.
        orderByFront.ifPresent(writeInfoDto::setOrderByFront);
        orderByBack.ifPresent(writeInfoDto::setOrderByBack);

        Pageable pageable = PageRequest.of(page.orElse(0), 20);
        Page<NovelListDto> items = writeInfoService.getCategoryPage(writeInfoDto, pageable, orderByFront, orderByBack);

        model.addAttribute("items", items);


        model.addAttribute("writeInfoDto", writeInfoDto);
        model.addAttribute("pageable", pageable);
        model.addAttribute("maxPage", 20);
        return "write/Novel";
    }

    @PostMapping(value = "/novel/scrolling")
    public  @ResponseBody ResponseEntity endNotScrolling(@RequestParam("category") Category category,
                                                         @RequestParam("search") Optional<String> search,
                                                         @RequestParam("orderByFront") Optional<String> orderByFront,
                                                         @RequestParam("orderByBack") Optional<String> orderByBack,
                                                         @RequestParam("page") Optional<Integer> page, Model model){
        WriteInfoDto writeInfoDto = new WriteInfoDto();
        writeInfoDto.setCategory(category);
        search.ifPresent(writeInfoDto::setSearch);//search가 비어있지 않으면 dto에 값을 넣는다.


        Pageable pageable = PageRequest.of(page.orElse(0), 20);
        Page<NovelListDto> items = writeInfoService.getCategoryPage(writeInfoDto, pageable, orderByFront, orderByBack);

        return ResponseEntity.ok(items);
    }

    @GetMapping(value = "/novel/best")
    public String NovelPage(Optional<Integer> page, Model model){

        WriteInfoDto writeInfoDto = new WriteInfoDto();

        Pageable pageable = PageRequest.of(page.orElse(0), 100);
        Page<NovelListDto> items = writeInfoService.getBestPage(writeInfoDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("writeInfoDto", writeInfoDto);
        model.addAttribute("pageable", pageable);
        model.addAttribute("maxPage", 100);
        return "write/Best";
    }



    @GetMapping(value = "/writeNovel/{id}")
    public  String WriteInfoDetail(@PathVariable Long id, Model model, Principal principal){

        if (principal==null){
            return "member/login";
        }
        String email = getEmailFromPrincipalOrSession(principal);

        Member member = memberService.memberLoginId(email);

        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);

        List<WriteDetail> writeDetailList = writeDetailService.searchListDetail(writeInfo.getId());
        List<Integer> count = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i< writeDetailList.size(); i++){
            count.add(counter);
            counter++;
        }
        int retry= 0;
        List<Favorite> favorite = favoriteService.searchfromWrteInfoId(writeInfo.getId());
        for (Favorite fav : favorite){
            if (Objects.equals(member.getId(), fav.getMember().getId())){
                retry=1;
            }
        }



        model.addAttribute("member", member);
        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("writeDetailList", writeDetailList);
        model.addAttribute("count", count);
        model.addAttribute("retry", retry);


        return "writeDetail/detailNovel";
    }

    @GetMapping(value = "/write/mypage")
    public  String myPage (Principal principal, Model model){
        if (principal == null){
            return "member/login";
        }
        String Id =  getEmailFromPrincipalOrSession(principal);
        Member member = memberService.SearchIdtoName(Id);
        MemberFormDto memberFormDto = memberService.of(member);

        model.addAttribute("member", member);
        model.addAttribute("memberFormDto", memberFormDto);


        return "member/mypage";
    }


    @GetMapping (value = "/write/board/{str}")
    public  String board(@PathVariable String str, Principal principal, Model model){
        String Id =  getEmailFromPrincipalOrSession(principal);
        Member member = memberService.SearchIdtoName(Id);
        if (str.equals("admin")){
            List<Board> boardList = boardService.findByAll();
            model.addAttribute("boardList", boardList);

            return "write/Board_Admin";
        }
       BoardDto boardDto = new BoardDto();
        boardDto.setMember(member);
        model.addAttribute("boardDto",boardDto);

        return  "write/boardPop";

    }

    @PostMapping(value = "/write/boardPop")
    public  String boardPopSave(@Valid BoardDto boardDto){
        boardService.saveBoardDto(boardDto);
        return "redirect:/";
    }



}
