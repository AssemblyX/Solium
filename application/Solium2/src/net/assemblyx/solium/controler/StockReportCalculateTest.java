package net.assemblyx.solium.controler;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.assemblyx.solium.model.Record;

import org.junit.Test;

public class StockReportCalculateTest {

	@Test
	public void calculateVests() {
		Map<Integer, Record> rs = new HashMap<Integer, Record>();
		Record r = new Record();
		r.setPrice(0.45);
		r.setUnits(1000);
		rs.put(20120101, r);
		r = new Record();
		r.setPrice(0.35);
		r.setUnits(1000);
		rs.put(20130101, r);
		r = new Record();
		r.setPrice(0.25);
		r.setUnits(1000);
		rs.put(20140101, r);
		
		StockReportCalculate src = new StockReportCalculate();
		double t = src.calculateVests(rs, 1.00, 20130101);
		assertEquals(1200, t, 0);
	}
	
	@Test
	public void calculatePerformance(){
		Map<Integer, Record> rsVest = new HashMap<Integer, Record>();
		Record r = new Record();
		r.setPrice(0.50);
		r.setUnits(1000);
		rsVest.put(20120101, r);
		r = new Record();
		r.setPrice(0.50);
		r.setUnits(1000);
		rsVest.put(20130101, r);
		r = new Record();
		r.setPrice(0.50);
		r.setUnits(1000);
		rsVest.put(20140101, r);
		r = new Record();
		r.setPrice(0.50);
		r.setUnits(1000);
		rsVest.put(20140101, r);
		r = new Record();
		r.setPrice(0.50);
		r.setUnits(1000);
		rsVest.put(20150101, r);
		
		Map<Integer, Record> rsPerm = new HashMap<Integer, Record>();
		r = new Record();
		r.setMulitply(1.5);
		rsPerm.put(20130101, r);
		r = new Record();
		r.setMulitply(1.5);
		rsPerm.put(20140101, r);
		
		StockReportCalculate src = new StockReportCalculate();
		src.calculatePerformance(rsVest, rsPerm, 20140101);
		
		assertEquals(2250, rsVest.get(20120101).getUnits());
		assertEquals(2250, rsVest.get(20130101).getUnits());
		assertEquals(1500, rsVest.get(20140101).getUnits());
		assertEquals(1000, rsVest.get(20150101).getUnits());
	}
	
	@Test
	public void caluclateSales(){
		Map<Integer, Record> rsVest = new HashMap<Integer, Record>();
		Record r = new Record();
		r.setPrice(0.45);
		r.setUnits(1000);
		rsVest.put(20120101, r);
		r = new Record();
		r.setPrice(0.45);
		r.setUnits(1000);
		rsVest.put(20130101, r);
		r = new Record();
		r.setPrice(0.45);
		r.setUnits(1000);
		rsVest.put(20140101, r);
		r = new Record();
		r.setPrice(0.45);
		r.setUnits(1000);
		rsVest.put(20140102, r);
		r = new Record();
		r.setPrice(0.45);
		r.setUnits(1000);
		rsVest.put(20150101, r);
		rsVest = new TreeMap<Integer, Record>(rsVest);
		
		Map<Integer, Record> rsSale = new HashMap<Integer, Record>();
		r = new Record();
		r.setPrice(1.00);
		r.setUnits(500);
		rsSale.put(20130101, r);
		r = new Record();
		r.setPrice(1.00);
		r.setUnits(500);
		rsSale.put(20140101, r);
		r = new Record();
		r.setPrice(1.00);
		r.setUnits(1200);
		rsSale.put(20120101, r);
		
		StockReportCalculate src = new StockReportCalculate();
		double t = src.calculateSales(rsVest, rsSale, 20140101);
		assertEquals(1210, t, 0);
		assertEquals(0, rsVest.get(20120101).getUnits());
		assertEquals(0, rsVest.get(20130101).getUnits());
		assertEquals(800, rsVest.get(20140101).getUnits());
	}
}
