package com.book.write.controller;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.WriteInfo;
import com.book.write.service.WriteDetailService;
import com.book.write.service.WriteInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WriteDetailController {
    private  final WriteInfoService writeInfoService;
    private  final WriteDetailService writeDetailService;

    @GetMapping(value = "/detail/new/{id}")
    public String WriteDetailNew(@PathVariable Long id, Model model){
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);
        WriteDetailDto writeDetailDto = new WriteDetailDto();
        writeDetailDto.setWriteInfo(writeInfo);

        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("WriteDetailDto", writeDetailDto);
        return "writeDetail/new";
    }

    @PostMapping(value = "/novel/new/add")
    public  String WriteDetailNewAdd(@Valid WriteDetailDto writeDetailDto){
        writeDetailService.saveWrite(writeDetailDto);
        return "write/Novel";
    }
}
