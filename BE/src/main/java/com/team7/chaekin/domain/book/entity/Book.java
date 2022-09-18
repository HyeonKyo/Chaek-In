package com.team7.chaekin.domain.book.entity;

import com.team7.chaekin.domain.category.entity.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255)
    private String author;

    private Date publishDate;

    @Column(length = 2000)
    private String description;

    @Column(length = 1000)
    private String cover;

    //    카테고리 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 50)
    private String publisher;

    private int page;

    private double ratingScore;

    private int ratingCount;
}
