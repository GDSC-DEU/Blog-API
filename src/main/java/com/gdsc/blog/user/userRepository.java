package com.gdsc.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<Customer, String> {

}
