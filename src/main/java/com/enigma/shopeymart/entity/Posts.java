package com.enigma.shopeymart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "m_posts")
@ToString
public class Posts {
    @Id
    private Integer id;

    private String title;

    private String body;

    @Column(name = "user_id")
    private String userId;
}
