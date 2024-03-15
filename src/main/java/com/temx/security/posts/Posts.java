package com.temx.security.posts;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.temx.security.comments.Comments;
import com.temx.security.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List; // Import the List class


@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Posts {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private long id;

    @NotBlank(message = "Post title Required")
    private String title;

    @NotBlank(message = "Post description Required")
    private String description;

    private String img;

    @ElementCollection
    private List<String> tags; // Use List<String> to store multiple tags

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Comments> comments;

    @JsonIgnore
    @ManyToOne()
    @JsonManagedReference
    @JsonProperty("Post_Author")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false) // Mark the column as not insertable or updatable
    @JsonProperty("author_id")
    private Long userId; // Field to store the user ID


}
