package com.example.firstproject.repository;

import com.example.firstproject.entitiy.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}
