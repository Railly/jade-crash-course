package com.example.behaviours;

import com.example.utils.LoggerUtils;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class PrinterPerformer extends Behaviour {
  private LoggerUtils loggerUtils = new LoggerUtils();
  private int times = 1;
  private long delay;
  private long startTime = System.currentTimeMillis();

  public PrinterPerformer(Agent a, long delay) {
    super(a);
    this.delay = delay;
    loggerUtils.prettyLog("Agent " + a.getLocalName() + " will sleep for " + delay + " milliseconds");
  }

  @Override
  public void action() {
    block(delay);
    loggerUtils.prettyLog("Agent " + myAgent.getLocalName() + " is performing action " + times + " after "
        + (System.currentTimeMillis() - startTime) + " milliseconds");
    times++;
  }

  @Override
  public boolean done() {
    loggerUtils.prettyLog("Agent " + myAgent.getLocalName() + " is done");
    return times > 10;
  }

  @Override
  public int onEnd() {
    loggerUtils.prettyLog("Agent " + myAgent.getLocalName() + " is ending");
    return super.onEnd();
  }
}
