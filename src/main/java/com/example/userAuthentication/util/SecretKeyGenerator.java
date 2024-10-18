package com.example.userAuthentication.util;

import java.util.UUID;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        String secretKey = UUID.randomUUID().toString();
        System.out.println("Generated Secret Key: " + secretKey);
    }
}

//berikut cara menjalankan fungsi secretKey:

//cd src/main/java
//javac com/example/userAuthentication/util/SecretKeyGenerator.java
//java com.example.userAuthentication.util.SecretKeyGenerator
