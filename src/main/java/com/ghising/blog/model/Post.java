package com.ghising.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
//@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "title", nullable = false, unique = true)
    private String title;

    @Column( name = "description", nullable = false)
    private String description;

    @Column( name = "content", nullable = false)
    private String content;
}
