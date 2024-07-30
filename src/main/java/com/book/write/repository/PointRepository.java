package com.book.write.repository;

import com.book.write.entity.Point;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

//포트원
    @Query("select o from Point o" + //order와
            " left join fetch o.payment p" + //payment 조인. order를 따른다.
            " left join fetch o.member m" + //멤버와 조인, order를 따른다.
            " where o.id = :id") //order에 있는 번호 순으로 나열한다.
    Point findOrderAndPaymentAndMember(Long id);

    @Query("select o from Point o" +
            " left join fetch o.payment p" +
            " where o.orderUid = :orderUid")
    Point findOrderAndPayment(String orderUid);

    Point findByOrderUid(String orderUid);

    List<Point> findByMemberId(Long memberId);
}
