Three types of records, `VEST`, `PREF`, `SALE`

## Records

`VEST`, `employee`, `dateVest`, `vestAmount`, `grantPrice`

`PREF`, `employee`, `dateEffect`, `multiplier`

`SALE`, `employee`, `dateSale`, `amount`, `marketPrice`

## JSON

/* Template */
```javascript
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

//
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


