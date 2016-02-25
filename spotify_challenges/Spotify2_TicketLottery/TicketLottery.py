# Ross Kahn 2012 
# Written and tested with Python version 2.6.6

import re
import string
from math import ceil
from math import factorial

# Finds the probability that, given a given group size, number of tickets each winner can buy,
# total number of people in the drawing, and the total number of possible winners, that everyone
# in the group can get a ticket (see section 0.0)
def findProbability(totalPeople, totalWinners, tixPerPerson, groupSize):
	
	# Minimum amount of winners required for your group. (see section 0.1)
	minRequiredWinners = ceil(float(groupSize)/float(tixPerPerson))
	minRequiredWinners = int(minRequiredWinners)
	
	# Final probability of your group getting tickets
	totalProbability = 0.00
	
	# Summation of probabilities for each winnerCount combination. 
	# winnerCount = number of winners for a given(see section 0.2)
	for winnerCount in range(minRequiredWinners, groupSize+1):
		
		# number of losers for the current number of winners (winnerCount)
		loserCount = groupSize - winnerCount
		
		# Numerator for winner probability for current winnerCount (see section 0.3)
		winnerProb = simplifyFactorialDivision(totalWinners,(totalWinners - winnerCount))
		
		# Numerator for loser probability for current winnerCount (see section 0.4)
		loserProb = simplifyFactorialDivision((totalPeople-totalWinners),(totalPeople-totalWinners-loserCount))
		print("loserProb: " + str(loserProb))
		
		# Denominator for both winner probability and loser probability (see section 0.5)
		probDenom = simplifyFactorialDivision((totalPeople - groupSize),totalPeople)
		
		# For each winnerCount, there are this many combinations of people that can satisfy the condition
		winnerCombinations = simplifyFactorialDivision(groupSize,(groupSize-winnerCount))/factorial(winnerCount)
			
		# The combined probability for winnerCount number of people to win tickets in any combination
		probFunction = winnerProb * loserProb * probDenom
		
		# Multiply the probability function by the number of combinations of people who can win. Then add to
		# the total probability calculated so far
		totalProbability = totalProbability + (winnerCombinations * probFunction)
		
	print("Probability: " + str(totalProbability))
#END findProbability	

	
# For very large factorial divisions, simplifies the factorial before the division so very
# large numbers do not need to be divided against each other (which can cause errors) 
def simplifyFactorialDivision(numerator, denominator):
	fraction = False
	
	# Create an array with all the numbers from 0-numerator
	numeratorArray = []
	for num in range(0,numerator+1):
		numeratorArray.append(num)
		
	# Create an array with all the numbers from 0-denominator
	denominatorArray = []
	for denom in range(0,denominator+1):
		denominatorArray.append(denom)
	
	# Find whether the numerator array or denominator array has more elements
	largerList = []
	smallerList = []
	if numerator > denominator:
		largerList = numeratorArray
		smallerList = denominatorArray
		fraction = False	# division will result in an integer
	elif denominator > numerator:
		largerList = denominatorArray
		smallerList = numeratorArray
		fraction = True		# division will result in a decimal
	else:
		pass
		
	# delete all overlapping elements from the larger list
	del largerList[:len(smallerList)]
	
	# whatever remains, multiply together
	factorialResult = 1
	for remainingNumber in largerList:
		factorialResult = factorialResult * remainingNumber
		
	if(fraction):
		return 1/float(factorialResult)
	else:
		return factorialResult
#END simplifyFactorialDivision
		
	
# Make sure that the input format is correct
def validateNumbers(numArray):
	if(len(numArray) != 4):
		return False
	
	totalPeople = numArray[0]
	totalWinners = numArray[1]
	tixPerPerson = numArray[2]
	groupSize = numArray[3]
	
	if not(1 <= totalPeople <= 1000):
		return False
	
	if not(1 <= totalWinners <= totalPeople):
		return False
		
	if not(1 <= tixPerPerson <= 100):
		return False
		
	if not(1 <= groupSize <= totalPeople):
		return False
		
	return True
#END validateNumbers
	
	
def main():

	while(True):
		try:
			inputStr = raw_input("\n: ")
		except EOFError:
			break

		inputStr = inputStr.strip()
		
		# Regular expression for '## ## ## ##' where ## is any integer
		pattern = re.compile("\d+\s*\d+\s*\d+\s*\d+$")
		
		if re.match(pattern, inputStr):
			
			# Isolates the four numbers, converts them from strings
			# to integers, validates, then passes to findProbability()
			strArray = inputStr.split(" ")
			numArray = [int(strVal) for strVal in strArray]
			if(validateNumbers(numArray)):
				findProbability(numArray[0], numArray[1], numArray[2], numArray[3])
			else:
				print("Input illegal. Input should be 4 integers 'm n t p'\n"+
					"1 <= m <= 1000: the total number of people who entered the lottery.\n"+
					"1 <= n <= m:    the total number of winners drawn.\n"+
					"1 <= t <= 100:  the number of tickets each winner is allowed to buy.\n"+
					"1 <= p <= m:    the number of people in your group.\n")					  
		else:
			# The input string did not match the A/B/C number format
			print(inputStr + "is illegal")
		
#END main


main()