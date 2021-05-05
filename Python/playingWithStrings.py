# using a for loop to count vowels in a string
def count_vowels(s):
	num = 0
	for char in s:
		if char in "aeiouAEIOU":
			num += 1
	return num

# using a while loop to count vowels in a string
def count_vowels2(s):
    num = 0
    index = 0
    while index < len(s):
        if s[index] in 'aeiouAEIOU':
            num += 1
        index += 1
    return num

def make_angry(s):
    s = s.upper()
    new_s=''
    for char in s:
        if char != '.':
            new_s += char
        elif char == '.':
            new_s += '!'
    return new_s
    
def count_sentences(s):
    """
    Returns the number of sentences which occur in a string s.
    Assume a sentence is any substring 
    begins with a capital letter and completes the first time o
    ne of ".", "!", or "?" occurs after said capital letter.

    Examples:
    >>> count_sentences("I like dogs. You like cats? I hate snakes!")
    3
    >>> count_sentences("i type like child!")
    0
    >>> count_sentences("Hmm, maybe...")
    1
    >>> count_sentences("this is a weIrd case?")
    1
    """
    num = 0
    index = -1
    for char in s:
        index += 1
        if char.isupper():
            if s.find('.', index)>-1 or s.find('!', index)>-1 or s.find('?', index)>-1:
                num +=1
    return num

def format_course_title(s:str) -> str:
    '''
    Returns s but shortens each word over 4 characters to just be the first
    characters of the word with a "." at the end.

    Examples:
    >>> format_course_title("Introduction to Programming")
    "Intr. to Comp. Prog."
    >>> format_course_title("Advanced Calculus")
    "Adva. Calc."
    '''
    word=''
    count=0
    new_string = ''

    for char in s:
        word += char
        count += 1
        if char == ' ' or count == len(s):
            if len(word)<= 4:
                new_string += word + ' '
                word = ''
            else:
                new_string += word[:4] + '.' + " "
                word = ''
    return new_string.strip()

def pothole_to_camel(pothole):
    """ (str) -> str
    Return a camel case version of the string pothole.
    pothole is guaranteed not to end with an underscore.
    >>> pothole_to_camel(’computer_science’)
    ’computerScience’
    >>> pothole_to_camel(’num_vowels_3_0’)
    ’numVowels30’
    """
    
    new_s=''
    i = 0

    while i < len(pothole):
        if pothole[i] == '_':
            new_s += pothole[i+1].upper()
            i += 1
        else:
            new_s += pothole[i]
        i += 1
    return new_s

def is_ordered(s:str) -> bool:
    """
    Return True if and only if s in in non-decreasing order
    based of the ord() function

    Examples:
    >>> is_ordered('abc')
    True
    >>> is_ordered("aBc")
    False
    """
    last_value = -1
    for char in s:
        if ord(char) < last_value:
            return False
        last_value = ord(char)
    return True 

# Encoding and decoding strings
def classic_encode(s:str, key:int)-> str:
    """
    Takes in a string containing only lowercase a-z letters, s, and encodes it based off an offset value
    key, following a classic Caesar cipher. See the examples below for how to handle key values greater than 26

    Examples:
    >>> classic_encode("abc",3)
    "def"
    >>> classic_encode("xyz",5)
    "cde"
    >>> classic_encode("abc",26)
    "abc"
    >>> classic_encode("abc",53)
    "bcd"
    """
    new_s = ''
    for char in s:
        if key >= 26:
            key = key%26
        new_s += chr(ord(char) + key)
    return new_s

def encode(x:str, y:int)-> str:
    return chr(ord(x) + y)

def encode_string(x:str, y:int) -> str:
    string = ''
    for char in x:
        string += encode(char, y)
    return string

def decode_string(x:str, y:int) -> str:
    string = ''
    for char in x:
        string += chr(ord(char) - y)
    return string 
    
def encode_char(c:str, key:int)->str:
    return chr(ord(c)+key)

def encode(s:str, key:int):
    code = ""
    for char in s:
        code = code + encode_char(char, key)
    return code

def encode1(s:str, key:int):
    code = ""
    for char in s:
        code = code + encode_char(char, key)
        print(code)
    return code

def decode_char(c:str, key:int)->str:
    return chr(ord(c)-key)

def decode(s:str, key:int):
    decode = ""
    for char in s:
        decode = decode + decode_char(char, key)
    return decode

def encode_mm(c:str, key: int) ->str:
    code = ''
    for s in c:
        code = code + encode_char(s, key)
    return code


def encode_str(x:str, y:int) -> str:
    string=""
    for char in x:
        string += chr(ord(char)+y)
    return string 