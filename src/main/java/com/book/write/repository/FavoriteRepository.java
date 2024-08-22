package com.book.write.repository;

import com.book.write.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByWriteInfoId(Long InfoId);
    @Query(value = "SELECT * FROM favorite WHERE member_id = :memberId ORDER BY favorite_id DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Favorite> findByMemberIdOrderByFavoriteIdDesc(@Param("memberId") Long memberId, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT COUNT(*) FROM favorite WHERE member_id = :memberId", nativeQuery = true)
    long countByMemberId(@Param("memberId") Long memberId);
    Favorite findByMemberIdAndWriteInfoId( Long memberId,Long infoId);

}
