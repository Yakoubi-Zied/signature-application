package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SignatureLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SignatureLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignatureLevelRepository extends JpaRepository<SignatureLevel, Long> {
}
