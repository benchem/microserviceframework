package team.benchem.usersystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.User;

import org.springframework.data.domain.Pageable;

import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    List<User> findAllByUsernameLikeOrderByUsername(String userName, Pageable pageable);

    Optional<User> findByUsername(String userName);

    Optional<User> findByMobile(String mobile);

    Optional<User>  findByEmail(String email);

//    User findByRowIdAndIsAdmin(String rowId,Boolean isAdmin);

//    User findByEmailAndIsAdmin(String email, Boolean isAdmin);

//
//    @Query("select u from User u where u.admin=true and u.email = :email")
//    User findByEmail(
//            @Param("email") String email
//    );

}
