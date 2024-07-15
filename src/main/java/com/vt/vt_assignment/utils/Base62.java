package com.vt.vt_assignment.utils;

public class Base62 {
  private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final int BASE = BASE62.length();
  private static final int FIXED_LENGTH = 4;

  public static String encode(long value) {
    StringBuilder sb = new StringBuilder();
    while (value > 0) {
      sb.append(BASE62.charAt((int) (value % BASE)));
      value /= BASE;
    }

    // Ensure the encoded string is exactly 4 characters
    while (sb.length() < FIXED_LENGTH) {
      sb.append('0');  // Pad with '0' to ensure 4-character length
    }

    return sb.reverse().toString();
  }
}
