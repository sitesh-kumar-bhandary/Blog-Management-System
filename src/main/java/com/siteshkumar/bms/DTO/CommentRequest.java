package com.siteshkumar.bms.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    
    @NotBlank(message="text is required")
    private String text;

    @NotNull(message="user id is required")
    private Long userId;
}
