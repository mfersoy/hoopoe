package com.hoopoe.repository;


import com.hoopoe.domain.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {

    //TODO please check save / persist/ flush in JPA/Hibernate

    //TODO please check triggers / indexes / views in SQL

}
