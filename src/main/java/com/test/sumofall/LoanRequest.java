package com.test.sumofall;

public class LoanRequest extends IdGenerator {

	protected LoanRequest(int parentId) {
		super(parentId);
		this.transactionDetail = new TransactionDetail(getMyId());
	}
	
	private TransactionDetail transactionDetail;

	public TransactionDetail getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(TransactionDetail transactionDetail) {
		this.transactionDetail = transactionDetail;
	}

}
