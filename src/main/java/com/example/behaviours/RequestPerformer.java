package com.example.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class RequestPerformer extends Behaviour {
  private static int times = 0;

  public RequestPerformer(Agent a) {
    super(a);
    System.out.println("Hello World from RequestPerformer");
  }

  @Override
  public void action() {
    System.out.println("Agent " + myAgent.getLocalName() + " is performing action " + times);
    times++;
  }

  @Override
  public boolean done() {
    System.out.println("Agent " + myAgent.getLocalName() + " is done");
    return times > 3;
  }
}
