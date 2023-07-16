package com.gdsc.blog.article.service;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.mapper.ArticleMapper;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.entity.UserRole;
import com.gdsc.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    /**
     * 게시글 생성
     *
     * @param articleCreateDto 게시글 생성 DTO
     * @param username         작성자
     */
    public ArticleDto createArticle(
            ArticleCreateDto articleCreateDto,
            String username) {

        // 현재 시간
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // 유저 정보
        User user = userRepository.findByUsername(username).orElseThrow();

        // 게시글 생성
        Article article = Article.builder()
                .title(articleCreateDto.getTitle())
                .content(articleCreateDto.getContent())
                .user(user)
                .createDate(timestamp)
                .modifyDate(timestamp)
                .build();

        return articleMapper.toDto(articleRepository.save(article));
    }

    /**
     * 최근 게시글 목록 조회
     *
     * @return 게시글 목록
     */
    public List<ArticleDto> getRecentArticle() {
        // get recent article list
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // 최신순으로 정렬

        // article list to article dto list
        List<ArticleDto> articleList = new ArrayList<>();
        articles.forEach(article -> articleList.add(articleMapper.toDto(article)));

        return articleList;
    }

    /**
     * 유저 게시글 목록 조회
     *
     * @return 게시글 목록
     */
    public List<ArticleDto> getUserArticle(User user) {
        // get user article list
        List<Article> articles = articleRepository.findByUser(user);
        log.info("articles: {}", articles);

        // article list to article dto list
        List<ArticleDto> articleList = new ArrayList<>();
        articles.forEach(article -> articleList.add(articleMapper.toDto(article)));

        return articleList;
    }

    /**
     * id로 게시글 조회
     *
     * @param idx 게시글 id
     * @return Article 객체
     */
    public ArticleDto getArticleById(Long idx) { //get article by id
        Optional<Article> article = articleRepository.findById(idx);

        if (article.isPresent()) {
            return articleMapper.toDto(article.get());
        } else {
            throw new IllegalArgumentException("Not found Article by id");
        }
    }

    public List<ArticleDto> findArticleByTitle(String title) {
        List<Article> articles = articleRepository.findByTitleLike(title);

        List<ArticleDto> articleList = new ArrayList<>();
        articles.forEach(article -> articleList.add(articleMapper.toDto(article)));

        return articleList;
    }

    /**
     * 게시글 수정
     *
     * @param id   게시글 id
     * @param dto  게시글 수정 DTO
     * @param user 작성자
     * @return Article 객체
     */
    public ArticleDto updateArticle(Long id, ArticleUpdateDto dto, String username) {

        Article article = articleRepository.findById(id).orElseThrow(); //게시글 id로 게시글 가져오기
        User user = userRepository.findByUsername(username).orElseThrow(); //유저 정보 가져오기

        if (!article.getUser().getUsername().equals(user.getUsername())) { //게시글 작성자와 로그인한 유저가 같지 않을 경우
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setModifyDate(new Timestamp(System.currentTimeMillis()));

        return articleMapper.toDto(articleRepository.save(article));
    }

    /**
     * 게시글 삭제 (관리자, 작성자만 가능)
     *
     * @param idx  게시글 id
     * @param user 작성자
     */
    public void deleteArticle(Long idx, String username) {
        Article article = articleRepository.findById(idx).orElseThrow(); //게시글 id로 게시글 가져오기

        User user = userRepository.findByUsername(username).orElseThrow(); //유저 정보 가져오기

        // 게시글 작성자와 로그인한 유저가 같거나, 로그인 한 유저가 관리자일 경우
        if (article.getUser().getUsername().equals(user.getUsername()) || user.getRoles().contains(UserRole.ROLE_ADMIN)) {
            articleRepository.delete(article);
        } else {
            throw new NullPointerException("삭제 권한이 없습니다.");
        }

    }
}
