package com.test.sumofall;

public class InvokeServicesRequest extends IdGenerator {

	protected InvokeServicesRequest(int parentId) {
		super(parentId);
		this.loanFile = new LoanRequest(getMyId());
	}
	private LoanRequest loanFile;
	public LoanRequest getLoanFile() {
		return loanFile;
	}
	public void setLoanFile(LoanRequest loanFile) {
		this.loanFile = loanFile;
	}
}
