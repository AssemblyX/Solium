/*
 * Copyright (c) 2015, AssemblyX and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of AssemblyX or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package net.assemblyx.solium.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Holds all records for an employee
 * extends employee
 * 
 * @author Kenneth Desormeaux
 *
 */
public class EmployeeRecords extends Employee{
	private String mTotalSales;
	private String mTotalVests;
	private Map<Integer, Record> mVest = new HashMap<Integer, Record>();
	private Map<Integer, Record> mSale = new HashMap<Integer, Record>();
	private Map<Integer, Record> mPerf = new HashMap<Integer, Record>();
	
	public void setTotalSales(double sales){
		mTotalSales = String.format("%.2f", sales);
	}
	
	public void setTotalVests(double vests) {
		mTotalVests = String.format("%.2f", vests); 
		
	}
	
	public void putVests(int date, Record record){
		mVest.put(date, record);
	}
	
	public void sortVests(){
		setVests(new TreeMap<Integer, Record>(getVests()));
	}
	
	public void setVests(Map<Integer, Record> records){
		mVest = records;
	}
	
	public void putSales(int date, Record record){
		mSale.put(date, record);
	}
	
	public void putPerfs(int date, Record record){
		mPerf.put(date, record);
	}
	
	public Map<Integer, Record> getVests(){
		return mVest;
	}
	
	public Map<Integer, Record> getSales(){
		return mSale;
	}
	
	public Map<Integer, Record> getPerfs(){
		return mPerf;
	}
	
	@Override
	public String toString(){
		String str = getEmployeeId() + "," + mTotalVests;
		if(mTotalSales != null)str += "," + mTotalSales;
		return str;
	}


}
