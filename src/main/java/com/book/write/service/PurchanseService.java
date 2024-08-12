package com.book.write.service;

import com.book.write.entity.*;
import com.book.write.repository.PurchanseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchanseService {
    private  final PurchanseRepository purchanseRepository;
    public  void savePur(WriteDetail writeDetail){
        Purchase purchase = new Purchase();
        purchase.setWriteDetail(writeDetail);
        purchanseRepository.save(purchase);



    }

    public List<Purchase> seachwriteList (List<WriteDetail> writeDetailList){
        List<Purchase> purchaseList = new ArrayList<>();
        for (int i = 0; i< writeDetailList.size() ; i++){
            purchaseList.add(purchanseRepository.findByWriteDetailId(writeDetailList.get(i).getId()));
        }
        return purchaseList;
    }

}
