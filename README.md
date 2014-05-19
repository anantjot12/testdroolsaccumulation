testdroolsaccumulation
======================

Drools Accumulation Test issue
How to reproduce?
A simple test project is attached to this report. This project has following DataModel. 

InvokeServiceRequest=>LoanRequest==>TransactionDetail==>PolicySet	and WorkingObjectTransactionDetail
					
<?xml version="1.0" encoding="UTF-8"?> 
<process xmlns="http://drools.org/drools-5.0/process"
         xmlns:xs="http://www.w3.org/2001/XMLSchema-instance"
         xs:schemaLocation="http://drools.org/drools-5.0/process drools-processes-5.0.xsd"
         type="RuleFlow" name="RF6365_200" id="278865" package-name="com.test.sumofall.GeneratedRules" version="200" >

  <header>
    <imports>
      <import name="com.test.sumofall.InvokeServicesRequest" />
      <import name="com.test.sumofall.TransactionDetail" />
      <import name="com.test.sumofall.LoanRequest" />
      <import name="com.test.sumofall.WorkingObjectsTransactionDetail" />
    </imports>
  </header>

  <nodes>
    <ruleSet id="1" name="1" x="307" y="31" width="80" height="48" ruleFlowGroup="group_1" />
    <start id="70004" name="Start" x="61" y="33" width="48" height="48" />
    <ruleSet id="2" name="2" x="463" y="27" width="80" height="48" ruleFlowGroup="group_2" />
    <ruleSet id="3" name="3" x="315" y="230" width="80" height="48" ruleFlowGroup="group_3" />
    <join id="90004" name="EndJoin90004" x="532" y="80" width="49" height="49" type="2" />
    <split id="11" name="11" x="337" y="101" width="49" height="49" type="2" >
      <constraints>
        <constraint toNodeId="90004" toType="DROOLS_DEFAULT" name="ServiceFlowCode Equal to Post Rules Processing" priority="1" type="rule" dialect="mvel" >$invokeServices : 
					InvokeServicesRequest(  )

					$lendingTransaction : 
					LoanRequest(  parentId == $invokeServices.myId )

					$transactionDetail : 
					TransactionDetail(  parentId == $lendingTransaction.myId )

					$workingTransactionDetail : 
					WorkingObjectsTransactionDetail(  parentId == $transactionDetail.myId
						, serviceFlowCode == "Post Rules Processing" )</constraint>
        <constraint toNodeId="3" toType="DROOLS_DEFAULT" name="ServiceFlowCode Not Equal to Post Rules Processing" priority="1" type="rule" dialect="mvel" >$invokeServices : 
					InvokeServicesRequest(  )

					$lendingTransaction : 
					LoanRequest(  parentId == $invokeServices.myId )

					$transactionDetail : 
					TransactionDetail(  parentId == $lendingTransaction.myId )

					$workingTransactionDetail : 
					WorkingObjectsTransactionDetail(  parentId == $transactionDetail.myId
						, serviceFlowCode != "Post Rules Processing" )</constraint>
      </constraints>
    </split>
    <end id="80004" name="End" x="640" y="30" width="48" height="48" />
  </nodes>

  <connections>
    <connection from="70004" to="1" />
    <connection from="1" to="2" />
    <connection from="2" to="11" />
	<!--If WE change order of the following two lines to this it works for this example but similar change doesn't work when multiple split and joins are present in ruleflow
    <connection from="11" to="3" />
    <connection from="11" to="90004" />
		-->
    <connection from="11" to="90004" />
    <connection from="11" to="3" />

    <connection from="3" to="90004" />
    <connection from="90004" to="80004" />
  </connections>

</process>				

****ROOT CAUSE****			
I believe the problem is caused by the childLeftTuple.getStagedType() being equal to LeftTuple.INSERT instead of LeftTuple.NONE or LeftTuple.UPDATE. 

Thread [main] (Suspended)	
	PhreakJoinNode.updateChildLeftTuple(LeftTuple, LeftTupleSets, LeftTupleSets) line: 443	
	PhreakJoinNode.doRightUpdatesProcessChildren(LeftTuple, LeftTuple, RightTuple, LeftTupleSets, ContextEntry[], BetaConstraints, LeftTupleSink, FastIterator, LeftTupleSets) line: 363	
	PhreakJoinNode.doRightUpdates(JoinNode, LeftTupleSink, BetaMemory, InternalWorkingMemory, RightTupleSets, LeftTupleSets, LeftTupleSets) line: 307	
	PhreakJoinNode.doNode(JoinNode, LeftTupleSink, BetaMemory, InternalWorkingMemory, LeftTupleSets, LeftTupleSets, LeftTupleSets) line: 48	
	RuleNetworkEvaluator.switchOnDoBetaNode(NetworkNode, LeftTupleSets, InternalWorkingMemory, LeftTupleSets, LeftTupleSets, LeftTupleSinkNode, BetaMemory, AccumulateNode$AccumulateMemory) line: 547	
	RuleNetworkEvaluator.evalBetaNode(LeftInputAdapterNode, PathMemory, NetworkNode, Memory, SegmentMemory[], int, LeftTupleSets, InternalWorkingMemory, LinkedList<StackEntry>, LinkedList<StackEntry>, Set<String>, boolean, RuleExecutor, LeftTupleSets, LeftTupleSets, LeftTupleSinkNode) line: 533	
	RuleNetworkEvaluator.innerEval(LeftInputAdapterNode, PathMemory, NetworkNode, long, Memory, SegmentMemory[], int, LeftTupleSets, InternalWorkingMemory, LinkedList<StackEntry>, LinkedList<StackEntry>, Set<String>, boolean, RuleExecutor) line: 334	
	RuleNetworkEvaluator.outerEval(LeftInputAdapterNode, PathMemory, NetworkNode, long, Memory, SegmentMemory[], int, LeftTupleSets, InternalWorkingMemory, LinkedList<StackEntry>, LinkedList<StackEntry>, Set<String>, boolean, RuleExecutor) line: 161	
	RuleNetworkEvaluator.evaluateNetwork(PathMemory, LinkedList<StackEntry>, RuleExecutor, InternalWorkingMemory) line: 116	
	RuleExecutor.evaluateNetwork(InternalWorkingMemory) line: 54	
	DefaultAgenda.evaluateEagerList() line: 958	
	DefaultAgenda.fireNextItem(AgendaFilter, int, int) line: 913	
	DefaultAgenda.fireAllRules(AgendaFilter, int) line: 1198	
	StatefulKnowledgeSessionImpl.fireAllRules(AgendaFilter, int) line: 1202	
	StatefulKnowledgeSessionImpl.fireAllRules() line: 1175	
	SumOfAllTest.main(String[]) line: 133	

