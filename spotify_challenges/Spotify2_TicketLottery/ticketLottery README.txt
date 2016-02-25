DOCUMENTATION ON TicketLotter.py

**Section 0.0

# Finds the probability that, given a given group size, number of tickets each winner can buy, total number of people in the drawing, and the total number of possible winners, that everyone in the group can get a ticket.

#Let's assume that there are 5 people in your group, and each winner can buy 2 tickets. That means that the minimum number of people in your group that must win is 3 (minRequiredWinners calculates this number). On an abstract level, finding the probability that 3 people in your group will win tickets is not enough, because it does not cover all the possibilities... it is possible that 4 or even 5 people out of your group will win. Those probabilities need to be added to the total probability, even though the chances of that happening are smaller. This is why the calculations for probability are done multiple times for different values of winnerCount.

# If the number of winners of your group does not equal the number of people in the group, that means that some people of the group need to be losers (loserCount). The number of losers for a given winnerCount is equal to groupSize-winnerCount.

# Let's say we're calculating the probability at winnerCount = minRequiredWinners (first step). It looks something like this: [winner prob]*[winner prob]*[winner prob]*[loser prob]*[loser prob]


**Section 0.1

$ minRequiredWinners = math.ceil(groupSize/tixPerPerson)

# Minimum amount of winners required for your group.For example, If there is a group of 5 and each winner can purchase 2 tickets, at least 3 people need to win from your group



**Secton 0.2

$ for(wCombination = minRequiredWinner; wCombination <= groupSize; wCombination++):

# There are multiple combinations of people within the group that can win, depending on the minimum number of people needed to win. For instance, if a minimum of 3 group members are required to win for everyone in a 5 person group to get tickets, multiple combinations of group members exist when 3 people win, 4 people win, and all 5 people win. We need to consider each of these combinations. This is why the probability calculation is a summation done k=(groupsize - minRequiredWinners) times



**Section 0.3
