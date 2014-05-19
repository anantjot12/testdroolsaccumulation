package com.commons;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.drools.core.WorkingMemory;
import org.drools.core.audit.WorkingMemoryLogger;
import org.drools.core.audit.event.ActivationLogEvent;
import org.drools.core.audit.event.ILogEventFilter;
import org.drools.core.audit.event.LogEvent;
import org.drools.core.audit.event.ObjectLogEvent;
import org.drools.core.audit.event.RuleBaseLogEvent;
import org.drools.core.audit.event.RuleFlowGroupLogEvent;
import org.drools.core.audit.event.RuleFlowLogEvent;
import org.drools.core.audit.event.RuleFlowNodeLogEvent;
import org.drools.core.common.DefaultFactHandle;
import org.kie.api.event.KieRuntimeEventManager;
import org.kie.internal.event.KnowledgeRuntimeEventManager;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.test.sumofall.PolicySet;
import com.test.sumofall.TransactionDetail;
import com.test.sumofall.WorkingObjectsTransactionDetail;

public class MyWorkingMemoryLogger extends WorkingMemoryLogger{


		Logger logger;
		StatefulKnowledgeSession ksession;

		public MyWorkingMemoryLogger(StatefulKnowledgeSession session, Logger logger)
		{
			super(session);
			this.logger = logger;
			this.ksession =session;
		}
		

	
		@Override
		public void logEventCreated(LogEvent arg0)
		{
//			super.logEventCreated(arg0);
			
			if (arg0 instanceof ActivationLogEvent)
			{
				ActivationLogEvent e = (ActivationLogEvent)arg0;
				log(e);
			}
			else if (arg0 instanceof ObjectLogEvent)
			{
				ObjectLogEvent e = (ObjectLogEvent)arg0;
				log(e);
			}
			else if (arg0 instanceof RuleBaseLogEvent)
			{
				RuleBaseLogEvent e = (RuleBaseLogEvent)arg0;
				log(e);
			}
			else if (arg0 instanceof RuleFlowGroupLogEvent)
			{
				RuleFlowGroupLogEvent e = (RuleFlowGroupLogEvent)arg0;
				log(e);
			}
			else if (arg0 instanceof RuleFlowLogEvent)
			{
				RuleFlowLogEvent e = (RuleFlowLogEvent)arg0;
				log(e);
			}
			else
			{
				logger.warn("Unknown LogEvent type received in ARGenTWorkingMemoryLogger");
			}
		}
		private void log(ActivationLogEvent e) 
		{
			String rulesetName = "";

			if(e.getRule().contains("RS7587.1.4_"))
			{
				Collection<DefaultFactHandle> psFH = ksession.getFactHandles();
				for(DefaultFactHandle fh : psFH)
				{
					Object obj = ksession.getObject(fh);
					if(obj instanceof WorkingObjectsTransactionDetail)
					{
						WorkingObjectsTransactionDetail	id= (WorkingObjectsTransactionDetail)obj;
						int i=10;
					}else if(obj instanceof PolicySet)
					{
						PolicySet id=(PolicySet)obj;
						int i=10;
					}
					int i=10;
				}
			}
			String s = getHeader(e) + ": " + e.getRule() + rulesetName ;
			if (e.getType() == LogEvent.BEFORE_ACTIVATION_FIRE) {
				s = "\r\n" + s;
			
			} 
			if (e.getType() == LogEvent.AFTER_ACTIVATION_FIRE) {
				s = s + parseDeclarations(e.getDeclarations());
			}
			logger.info(s);		
		}

