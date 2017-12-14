package com.lotte.redis.model;

import lombok.*;

@Data
@AllArgsConstructor
public class User {

    private String userId;
    private String userName;
    private long serialNumber;

}
