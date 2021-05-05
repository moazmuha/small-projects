from typing import List, Dict

def add_sighting(sightings: Dict[str, int], new_sighting: str) -> None:
    '''
    Update the dictionary of sightings with the new_sighting.

    Exampes:
    >>> d = {'lion' : 5, 'rhino' : 1}
    >>> add_sighting(d, 'rhino')
    >>> d
    >>> {'lion' : 5, 'rhino' : 2}
    '''

    sightings[new_sighting] = sightings.get(new_sighting, 0) + 1
    
def invert_sightings(sightings: Dict[str, int])-> Dict[int, List[str]]:
    """
    Given a animal -> number of sightings dictionary, sightings, returns a
    new dictionary which given a number of sightings, gives a list of animals
    which have that sighting number.

    Examples:
    >>> d = {"lion" : 5, "rhino" : 1, "elephant" : 1}
    >>> invert_sightings(d)
    {5 : ["lion"], 1 : ["elephant", "rhino"]}
    """
    inverted = {}
    for animal in sightings:
        if sightings[animal] in inverted:
            inverted[sightings[animal]].append(animal)
        else:
            inverted[sightings[animal]] = [animal]
    return inverted

def invert(bdm: Dict[str, Dict[int, List[str]]]):
    '''
    Takes in a bdm month dictionary and returns and equivalent person
    to month dictionary. Assume names are unique.

    Examples:
    >>> bdm = {'February' : {13: ["Catherine",]},
	   'May' : {3 : ['Katie'], 8 : ['Peter', 'Ed']},
	   'December' : {20:['Hank']}}
    >>> invert(bdm)
        {"Catherine" : ["February, 13"],
        "Katie" : ["May", 3], "Peter": ["May", 8], "Ed": ["May", 8],
        ...}
    '''
    d={}
    for month in bdm:
        for day in bdm[month]:
            for name in bdm[month][day]:
                d[name] = [month, day]
    return d