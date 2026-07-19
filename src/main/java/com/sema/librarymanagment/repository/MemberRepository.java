package com.sema.librarymanagment.repository;

import com.sema.librarymanagment.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
