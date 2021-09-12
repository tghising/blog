package com.ghising.blog.payload;

import com.ghising.blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Comment name should not be null or empty.")
    private String name;

    @NotEmpty(message = "Comment email should not be null or empty.")
    @Email(message = "Comment email should be valid.")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters.")
    private String body;
}
