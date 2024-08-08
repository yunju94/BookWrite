package com.book.write.service;

import com.book.write.entity.Rental;
import com.book.write.entity.WriteDetail;
import com.book.write.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    public  void  save (WriteDetail writeDetail){
        rentalRepository.save(Rental.create(writeDetail));
    }
}
