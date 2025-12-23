package com.utils;

import java.security.*;
import java.nio.charset.StandardCharsets;

public class TestHash {
  public static void main(String[] args) throws Exception {
    String pwd = "Welcome@123";
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] bytes = md.digest(pwd.trim().getBytes(StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) sb.append(String.format("%02x", b));
    System.out.println("SHA-256(Welcome@123) = " + sb);
  }
}
