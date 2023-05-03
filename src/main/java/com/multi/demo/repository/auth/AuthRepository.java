package com.multi.demo.repository.auth;

import com.multi.demo.entity.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth,Long> {
}
