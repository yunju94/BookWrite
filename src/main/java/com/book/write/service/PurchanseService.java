package com.book.write.service;

import com.book.write.entity.Purchanse;
import com.book.write.entity.Write;
import com.book.write.repository.PurchanseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchanseService {
    private  final PurchanseRepository purchanseRepository;

    public void savePur(Write write, double KDR_coin, double YES_coin){
        Purchanse purchanse = Purchanse.createSave(write, KDR_coin, YES_coin);
        purchanseRepository.save(purchanse);
    }

}
