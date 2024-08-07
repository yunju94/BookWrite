package com.book.write.service;

import com.book.write.entity.Rental;
import com.book.write.entity.Write;
import com.book.write.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    public  void  save (Write write){
        rentalRepository.save(Rental.create(write));
    }
}
