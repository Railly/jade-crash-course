package com.example.agents;

import com.example.behaviours.RequestPerformer;

import jade.core.Agent;

public class BookBuyer extends Agent {
  private String targetBookTitle;

  protected void setup() {
    System.out.println("Hello! Buyer-agent " + getAID().getName() + " is ready.");

    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      targetBookTitle = (String) args[0];
      System.out.println("Target book is " + targetBookTitle);
      addBehaviour(new RequestPerformer(this));
    } else {
      // Make the agent terminate immediately
      System.out.println("No target book title specified");
      doDelete();
    }
  }
}
