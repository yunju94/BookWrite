package com.book.write.service;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.Purchase;
import com.book.write.entity.Rental;
import com.book.write.entity.WriteDetail;
import com.book.write.repository.PurchanseRepository;
import com.book.write.repository.RentalRepository;
import com.book.write.repository.WriteDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteDetailService {
    private final WriteDetailRepository writeDetailRepository;
    private  final PurchanseRepository purchanseRepository;
    private  final RentalRepository rentalRepository;
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

    public Boolean    searchRen(WriteDetail writeDetail){
        Rental rental = rentalRepository.findByWriteDetailId(writeDetail.getId());
        LocalDateTime now = LocalDateTime.now();
        if (rental == null){
            return false;
        } else if (rental.getEndTime().isBefore(now)) {
            return false;
        } else {
            return true;
        }
    }
}
