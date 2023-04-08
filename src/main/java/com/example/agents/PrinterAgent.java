package com.example.agents;

import com.example.behaviours.PrinterPerformer;

import jade.core.Agent;

public class PrinterAgent extends Agent {
  protected void setup() {
    Object[] args = getArguments();
    if (args != null & args.length > 0) {
      for (Object arg : args) {
        long delay = Long.parseLong((String) arg);
        addBehaviour(new PrinterPerformer(this, delay));
      }
    } else {
      System.out.println("No delay specified");
      doDelete();
    }
  }
}
