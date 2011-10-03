package com.partysun.flight.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.partysun.flight.core.ViperExamples;

public class flightHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assetManager().setPathPrefix("flight/");
    PlayN.run(new ViperExamples());
  }
}
