package com.example.agents;

import com.example.utils.LoggerUtils;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;

public class YellowAgent extends Agent {
  private LoggerUtils loggerUtils = new LoggerUtils();

  protected void setup() {
    Object[] args = getArguments();
    if (args != null && args.length > 0) {
      String argument = (String) args[0];
      loggerUtils.prettyLog("The agent " + getLocalName() + " is ready");
      // If argument is 'fire'
      if (argument.equalsIgnoreCase("Firefighter")) {
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("Extinguish the fire");
        requestNotification(serviceDescription, "Firefighter");
        loggerUtils.prettyLog("The agent " + getLocalName() + " has requested a notification for the " + argument);
      }
      if (argument.equalsIgnoreCase("Police")) {
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("Catch the robber");
        requestNotification(serviceDescription, "Police");
        loggerUtils.prettyLog("The agent " + getLocalName() + " has requested a notification for the " + argument);
      }
      if (argument.equalsIgnoreCase("Doctor")) {
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("Treat the patient");
        requestNotification(serviceDescription, "Doctor");
        loggerUtils.prettyLog("The agent " + getLocalName() + " has requested a notification for the " + argument);
      }

      addBehaviour(new CyclicBehaviour(this) {
        MessageTemplate mt = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
        MessageTemplate mt2 = MessageTemplate.not(mt);

        public void action() {
          ACLMessage msg = myAgent.receive(mt2);
          if (msg != null) {
            String content = msg.getContent();
            loggerUtils.prettyLog("--> " + msg.getSender().getName() + ": " + content);
          } else {
            block();
          }
        }
      });
    }
    ;
  }

  protected void searchForAgent(ServiceDescription serviceDescription, String type) {
    addBehaviour(new TickerBehaviour(this, 60000) {
      protected void onTick() {
        DFAgentDescription template = new DFAgentDescription();
        template.addServices(serviceDescription);
        try {
          DFAgentDescription[] result = DFService.search(myAgent, template);
          if (result.length > 0) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID(result[0].getName().getLocalName(), AID.ISLOCALNAME));
            msg.setLanguage(" English");
            msg.setOntology(" Emergency ");
            msg.setContent(type);
            loggerUtils.prettyLog("The agent " + myAgent.getLocalName() + " is sending a message to the " + type);
            myAgent.send(msg);
            stop();
          }
        } catch (FIPAException fe) {
          fe.printStackTrace();
        }
      }
    });
  }

  protected void requestNotification(final ServiceDescription sd, final String type) {
    DFAgentDescription dfd = new DFAgentDescription();
    dfd.addServices(sd);
    ACLMessage msg = DFService.createSubscriptionMessage(this, getDefaultDF(), dfd, null);
    loggerUtils.prettyLog("The agent " + getLocalName() + " is requesting a notification for the " + type);

    addBehaviour(new SubscriptionInitiator(this, msg) {
      protected void handleInform(ACLMessage inform) {
        loggerUtils.prettyLog("The agent " + myAgent.getLocalName() + " has received a notification for the " + type);
        try {
          DFAgentDescription[] dfds = DFService.decodeNotification(inform.getContent());
          ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
          msg.addReceiver(new AID(dfds[0].getName().getLocalName(), AID.ISLOCALNAME));
          msg.setLanguage(" English");
          msg.setOntology(" Emergency ");
          msg.setContent(type);
          msg.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
          loggerUtils.prettyLog("The agent " + myAgent.getLocalName() + " is sending a message to the " + type);
          myAgent.send(msg);
        } catch (FIPAException fe) {
          fe.printStackTrace();
        }
      }
    });
  }

  protected void takeDown() {
    loggerUtils.prettyLog("The agent " + getLocalName() + " is terminating");
  }
}
