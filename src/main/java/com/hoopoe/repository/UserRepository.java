package com.hoopoe.repository;

import com.hoopoe.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = "id")
    Optional<User> findUserById(Long id);

    @EntityGraph(attributePaths = "roles")
    Page<User> findAll(Pageable pageable);

    //if  ı am updating (DML OPS in JPA repo you should use this annotation)
    @Query("UPDATE User u SET u.firstName=:firstName,u.lastName=:lastName," +
    "u.phoneNumber=:phoneNumber,u.email=:email WHERE u.id=:id")
    @Modifying
    void update(@Param("id") Long id,
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("phoneNumber") String phoneNumber,
                @Param("email") String email);


}
