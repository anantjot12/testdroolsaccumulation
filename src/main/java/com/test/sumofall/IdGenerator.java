package com.test.sumofall;

import java.util.HashSet;

public class IdGenerator
{
	protected static int id;
	static{id = 0;}
	private HashSet<String> firings = new HashSet<String>();
	public HashSet<String> getFirings(){if (firings == null) firings = new HashSet<String>(); return firings;}
	public void setFirings(HashSet<String> x){firings = x;}
	private  int parentId;
	private final int myId;
	public int getMyId() { return myId;}
	public int getParentId() { return parentId;}
	public void setParentId(int parentId) { this.parentId = parentId;}
	static final int maxId = 1024 * 1024 * 1024 ;


	protected IdGenerator(int parentId)
	{
		myId = ++id;
		if (id > maxId)
			id = 1;
		

		this.parentId = parentId; 
	}
	
}

