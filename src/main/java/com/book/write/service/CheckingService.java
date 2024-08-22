package com.book.write.service;

import com.book.write.entity.Checking;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.repository.CheckingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckingService {
    private  final CheckingRepository checkingRepository;
    private  final PointService pointService;

    public Checking searchfromMember(Long memberId){
        //멤버가 오늘 checking을 가지고 있는가.
        LocalDate now = LocalDate.now();

        Checking checking = checkingRepository.findByMemberIdAndRegDate(memberId, now);
        return checking;
    }
    public  Checking  saveChecking(Member member, int point){
        Point points = pointService.savepoint(member, point);

        Checking checking = Checking.saveChecking(member, points);
        checkingRepository.save(checking);
        return checking;
    }

}