		private String parseDeclarations(String decl)
		{
			String ret = "";
			String [] segs = decl.split(";");
			String comma = " ";
			for (String seg : segs)
			{
				String s = seg;
				int i = s.indexOf('(');
				if (i > 0)
				{
					s = s.substring(0,i);
				}
				i = s.indexOf('@');
				if (i > 0)
				{
					int j = s.indexOf('=');
					if (j > 0)
					{
						String obj = parseObject(s.substring(j + 1, i));
						s = s.substring(0,j) + obj;
					}
				}
				ret += comma + s.trim();
				comma = ", ";
			}
			return ret;
		}
		private String getFactId(String objectToString)
		{
			String [] parts = objectToString.split("factId:");
			if(parts != null && parts.length == 2)
				return " (Object Id: " +parts[1]+ ")";
			return "";
			
		}
		private void log(ObjectLogEvent e)
		{
			String s = getHeader(e) + parseObject(e.getObjectToString()) +  getFactId(e.toString());
			
			if(s.contains("OBJECT UPDATED TransactionDetail"))
			{
				Collection<DefaultFactHandle> psFH = ksession.getFactHandles();
				for(DefaultFactHandle fh : psFH)
				{
					//com.wellsfargo.service.provider.hcfg.entity.financialDetails.x2007.HousingExpense
					Object obj = ksession.getObject(fh);
					if(obj instanceof TransactionDetail)
					{
						TransactionDetail	id= (TransactionDetail)obj;
					}else if(obj instanceof PolicySet)
					{
						PolicySet id=(PolicySet)obj;
						int i=10;
					}
					int i=10;
				}	
			}

			logger.info(s);
		}
		private String parseObject(String obj)
		{
			String ret = "";
			int i = obj.indexOf('@');
			if (i > 0)
				ret = obj.substring(0,i);
			else
				ret = obj;
			i = ret.lastIndexOf('.');
			if (i > 0)
				ret = ret.substring(i + 1);
			i = ret.indexOf('<');
			if (i > 0)
				ret = ret.substring(0,i);
			return ret;
		}
		private void log(RuleBaseLogEvent e)
		{
			String s = getHeader(e) + " - " + e.getPackageName() + " - " + e.getRuleName();
			logger.info(s);
		}
		private void log(RuleFlowGroupLogEvent e)
		{
			String s = getHeader(e) + e.getGroupName();
			if (s.contains("GROUP DEACTIVATED"))
				s += "\r\n====================================================================================\r\n";
			else if (s.contains("GROUP ACTIVATED"))
				s =  "\r\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\r\n" + s;
			logger.info(s);
		}
		private void log(RuleFlowLogEvent e)
		{
			String s = getHeader(e) + " - " +  e.getProcessName() + " - " + e.getProcessId();
			if (e instanceof RuleFlowNodeLogEvent)
			{
				RuleFlowNodeLogEvent ne = (RuleFlowNodeLogEvent)e;
				s += " - " + ne.getNodeName() + " - " + ne.getNodeInstanceId();
			}
			logger.info(s);
		}
		private String getHeader(LogEvent arg0)
		{
			String header = "";
			switch(arg0.getType())
			{
				case LogEvent.ACTIVATION_CREATED:
					header = "RULE ACTIVATED";
					break;
				case LogEvent.ACTIVATION_CANCELLED:
					header = "ACTIVATION CANCELLED";
					break;
				case LogEvent.AFTER_ACTIVATION_FIRE:
					header = "RULE FIRED";
					break;
				case LogEvent.AFTER_PACKAGE_ADDED:
					header = "PACKAGE ADDED";
					break;
				case LogEvent.AFTER_PACKAGE_REMOVED:
					header = "PACKAGE REMOVED";
					break;
				case LogEvent.AFTER_RULE_ADDED:
					header = "RULE ADDED";
					break;
				case LogEvent.AFTER_RULE_REMOVED:
					header = "RULE REMOVED";
					break;
				case LogEvent.AFTER_RULEFLOW_COMPLETED:
					header = "RULEFLOW FINISHED";
					break;
				case LogEvent.AFTER_RULEFLOW_CREATED:
					header = "RULEFLOW CREATED";
					break;
				case LogEvent.AFTER_RULEFLOW_GROUP_ACTIVATED:
					header = "RULEFLOW GROUP ACTIVATED";
					break;
				case LogEvent.AFTER_RULEFLOW_GROUP_DEACTIVATED:
					header = "RULEFLOW GROUP DEACTIVATED";
					break;
				case LogEvent.AFTER_RULEFLOW_NODE_EXITED:
					header = "RULEFLOW NODE EXITED";
					break;
				case LogEvent.AFTER_RULEFLOW_NODE_TRIGGERED:
					header = "RULEFLOW NODE ENTERED";
					break;
				case LogEvent.AFTER_TASK_INSTANCE_COMPLETED:
					header = "TASK INSTANCE FINISHED";
					break;
				case LogEvent.AFTER_TASK_INSTANCE_CREATED:
					header = "TASK INSTANCE CREATED";
					break;
				case LogEvent.BEFORE_ACTIVATION_FIRE:
					header = "RULE IS FIRING";
					break;
				case LogEvent.BEFORE_PACKAGE_ADDED:
					header = "PACKAGE WILL BE ADDED";
					break;
				case LogEvent.BEFORE_PACKAGE_REMOVED:
					header = "PACKAGE WILL BE REMOVED";
					break;
				case LogEvent.BEFORE_RULE_ADDED:
					header = "RULE WILL BE ADDED";
					break;
				case LogEvent.BEFORE_RULE_REMOVED:
					header = "RULE WILL BE REMOVED";
					break;
				case LogEvent.BEFORE_RULEFLOW_COMPLETED:
					header = "RULEFLOW WILL FINISH";
					break;
				case LogEvent.BEFORE_RULEFLOW_CREATED:
					header = "RULEFLOW WILL BE CREATED";
					break;
				case LogEvent.BEFORE_RULEFLOW_GROUP_ACTIVATED:
					header = "RULEFLOW GROUP WILL BE ACTIVATED";
					break;
				case LogEvent.BEFORE_RULEFLOW_GROUP_DEACTIVATED:
					header = "RULEFLOW GROUP WILL BE DEACTIVATED";
					break;
				case LogEvent.BEFORE_RULEFLOW_NODE_EXITED:
					header = "RULEFLOW NODE WILL BE FINISHED";
					break;
				case LogEvent.BEFORE_RULEFLOW_NODE_TRIGGERED:
					header = "RULEFLOW NODE WILL BE TRIGGERED";
					break;
				case LogEvent.BEFORE_TASK_INSTANCE_COMPLETED:
					header = "TASK INSTANCE WILL FINISH";
					break;
				case LogEvent.BEFORE_TASK_INSTANCE_CREATED:
					header = "TASK INSTANCE WILL BE CREATED";
					break;
				case LogEvent.UPDATED:
					header = "OBJECT UPDATED";
					break;
				case LogEvent.RETRACTED:
					header = "OBJECT RETRACTED";
					break;
				case LogEvent.INSERTED:
					header = "OBJECT INSERTED";
					break;
				default:
					header = "UNKNOWN EVENT TYPE";
					break;
			}
			return header + " ";
		}
}
