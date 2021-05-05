import random
from typing import List, Dict

def can_drink_beer(age: int, country_name: str, contains_alcohol: bool) -> bool:
    """
    age: a non negative integer, person's age
    country_name: a string, country name
    contains_alcohol: True if beer contains alcohol, False if not
    return: True if contains_alcohol is False, regardless of age and country,
            If contains_alcohol is True:
              False if country_name is 'USA' and age is less than 25
              True if country_name is 'USA' and age is greater or equal to 25
              True if country name is 'Beerland' regardless of age
              False if age is less than 16 and country anything else
            Otherwise True 
    """
    if age <=0:
	    return "Error"
    elif contains_alcohol == False:
        return True
    elif country_name == "USA":
        if age < 25:
            return False
        return True 
    elif country_name == "Beerland":
        return True
    elif age < 16:
        return False
    else:
        return True 
    
def bin_str_to_dec(b:str) -> int:
    """
    Takes in a binary string b and returns the decimal value, considering the left
    most digit as the least significant digit.

    Examples
    >>> bin_str_to_dec('01011')
    11
    >>> bin_str_to_dec('10')
    2
    """

    dec = 0
    c = 2**0
    b_rev = b[::-1]
    for dig in b_rev:
        if dig == '1':
            dec = dec + c
        c = c * 2
    return dec 

def guess_game():
    i = random.randint(1,50)
    guess = int(input('Guess: '))
    while guess != i:
        if i < guess:
            print('the # is less than', guess)
        else:
            print('the # is more than', guess)
        guess = int(input('Sorry , guess again:'))
    print('You Guessed it!!')

def average_even(L: List[int]) -> int:
    even_list = []
    for number in L:
        if number%2 == 0:
            even_list.append(number)
    if len(even_list) > 0:
        return int(sum(even_list) / len(even_list))
    return 0

def most_covered(bdm: Dict[str, Dict[int, List[str]]]):
    '''
    Takes in a bdm dictionary and returns one of the months which has
    thegreatest  number of days with birthdays on it.

    Examples:
    >>> bdm = {'February' : {13: ["Catherine",'Moaz','Sarah']},
	   'May' : {3 : ['Katie'], 8 : ['Peter', 'Ed']},
	   'December' : {20:['Hank']}}
    >>> most_covered(bdm)
    "May"
    '''
    most_covered = ''
    value = 0
    for month in bdm:
        if len(bdm[month]) > value:
            value = len(bdm[month])
            most_covered = month
    return most_covered