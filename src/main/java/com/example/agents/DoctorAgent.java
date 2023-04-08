
package com.example.agents;

import com.example.utils.LoggerUtils;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class DoctorAgent extends Agent {
  private LoggerUtils loggerUtils = new LoggerUtils();
  private String TYPE = "Doctor";
  private String RESPONSE = "I am a doctor, I'm gonna heal you!";
  private String SERVICE_DESCRIPTION = "Treat the patient";

  protected void setup() {
    ServiceDescription sd = new ServiceDescription();
    sd.setType(SERVICE_DESCRIPTION);
    sd.setName(getLocalName());
    registerService(sd);
    receiveMessages(TYPE, RESPONSE);
    loggerUtils.prettyLog("The agent " + getLocalName() + " is ready");
  }

  protected void registerService(ServiceDescription sd) {
    DFAgentDescription dfd = new DFAgentDescription();
    dfd.addServices(sd);
    try {
      DFService.register(this, dfd);
    } catch (FIPAException fe) {
      fe.printStackTrace();
    }
  }

  protected void receiveMessages(final String serviceType, final String serviceDescription) {
    addBehaviour(new CyclicBehaviour(this) {
      public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null && msg.getContent().equalsIgnoreCase(serviceType)) {
          ACLMessage reply = msg.createReply();
          reply.setPerformative(ACLMessage.INFORM);
          loggerUtils.prettyLog("The agent " + msg.getSender().getName() + " reported a patient ");
          loggerUtils.prettyLog("I will activate the procedures to heal the patient!");
          reply.setContent(serviceDescription);
          myAgent.send(reply);
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
