package com.test.sumofall;

public class PolicySet extends IdGenerator {

	public PolicySet(int parentId) {
		super(parentId);
		this.totalLoanAmt=1000.0;
		isSetTotalLoanAmt=true;
		interestRate=0.5;
		reviewLevel="none";
	}
	
	Boolean overallEligibilityIndicator;
	
	public Boolean getOverallEligibilityIndicator() {
		return overallEligibilityIndicator;
	}

	public void setOverallEligibilityIndicator(Boolean overallEligibilityIndicator) {
		this.overallEligibilityIndicator = overallEligibilityIndicator;
	}

	public String getReviewLevel() {
		return reviewLevel;
	}

	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	String reviewLevel;
	
	Double interestRate;
	
	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	private boolean isSetTotalLoanAmt;
	public boolean getIsSetTotalLoanAmt()
	{
		return isSetTotalLoanAmt;
	}
	private Double totalLoanAmt;

	public Double getTotalLoanAmt() {
		return totalLoanAmt;
	}

	public void setTotalLoanAmt(Double totalLoanAmt) {
		this.totalLoanAmt = totalLoanAmt;
	}
}
