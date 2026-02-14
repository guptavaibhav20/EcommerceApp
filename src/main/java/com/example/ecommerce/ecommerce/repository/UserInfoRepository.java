package com.example.ecommerce.ecommerce.repository;

import com.example.ecommerce.ecommerce.models.UserInfo;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByUsername(String username);

    boolean existsByUsername(String username);
}
