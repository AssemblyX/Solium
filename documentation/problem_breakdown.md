Three types of records, `VEST`, `PREF`, `SALE`

## Records

`VEST`, `employee`, `dateVest`, `units`, `grantPrice`

`PREF`, `employee`, `dateEffect`, `multiplier`

`SALE`, `employee`, `dateSale`, `amount`, `marketPrice`

## JSON


```javascript
// Template
{
	recordCount:x, 
	dateCut:"YYYMMDD", 
	marketPrice:x.xx, 
	employees:[
		{
			employeeId:"SSSS",
			vest:[{dateVest:"YYYMMDD", units:x, grantPrice:x.xx}], 
			pref:[{dateEffect:"YYYYMMDD", multiplier:x.x}], 
			sale:[{dateSale:"YYYYMMDD", amount:x, marketPrice:x.xx}]
		}
	]
}

/* Sample data in
10
VEST,001B,20120101,1000,0.45
VEST,002B,20120101,1500,0.45
VEST,001B,20120102,1000,0.45
VEST,002B,20120102,1000,0.45
SALE,001B,20120402,500,1.00
VEST,002B,20130101,1000,0.50
VEST,001B,20130101,1500,0.50
VEST,003B,20130101,1000,0.50
PERF,001B,20130102,1.5
PERF,002B,20130102,1.5
20140101,1.00
*/

// Sample JSONObject
{
	recordCount:10, 
	dateCut:"20140101", 
	marketPrice:1.00, 
	employees:[
		{
			employeeId:"001B",
			vest:[
				{dateVest:"20120101", units:1000, grantPrice:0.45},
				{dateVest:"20120102", units:1000, grantPrice:0.45},
				{dateVest:"20130101", units:1500, grantPrice:0.50}
			], 
			pref:[{dateEffect:"20130102", multiplier:1.5}], 
			sale:[{dateSale:"20120402", amount:500, marketPrice:1.00}]
		},
		{
			employeeId:"002B",
			vest:[
				{dateVest:"20120102", units:1000, grantPrice:0.45},
				{dateVest:"20130101", units:1000, grantPrice:0.50}
			], 
			pref:[{dateEffect:"20130102", multiplier:1.5}], 
			sale:[]
		}
		{
			employeeId:"003B",
			vest:[{dateVest:"20130101", units:1000, grantPrice:0.50}], 
			pref:[], 
			sale:[]
		}
	]
}
```
## Issues
1) Can there be multiple PREF and if so do they compound? For example if there are two PREF on 2010 and 2014. All units before 2010 would receive (0000to2010TotalUnits x 2010Pref x 2014Pref) + (2011to2014TotalUnits x 2014Pref)

2) Are SALE subtracted from the earliest VEST?



