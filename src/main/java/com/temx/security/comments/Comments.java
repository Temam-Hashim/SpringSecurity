package com.temx.security.comments;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.temx.security.customers.*;
import com.temx.security.posts.Posts;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment text is required")
    private String text;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Posts post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;



}
