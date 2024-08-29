package com.book.write.service;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.Purchase;
import com.book.write.entity.Rental;
import com.book.write.entity.WriteDetail;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.PurchanseRepository;
import com.book.write.repository.RentalRepository;
import com.book.write.repository.WriteDetailRepository;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        LocalDateTime now = LocalDateTime.now();
        return writeDetailRepository.findByWriteInfoId(WriteInfoId, now);
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

    public  WriteDetailDto searchDetailDto(Long id){
        WriteDetail writeDetail = writeDetailRepository.findById(id).orElseThrow();
        return WriteDetailDto.of(writeDetail);
    }

    public  void updateWriteDetail(WriteDetailDto writeDetailDto){
        WriteDetail writeDetail = writeDetailRepository.findById(writeDetailDto.getId()).orElseThrow();
        writeDetail.setMiniWrite(writeDetailDto.getMiniWrite());
        writeDetail.setMiniTitle(writeDetailDto.getMiniTitle());
        writeDetail.setUpdateTime(LocalDateTime.now());
        writeDetail.setUpdateDate(LocalDate.now());


    }

    public  void  deleteWriteDetail(Long detailId){
        writeDetailRepository.deleteById(detailId);
    }
}
