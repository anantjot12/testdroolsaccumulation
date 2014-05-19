testdroolsaccumulation
======================

Accumulation in Drools PHREAK engine returns n-times more value than actual number of summed values present when ***split and join*** are used in JBPM flow. 
The factor n is dependent upon the number of times the parent object updates are called before rule that does the summation is invoked in the flow.

This test project has following DataModel to demonstrate the issue. 

InvokeServiceRequest=>LoanRequest==>TransactionDetail==>PolicySet	and WorkingObjectTransactionDetail
					
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

