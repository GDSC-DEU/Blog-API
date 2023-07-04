package com.gdsc.blog.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gdsc.blog.user.entity.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

	// 아이디 중복 체크
	boolean existsByEmail(String email);

	// 로그인 메소드
	Optional<User> findByEmailAndPassword(String email, String password);


	// 회원 탈퇴
	@Transactional
	void deleteById(Long id);

	// 유저 검색
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	// 회원가입 메소드
}
