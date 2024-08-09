package com.book.write.service;

import com.book.write.entity.Kis_KDR;
import com.book.write.entity.Kis_YES;
import com.book.write.repository.Kis_KDRRepository;
import com.book.write.repository.Kis_YESRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KisService {
    private  final Kis_KDRRepository kdrRepository;
    private  final Kis_YESRepository yesRepository;

    public  void  saveKDR(Object stckPrpr, LocalDate now){
        Integer  stc = Integer.parseInt((String) stckPrpr);
        Kis_KDR kdr = Kis_KDR.createKDR(stc, now);
        kdrRepository.save(kdr);

    }
    public  void  saveYES(Object stckPrpr, LocalDate now){
        Integer  stc = Integer.parseInt((String) stckPrpr);
        Kis_YES yes = Kis_YES.createYES(stc, now);
        yesRepository.save(yes);
    }


    public  List<Integer> getKDR(){
        List<Integer> KDR = new ArrayList<>();
        LocalDate date = LocalDate.now();
        List<Kis_KDR> str;

        for (int i = 6; i >=0; i--) {
            LocalDate currentDate = date.minusDays(i);
            Integer value = 0;
            str = kdrRepository.findByUpDate(currentDate);
            if (!str.isEmpty()){
                value = str.getLast().getWon();
            }
            KDR.add(value);
        }
        return KDR;
    }

    public  List<Integer> getYES(){
        List<Integer> YES = new ArrayList<>();
        LocalDate date = LocalDate.now();
        List<Kis_YES> str;

        for (int i = 6; i >=0; i--) {
            LocalDate currentDate = date.minusDays(i);
            Integer value = 0;
            str = yesRepository.findByUpDate(currentDate);
            if (!str.isEmpty()){
                value = str.getLast().getWon();
            }
            YES.add(value);
        }
        return YES;
    }





}
