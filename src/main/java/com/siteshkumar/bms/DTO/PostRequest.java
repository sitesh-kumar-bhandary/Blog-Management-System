package com.siteshkumar.bms.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;
}
