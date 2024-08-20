package com.book.write.repository;

import com.book.write.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String logonId);
    Member findByNickname(String nickName);

    Member findByEmail(String email);

    Member findById(Optional<Long> id);

}
