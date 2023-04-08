package com.example.agents;

import com.example.utils.LoggerUtils;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;

public class WhiteAgent extends Agent {
  private LoggerUtils loggerUtils = new LoggerUtils();

  protected void setup() {
    try {
      AMSAgentDescription[] agents = null;
      SearchConstraints sc = new SearchConstraints();
      sc.setMaxResults(Long.valueOf(-1));
      agents = AMSService.search(this, new AMSAgentDescription(), sc);
      AID myAID = getAID();

      for (AMSAgentDescription agent : agents) {
        AID agentAID = agent.getName();
        loggerUtils.prettyLog(agentAID.getName() + (agentAID.equals(myAID) ? " --> Me" : " "));
      }
    } catch (FIPAException e) {
      e.printStackTrace();
    }
    doDelete();
    System.exit(0);
  }
}
