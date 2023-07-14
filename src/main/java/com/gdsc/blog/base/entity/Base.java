package com.gdsc.blog.base.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdsc.blog.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass //엔티티 부모 클래스
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {
    @Column(columnDefinition = "TEXT") //no limit length of text
    private String content;

    @ManyToOne
    @JsonBackReference
    private User user;

    @CreatedDate
    @Column(updatable = false) //수정 방지
    private LocalDateTime createDate; //생성 날짜

    @LastModifiedDate
    private LocalDateTime modifyDate; //수정된 날짜
}
