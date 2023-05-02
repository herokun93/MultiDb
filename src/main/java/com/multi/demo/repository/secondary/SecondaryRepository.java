package com.multi.demo.repository.secondary;

import com.multi.demo.entity.secondary.Secondary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryRepository extends JpaRepository<Secondary,Long> {
}
