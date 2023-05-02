package com.multi.demo.repository.primary;

import com.multi.demo.entity.primary.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrimaryRepository extends JpaRepository<Primary,Long> {
    List<Primary> findAll();
}
