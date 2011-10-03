package com.partysun.flight.flash;

import playn.core.PlayN;
import playn.flash.FlashGame;
import playn.flash.FlashPlatform;

import com.partysun.flight.core.ViperExamples;

public class flightFlash extends FlashGame {

  @Override
  public void start() {
    FlashPlatform platform = FlashPlatform.register();
    platform.assetManager().setPathPrefix("flightflash/");
    PlayN.run(new ViperExamples());
  }
}
