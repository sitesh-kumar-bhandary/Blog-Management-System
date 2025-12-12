package com.siteshkumar.bms.Entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message="username is required")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message="password is required")
    private String password;

    private String roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;
}
