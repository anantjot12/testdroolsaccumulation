package com.test.sumofall;



public class TransactionDetail extends IdGenerator {

	public TransactionDetail(int parentId) {
		super(parentId);
		this.workingObjectsTransactionDetail = new WorkingObjectsTransactionDetail(getMyId());
		
		totalSubordinateFinancingLoanBalanceAmt=0.0;
		totalSubordinateFinancingLineLimitAmt=0.0;
		interestRate=0.7;
		workflowGoForwardIndicator=false;
		// TODO Auto-generated constructor stub
	}
	
	Boolean workflowGoForwardIndicator;
	
	public Boolean getWorkflowGoForwardIndicator() {
		return workflowGoForwardIndicator;
	}

	public void setWorkflowGoForwardIndicator(Boolean workflowGoForwardIndicator) {
		this.workflowGoForwardIndicator = workflowGoForwardIndicator;
	}

	Double interestRate;
	
	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	private Double totalSubordinateFinancingLoanBalanceAmt;
	public Double getTotalSubordinateFinancingLoanBalanceAmt() {
		return totalSubordinateFinancingLoanBalanceAmt;
	}
	public void setTotalSubordinateFinancingLoanBalanceAmt(Double totalSubordinateFinancingLoanBalanceAmt) 
	{
		this.totalSubordinateFinancingLoanBalanceAmt = totalSubordinateFinancingLoanBalanceAmt;
	}
	public Double getTotalSubordinateFinancingLineLimitAmt() 
	{
		return totalSubordinateFinancingLineLimitAmt;
	}
	public void setTotalSubordinateFinancingLineLimitAmt(Double totalSubordinateFinancingLineLimitAmt) 
	{
		this.totalSubordinateFinancingLineLimitAmt = totalSubordinateFinancingLineLimitAmt;
	}

	private Double totalSubordinateFinancingLineLimitAmt;
	private WorkingObjectsTransactionDetail workingObjectsTransactionDetail;
	public WorkingObjectsTransactionDetail getWorkingObjectsTransactionDetail() {
		return workingObjectsTransactionDetail;
	}
	public void setWorkingObjectsTransactionDetail(
			WorkingObjectsTransactionDetail workingObjectsTransactionDetail) {
		this.workingObjectsTransactionDetail = workingObjectsTransactionDetail;
	}
	public PolicySet getPolicySet() {
		return policySet;
	}
	public void setPolicySet(PolicySet policySet) {
		this.policySet = policySet;
	}
	
	public PolicySet addNewPolicySet()
	{
		policySet = new PolicySet(getMyId());
		return policySet;
	}
	
	private PolicySet policySet;
}
