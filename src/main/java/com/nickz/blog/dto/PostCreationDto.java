package com.nickz.blog.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCreationDto {
    @NotEmpty(message = "Message cannot be empty")
    private String text;
    
}
