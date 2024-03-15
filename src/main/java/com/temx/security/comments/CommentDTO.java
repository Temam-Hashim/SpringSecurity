package com.temx.security.comments;

import com.temx.security.customers.Customer;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CommentDTO {

    private Long commentId;
    private Long postId;
    private Long customerId;
    private String text;
    private Date createdAt;



}




