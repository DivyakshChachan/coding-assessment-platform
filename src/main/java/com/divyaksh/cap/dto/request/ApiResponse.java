package com.divyaksh.cap.dto.request;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T>{
    private boolean success;

    private String message;

    private T data;
}
