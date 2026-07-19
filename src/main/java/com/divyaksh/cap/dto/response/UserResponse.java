package com.divyaksh.cap.dto.response;

import com.divyaksh.cap.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String email;

    private String fullName;

    private Role role;
}
