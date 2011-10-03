package com.partysun.flight.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.partysun.flight.core.ViperExamples;

public class flightJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assetManager().setPathPrefix("src/main/java/com/partysun/flight/resources");
    PlayN.run(new ViperExamples());
  }
}
