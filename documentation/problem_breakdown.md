Three types of records, `VEST`, `PREF`, `SALE`

## Records

`VEST`, `employee`, `dateVest`, `vestAmount`, `grantPrice`

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
			vest:[{dateVest:"YYYMMDD", grantPrice:x.xx}], 
			pref:[{dateEffect:"YYYYMMDD", multiplier:x.x}], 
			sale:[{dateSale:"YYYYMMDD", amount:x, marketPrice:x.xx}]
		}
	]
}

// Sample data in
5
VEST,001B,20120102,1000,0.45
SALE,001B,20120402,500,1.00
VEST,002B,20120102,1000,0.45
PERF,001B,20130102,1.5
PERF,002B,20130102,1.5
20140101,1.00


```


Objects | Name
--- | ---
record type |
stock option | 
grant price |
vesting period |
grant date |
vest date |
total gain |
stock amounts |
list of records |
employee |


