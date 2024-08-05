package com.book.write.service;

import com.book.write.entity.Purchanse;
import com.book.write.entity.Write;
import com.book.write.repository.PurchanseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchanseService {
    private  final PurchanseRepository purchanseRepository;

    public void savePur(Write write, double KDR_coin, double YES_coin){
        Purchanse purchanse = Purchanse.createSave(write, KDR_coin, YES_coin);
        purchanseRepository.save(purchanse);
    }

    public List<Optional<Purchanse>> seachwriteList (List<Write> writeList){
        List<Optional<Purchanse>> purchanseList = new ArrayList<>();
        for (int i = 0 ; i< writeList.size() ; i++){
            purchanseList.add(purchanseRepository.findById(writeList.get(i).getId()));

        }

        return purchanseList;
    }

}
