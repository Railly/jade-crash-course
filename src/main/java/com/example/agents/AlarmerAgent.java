package com.example.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import com.example.utils.LoggerUtils;

import jade.core.AID;

public class AlarmerAgent extends Agent {
  private LoggerUtils loggerUtils = new LoggerUtils();

  protected void setup() {
    addBehaviour(new OneShotBehaviour(this) {
      public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Firefighter", AID.ISLOCALNAME));
        msg.setLanguage(" English");
        msg.setOntology(" Emergency ");
        msg.setContent("Fire");

        loggerUtils.prettyLog("The agent " + myAgent.getLocalName() + " is sending a message to the firefighter");

        myAgent.send(msg);
      }
    });

    addBehaviour(new CyclicBehaviour(this) {
      public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
          String content = msg.getContent();
          loggerUtils.prettyLog("--> " + msg.getSender().getName() + ": " + content);
        } else {
          block();
        }
      }
    });
  }

  protected void takeDown() {
    loggerUtils.prettyLog("The agent " + getLocalName() + " is terminating");
  }
}
