package com.vt.vt_assignment.utils;

public class Base62 {
  private static final String BASE62 =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final int BASE = BASE62.length();

  public static String encode(long value) {
    StringBuilder sb = new StringBuilder();
    while (value > 0) {
      sb.append(BASE62.charAt((int) (value % BASE)));
      value /= BASE;
    }
    return sb.reverse().toString();
  }
}
