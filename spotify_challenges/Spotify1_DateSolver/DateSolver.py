# Ross Kahn December 27, 2011
# Written with version 311 and converted to version 264 

import re
import string

# Constant, referencing how many days each month has. Dummy element put at
# position 0 so that the real month number can be used without normalizing it
# to an index (by subtracting 1) in the program
NUM_DAYS_IN_MONTH = [None,31,28,31,30,31,30,31,31,30,31,30,31]
	
# Determines if a year is a leap year
def isLeap(yearVal):
	div4 = (yearVal % 4 == 0)
	div100 = (yearVal % 100 == 0)
	div400 = (yearVal % 400 == 0)
	if(div4 and not div100):
		return True
	elif(div4 and div100):
		if div400:
			return True
		else:
			return False
	else:
		return False
#END
	
# Returns True if the given day/month/year configuration is a valid day (meaning
# the day vaue doesn't exceed the month's corresponding NUM_DAYS_IN_MONTH value
def checkValidDay(dayVal, monthVal, yearVal):
	# Python does not check second value if first value returns False,
	# so isLeap() will not be called needlessly
	if(monthVal == 2 and isLeap(yearVal)):
		return (dayVal <= 29)
	else:
		return (dayVal <= NUM_DAYS_IN_MONTH[monthVal])
#END

# For an array of 3, will have at worst case 2 swapping operations for sort
def shortSort(array):
	temp = None
	if(array[0] > array[2]):
		array = switchValues(array,0,2)
		
	if(array[0] > array[1]):
		array = switchValues(array,0,1)
	
	if(array[1] > array[2]):
		array = switchValues(array,1,2)
		
	return array
#END

# Switches two index values in a given array
def switchValues(array, index1, index2):
	temp = array[index1]
	array[index1] = array[index2]
	array[index2] = temp
	return array
#END

def getLowestDate(origDate, dateNumbers):
	yearVal = None
	monthVal = None
	dayVal = None			
	weightedYear = False	# True if 2000 needs to be added to the year field
	
	# Returns a sorted list of the numbers, from low to high
	dateNumbers = shortSort(dateNumbers)
	
	while len(dateNumbers) > 0:
		maxNum = dateNumbers.pop()	
		
		# Try to give Day the highest value. If it's already been filled, give it to month (if
		# day has already been filled, month is guaranteed to be equal or lower, because we're
		# going in high-low order). ONLY if the value is too big for day or month, put it in year.

		if((dayVal == None) and (0 < maxNum <= 31)):
			dayVal = maxNum
			
		elif((monthVal == None) and (0 < maxNum <= 12)):
			monthVal = maxNum
		
		elif((yearVal == None) and (0 <= maxNum < 999)): 
			yearVal = maxNum + 2000
			weightedYear = True
			
		elif ((yearVal == None) and (2000 <= maxNum <= 2999)):
			yearVal = maxNum
			
	# True if there were no date numbers out of range
	if(monthVal and dayVal and yearVal):	
		print("TRY: " + str(yearVal) + "-" + str(monthVal) + "-" + str(dayVal))
		# The date procured is valid in terms of month days and leap years. Print.
		if(checkValidDay(dayVal, monthVal, yearVal)):
			print(str(yearVal) + "-" + str(monthVal) + "-" + str(dayVal))
		
		# The date procured is invalid for one reason or another
		else:
		
			# If a weight was put on the year before, take it off for reprocessing
			if weightedYear:
				yearVal = yearVal - 2000 
			
			# If the unweighted year is in a valid month range, check to see if they
			# can be switched. Since the month value will always be less than or equal
			# to the day value, this will give the lowest possible date. If year and
			# month can't be switched, try year and day. If that also does not work,
			# the date is illegal
			if (0 < yearVal <= 12) and checkValidDay(dayVal,yearVal,monthVal):
				temp = yearVal		# Swap year and month values
				yearVal = monthVal
				monthVal = temp
				yearVal += 2000		# Put weight on year value
				print(str(yearVal) + "-" + str(monthVal) + "-" + str(dayVal))
			elif (0 < yearVal <= 31) and checkValidDay(yearVal, monthVal, dayVal):
				temp = yearVal		# Swap year and day values
				yearVal = dayVal
				dayVal = temp
				yearVal += 2000		# Put weight on year value
				print(str(yearVal) + "-" + str(monthVal) + "-" + str(dayVal))
			else:
				print(origDate + " is illegal")
				
	# One or more date numbers were out of range (e.g. a year value of 3000)
	else:
		print(origDate + " is illegal")
			
#END getLowestDate()
	
# Main loop. Loops on user input and then processes the input
def begin():
	
	# Will loop forever on stdin until EOF is found
	while(True):
	
		try:
			date = raw_input("\n")
		except EOFError:
			break

		date = date.strip() 
		
		# Regular expression for 'A/B/C' format where A, B, and C are 1 or more numbers.
		# Ignores whitespace.
		pattern = re.compile("\d+\s*/\s*\d+\s*/\s*\d+$")

		if re.match(pattern, date):
		
			# Isolates the three date numbers, converts them from strings
			# to integers, passes to getLowestDate()
			strArray = date.split("/")
			numArray = [int(strVal) for strVal in strArray]
			getLowestDate(date, numArray)
		else:
			# The input string did not match the A/B/C number format
			print(date + "is illegal")
#END begin()
	
begin()