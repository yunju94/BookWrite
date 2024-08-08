package com.book.write.service;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.Purchase;
import com.book.write.entity.WriteDetail;
import com.book.write.repository.PurchanseRepository;
import com.book.write.repository.WriteDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteDetailService {
    private final WriteDetailRepository writeDetailRepository;
    private  final PurchanseRepository purchanseRepository;
    public WriteDetail saveWrite(WriteDetailDto writeDetailDto){

        WriteDetail writeDetail = WriteDetail.createWrite(writeDetailDto);

        writeDetailRepository.save(writeDetail);
        return writeDetail;
    }

    public List<WriteDetail> searchListDetail(Long WriteInfoId){
        return writeDetailRepository.findByWriteInfoId(WriteInfoId);
    }

    @Transactional(readOnly = true)
    public WriteDetail searchDetailId(Long DetailId){
        return  writeDetailRepository.findById(DetailId).orElseThrow();
    }


    public Boolean    searchPur(WriteDetail writeDetail){
        Purchase purchase = purchanseRepository.findByWriteDetailId(writeDetail.getId());

        if (purchase == null){
            return false;
        }else {
            return true;
        }
    }
}
