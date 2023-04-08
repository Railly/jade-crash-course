package com.example.utils;

import java.util.HashSet;
import java.util.Set;

public class LoggerUtils {
  private static final Set<String> usedColors = new HashSet<>();
  private static final String[] colors = { "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m",
      "\u001B[36m" };
  private String selectedColor;

  public LoggerUtils() {
    this.selectedColor = this.selectColor();
  }

  private String selectColor() {
    if (selectedColor == null) {
      selectedColor = getRandomColor();
    }
    return selectedColor;
  }

  private synchronized String getRandomColor() {
    String randomColor;
    do {
      randomColor = colors[(int) (Math.random() * colors.length)];
    } while (usedColors.contains(randomColor));
    usedColors.add(randomColor);
    return randomColor;
  }

  public static synchronized void resetColors(LoggerUtils loggerUtils) {
    LoggerUtils.usedColors.clear();
  }

  public void prettyLog(String message) {
    System.out.println(this.selectedColor + message + "\u001B[0m");
  }
}
