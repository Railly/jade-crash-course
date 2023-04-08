package com.example.agents;

import jade.core.Agent;

public class HelloAgent extends Agent {
  protected void setup() {
    System.out.println("Hello World. ");
    System.out.println("My name is " + getLocalName());
    System.out.println("My complete identification (AID): \n" + getAID());
    System.out.println("My local name is " + getAID().getLocalName());
    System.out.println("My global name (GUID) is " + getAID().getName());
    System.out.println("My addresses are: ");
    for (String address : getAID().getAddressesArray()) {
      System.out.println(address);
    }
  }
}
