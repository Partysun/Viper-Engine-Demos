package com.partysun.flight.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.partysun.flight.core.ViperExamples;

public class flightActivity extends GameActivity {

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("com/partysun/flight/resources");
    PlayN.run(new ViperExamples());
  }
}
