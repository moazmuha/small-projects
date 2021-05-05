from __future__ import annotations
from typing import Dict, Any , Union, List

class Building:
    """A building

    === Attributes ===
    address: the address of this building
    num_rooms: the number of rooms in this building

    === Private Attributes ===
    _rooms:
        The rooms in this building. The key is the room name and
        the value is the room size in square feet

    === Representation Invariants ===
    - The size of a room must be greater than 0
    - num_rooms should be greater than 0

    === Sample Usage ===
    >>> building = Building('123 lane rd', 1)
    >>> building.create_room('room1', 100)
    >>> print(building)
    100
    >>> building.rename_room('room1', 'renovated_room')
    """
    addy: str
    num_rooms: int
    _rooms: Dict[str, int]

    def __init__(self, address: str, num_rooms: Union[List, int, str]) -> None:
        """Initialize this building

        >>> building = Building('123 lane rd', 1)
        >>> building.address
        '123 lane rd'
        >>>building.num_rooms
        1
        """
        self.address = address
        self.num_rooms = num_rooms
        self._rooms = {}

    def __str__(self):
        """Return a string(total size of building in square feet)
        representing this building

        >>> building = Building('123 lane rd', 1)
        >>> building.create_room('room1', 100)
        >>> print(building)
        100
        """
        size = 0
        for room in self._rooms:
            size += self._rooms[room]
        return str(size)

    def create_room(self, name: str, sq_ft: float):
        """Create a room in this building

        Create a room with name <name> and size <sq_ft>.

        Preconditions:
          - <sq_ft> must be greater than 0

        >>> building = Building('123 lane rd', 1)
        >>> building.create_room('room1', 100)
        """
        if len(self._rooms) < self.num_rooms:
            self._rooms[name] = sq_ft

    def rename_room(self, old_name, new_name):
        """Rename an existing room in the building

        Rename an existing room with <old_name>

        >>> building = Building('123 lane rd', 1)
        >>> building.create_room('room1', 100)
        >>> building.rename_room('room1, 'new_room')
        """
        self._rooms[new_name] = self._rooms.pop(old_name)


class House(Building):
    """A house building

    === Sample Usage ===
    >>> building = House('123 lane rd', 1)
    >>> building.create_room('room1', 100)
    >>> print(building)
    Welcome to our house.
    Rooms: room1: 100sq ft
    >>> building.rename_room('room1', 'renovated_room')
    """

    def __str__(self):
        """Return a string representing this house

        >>> building = House('123 lane rd', 2)
        >>> building.create_room('room1', 100)
        >>> building.create_room('room2', 110)
        >>> print(building)
        Welcome to our house.
        Rooms: room1: 100sq ft, room2: 110sq ft
        """
        rooms = 'Rooms: '
        for room in self._rooms:
            rooms += room + ": " + str(self._rooms[room]) + 'sq ft, '
        house = "Welcome to our house.\n" + rooms
        return house.strip(", ")


class Business(Building):
    """A business building

    === Sample Usage ===
    >>> building = Business('123 lane rd', 1)
    >>> building.create_room('room1', 100)
    >>> print(building)
    100
    >>> building.chnage_room_size('room1', 300)
    >>> print(building)
    300
    """

    def change_room_size(self, room: str, new_size: float):
        """Change an existing room's size

        Change a <room>'s size to <new_size>.

        Preconditions:
        - <room> must exist in the building

        >>> building = Business('123 lane rd', 1)
        >>> building.create_room('room1', 100)
        >>> print(building)
        100
        >>> building.chnage_room_size('room1', 300)
        >>> print(building)
        300
        """
        self._rooms[room] = new_size



if __name__ == "__main__":
    building = input("Type of Building: ")
    while building != 'house' and building != 'business' and building != 'building':
        print('ERROR: Invalid Type')
        building = input("Type of Building: ")
    addy = input("Address: ")
    num_rooms = int(input("Number of rooms: "))
    if building == 'house':
        while num_rooms > 10:
            print("ERROR: A house can't have more than 10 rooms")
            num_rooms = int(input("Number of rooms: "))
    rooms = {}
    for room_num in range(1, num_rooms+1):
        room_name = input("Room #" + str(room_num) + ' name: ')
        while room_name in rooms:
            print("ERROR: Room Names should be unique")
            room_name = input("Room #" + str(room_num) + ' name: ')
        if building == 'business':
            while room_name == 'bedroom':
                print("ERROR: You can't name a room 'bedroom' in a business building")
                room_name = input("Room #" + str(room_num) + ' name: ')
        room_size = int(input("Room #" + str(room_num) + " '" + room_name + "' size(sq ft): "))
        if building == 'business':
            while room_size < 100:
                print("ERROR: Rooms in a business building can not be smaller than 100 sq ft")
                room_size = int(input("Room #" + str(room_num) + " '" + room_name + "' size(sq ft): "))
        rooms[room_name] = room_size
    if building == 'building':
        your_building = Building(addy, num_rooms)
        for room in rooms:
            your_building.create_room(room, rooms[room])
        print('your_building has been created')
    elif building == 'house':
        your_building = House(addy, num_rooms)
        for room in rooms:
            your_building.create_room(room, rooms[room])
        print('your_building has been created')
    else:
        your_building = Business(addy, num_rooms)
        for room in rooms:
            your_building.create_room(room, rooms[room])
        print('your_building has been created')
















