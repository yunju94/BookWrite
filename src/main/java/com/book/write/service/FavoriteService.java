package com.book.write.service;

import com.book.write.entity.Favorite;
import com.book.write.entity.Member;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private  final FavoriteRepository favoriteRepository;
    public List<Favorite> searchfromWrteInfoId(Long InfoId){
        return favoriteRepository.findByWriteInfoId(InfoId);
    }

    public  void  saveFav(Member member, WriteInfo writeInfo){
       Favorite favorite =  Favorite.createFav(member, writeInfo);
       favoriteRepository.save(favorite);
    }

    public  void  DeleteFav(Member member, WriteInfo writeInfo){

        Favorite favorite = favoriteRepository.findByMemberIdAndWriteInfoId(member.getId(), writeInfo.getId());
        System.out.println(favorite.getId());

        while (true){
            Optional<Favorite> existingFavorite = favoriteRepository.findById(favorite.getId());
            if (existingFavorite.isPresent()) {
                favoriteRepository.delete(existingFavorite.get());
                System.out.println("No!!!!!!!!!!!!!!!!!!");

            } else {
                System.out.println("OK!!!!!!!!!!!!!!!!!!!!");
                break;
            }
        }

    }

    public Page<Favorite> searchfromMember(Long memberId, Pageable pageable){
        int offset= (int) pageable.getOffset();
        int limit= pageable.getPageSize();
        List<Favorite> favoriteList=favoriteRepository.findByMemberIdOrderByFavoriteIdDesc(memberId, offset, limit);
        long totalElements = favoriteRepository.countByMemberId(memberId);
        return new PageImpl<>(favoriteList, pageable, totalElements);

    }
}
