package com.test.sumofall;


public class WorkingObjectsTransactionDetail extends IdGenerator {

	String serviceFlowCode="none";
	public String getServiceFlowCode() {
		return serviceFlowCode;
	}
	public void setServiceFlowCode(String serviceFlowCode) {
		this.serviceFlowCode = serviceFlowCode;
	}
	Double totalLoanAmount;
	Double totalCombinedLendingAmt;
	public Double getTotalCombinedLendingAmt() {
		return totalCombinedLendingAmt;
	}
	public void setTotalCombinedLendingAmt(Double totalCombinedLendingAmt) {
		this.totalCombinedLendingAmt = totalCombinedLendingAmt;
	}
	public Double getTotalLoanAmount() {
		return totalLoanAmount;
	}
	public void setTotalLoanAmount(Double totalWellsFargoLoanAmount) {
		this.totalLoanAmount = totalWellsFargoLoanAmount;
	}
	public WorkingObjectsTransactionDetail(int parentId) {
		super(parentId);
	}

}
