package com.book.write.service;

import com.book.write.entity.Purchase;
import com.book.write.entity.Write;
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

    public List<Purchase> seachwriteList (List<Write> writeList){
        List<Purchase> purchaseList = new ArrayList<>();
        for (int i = 0 ; i< writeList.size() ; i++){
            purchaseList.add(purchanseRepository.findByWriteId(writeList.get(i).getId()));
        }
        return purchaseList;
    }

}
