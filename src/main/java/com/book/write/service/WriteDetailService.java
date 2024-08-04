package com.book.write.service;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.Write;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.WriteDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteDetailService {
    private final WriteDetailRepository writeDetailRepository;
    public void saveWrite(WriteDetailDto writeDetailDto){
        Write write = Write.createWrite(writeDetailDto);

        writeDetailRepository.save(write);

    }

}
