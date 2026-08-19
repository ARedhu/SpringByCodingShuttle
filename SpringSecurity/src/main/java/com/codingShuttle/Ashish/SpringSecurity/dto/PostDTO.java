package com.codingShuttle.Ashish.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // ✅ Useful (generates getters, setters, toString(), etc.)
@AllArgsConstructor // ✅ Commonly used because Spring/Jackson often needs it. Because sometimes an empty object is created and then it's fields set.
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String description;

    private UserDto author;
}
