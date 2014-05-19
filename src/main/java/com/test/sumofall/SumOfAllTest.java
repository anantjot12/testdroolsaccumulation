package com.test.sumofall;


import java.util.Properties;

import org.apache.log4j.Logger;
import org.drools.compiler.builder.impl.KnowledgeBuilderConfigurationImpl;
import org.drools.compiler.rule.builder.dialect.java.JavaDialectConfiguration;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.logger.KnowledgeRuntimeLogger;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.commons.MyWorkingMemoryLogger;


public class SumOfAllTest {

	private static boolean IS_ECLIPSE_COMPILER = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
			 	Logger logger =Logger.getLogger("MyLog");
			 	logger.info("Begin SumAccumulateFunction Test");
	            Properties properties = new Properties();
	            if (IS_ECLIPSE_COMPILER) {
	            	properties.setProperty("drools.dialect.java.compiler", "ECLIPSE");
	            } else {
	            	properties.setProperty("drools.dialect.java.compiler", "JANINO");
	            }

	            KnowledgeBuilderConfigurationImpl config = new KnowledgeBuilderConfigurationImpl(properties);
				JavaDialectConfiguration javaConf = (JavaDialectConfiguration) config
						.getDialectConfiguration("java");
				
	            if (IS_ECLIPSE_COMPILER) {
	    			javaConf.setCompiler(JavaDialectConfiguration.ECLIPSE);
	            } else {
	    			javaConf.setCompiler(JavaDialectConfiguration.JANINO);
	            }
	            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config);
	            
	            kbuilder.add(ResourceFactory.newClassPathResource("accumulate_flow_with_SplitAndJoin.rf"), ResourceType.DRF);
	            KnowledgeBuilderErrors sumofall = kbuilder.getErrors();
	            if (sumofall.size() > 0) {
	                for (KnowledgeBuilderError error: sumofall) {
	                    System.err.println(error);
	                }
	                throw new IllegalArgumentException("Could not parse knowledge.");
	            }
	            
	            kbuilder.add(ResourceFactory.newClassPathResource("group_1.drl"), ResourceType.DRL);
	            KnowledgeBuilderErrors errors50068424 = kbuilder.getErrors();
	            if (errors50068424.size() > 0) {
	                for (KnowledgeBuilderError error: errors50068424) {
	                    System.err.println(error);
	                }
	                throw new IllegalArgumentException("Could not parse knowledge.");
	            }
	            
	            kbuilder.add(ResourceFactory.newClassPathResource("group_2.drl"), ResourceType.DRL);
	            KnowledgeBuilderErrors errors50068460 = kbuilder.getErrors();
	            if (errors50068460.size() > 0) {
	                for (KnowledgeBuilderError error: errors50068460) {
	                    System.err.println(error);
	                }
	                throw new IllegalArgumentException("Could not parse knowledge.");
	            }
	            
	            kbuilder.add(ResourceFactory.newClassPathResource("group_3.drl"), ResourceType.DRL);
	            KnowledgeBuilderErrors errors50068453 = kbuilder.getErrors();
	            if (errors50068453.size() > 0) {
	                for (KnowledgeBuilderError error: errors50068453) {
	                    System.err.println(error);
	                }
	                throw new IllegalArgumentException("Could not parse knowledge.");
	            }

	            KieBaseConfiguration kbc = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(properties);

	            KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbc);
	            kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	           
	        	
	        	StatefulKnowledgeSession ksession =  kbase.newStatefulKnowledgeSession();
	            //WorkingMemory workingMemory = ruleBase.newStatefulSession();
	            //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "C:/temp/droolsaudit.log");

	            
	            MyWorkingMemoryLogger ssl = new MyWorkingMemoryLogger(ksession, logger);
				/*ssl.addFilter(new ARGenTLogEventFilter(new int[]
				                                               { LogEvent.AFTER_RULEFLOW_COMPLETED, LogEvent.AFTER_RULEFLOW_CREATED, LogEvent.ACTIVATION_CREATED,
						LogEvent.ACTIVATION_CANCELLED, LogEvent.BEFORE_ACTIVATION_FIRE, LogEvent.AFTER_ACTIVATION_FIRE,
						LogEvent.AFTER_RULE_ADDED, LogEvent.AFTER_RULE_REMOVED, LogEvent.AFTER_RULEFLOW_GROUP_ACTIVATED,
						LogEvent.AFTER_RULEFLOW_GROUP_DEACTIVATED, LogEvent.INSERTED, LogEvent.RETRACTED,
						LogEvent.AFTER_TASK_INSTANCE_COMPLETED, LogEvent.AFTER_RULEFLOW_NODE_EXITED,
						LogEvent.AFTER_RULEFLOW_NODE_TRIGGERED, LogEvent.UPDATED }));*/
	            InvokeServicesRequest req = new InvokeServicesRequest(0);
	         
 
	            ksession.insert(req);
	            ksession.insert(req.getLoanFile());
	            ksession.insert(req.getLoanFile().getTransactionDetail());
	            ksession.insert(req.getLoanFile().getTransactionDetail().getWorkingObjectsTransactionDetail());
	            
	            //PolicySet is inserted by 
	            
	            for (final KnowledgePackage kp : kbase.getKnowledgePackages())
					for (final org.kie.api.definition.process.Process p : kp.getProcesses())
					{
						final String id = p.getId();
						final Long lId = new Long(id);
						ksession.startProcess(id);
					}
	            
	            System.out.println("Before Rule Firing:");
	            System.out.println("PolicySet: " +req.getLoanFile().getTransactionDetail().getPolicySet());
	            System.out.println("WorkingObjectTransactionDetail=>TotalLoanAmount: " +req.getLoanFile().getTransactionDetail().getWorkingObjectsTransactionDetail().getTotalLoanAmount());
	            
	            
	            ksession.fireAllRules();

	            System.out.println("After Rule Firing:");
	            if(req.getLoanFile().getTransactionDetail().getPolicySet()!=null)
	            {
	            	System.out.println("PolicySet==>TotalLoanAmount: " +req.getLoanFile().getTransactionDetail().getPolicySet().getTotalLoanAmt());
	            }
	            System.out.println("WorkingObjectTransactionDetail=>TotalLoanAmount: " +req.getLoanFile().getTransactionDetail().getWorkingObjectsTransactionDetail().getTotalLoanAmount());

	            System.out.println("Total Sum of All: "+ req.getLoanFile().getTransactionDetail().getWorkingObjectsTransactionDetail().getTotalLoanAmount());
	 
			 	logger.info("End SumAccumulateFunction Test");
	 
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }

	}

}
