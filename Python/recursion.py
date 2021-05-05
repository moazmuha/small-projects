
from __future__ import annotations
from typing import Any, List

def list_to_dict(lst: list) -> dict:
    '''
    Given a list L that may have sublists nested within it at an arbitrary
    depth, return a dictionary that has all the values from the list as its
    values, and the associated keys for each value are the indices that
    we would need to go through to get to that value. The key is represented
    as a string as exemplified below.
    
    e.g. In the list shown below, the value 'a' is at L[0] so
    the key for this value in the dictionary returned is just '0'.
    The value 'd' is at index L[2][1] so the key for this value is
    '2->1'. The value 'e' is at index L[2][2][0], so the key for this
    value is '2->2->0' and so on.

    Note: Your code MUST be recursive

    >> L = ['a', 'b', ['c', 'd', ['e']]]
    >> list_to_dict(L)
    {'0': 'a', '1': 'b', '2->0': 'c', '2->1': 'd', '2->2->0': 'e'}
    '''

    d = {}
    i = -1
    for elm in lst:
        i += 1
        if not isinstance(elm, list):
            d[str(i)] = elm
        else:
            new_d = list_to_dict(elm)
            new_i = -1
            for new_elm in new_d:
                new_i += 1
                if '->' not in new_elm:
                    d["{}->{}".format(i, new_i)] = new_d[new_elm]
                else:
                    string = str(i) + '->' + new_elm
                    d[string] = new_d[new_elm]
    return d

class LinkedListNode:

    def __init__(self, items: List = []) -> None:
        """
        Create a new linked list containing the elements in items.
        If items is empty, self.first initialized to EmptyValue.
        """
        
        if len(items) == 0:  # base case: empty list
            self.first = None
        elif len(items) == 1:
            self.first = items[0]
            self.next = None
        else:
            self.first = items[0] # initializes first item
            self.next = LinkedListNode(items[1:]) # creates new list with rest of items


    def __repr__(self) -> str:
        """
        Return a detailed str representation of Node.
        """
        
        if not self.next:
            return '({})'.format(repr(self.first))
        else:
            return '({}, {})'.format(repr(self.first), repr(self.next))

    def __str__(self) -> str:
        """
        Return a detailed str representation of Node.
        """

        return '{} -> {}'.format(str(self.first), str(self.next))
       

    def is_empty(self) -> bool:
        """
        Return True iff this list is empty.
        """
        
        return self.first is None

    def __getitem__(self, index: int) -> Any:
        """
        Return the item at position <index> in this list.
        Raise IndexError if <index> is >= the length of this list.
        """

        if self.first is not None:
            if index == 0:
                return self.first
            else:
                if self.next:
                    return self.next[index-1]
        raise IndexError


def reverse_list(head: LinkedListNode) -> LinkedListNode:
    '''
    Reverse the list that starts with the given <head> as its first node,
    and return the new head of the list.

    >>> ll = LinkedListNode([1, 2, 3])
    >>> ll = reverse_list(ll)
    >>> print(ll)
    3 -> 2 -> 1 -> None
    '''
    if head.is_empty():
        return head
    if head.next is None:
        return head
    else:
        curr = head
        saved = head.first
        while curr.next:
            curr.next.first, saved = saved, curr.next.first
            curr = curr.next
        head.first = saved
        reverse_list(head.next)
    return head

# recursion practice 

def sumlist(lst: list):
    s = 0
    for elm in lst:
        if isinstance(elm, int):
            s += elm
        else:
            s += sumlist(elm)
    return s

def sumlist_odd(lst: list):
    s = 0
    for elm in lst:
        if isinstance(elm, int):
            if elm % 2 != 0:
                s += elm
        else:
            s += sumlist_odd(elm)
    return s

def count_odd(lst: list):
    c = 0
    for elm in lst:
        if isinstance(elm, int):
            if elm % 2 != 0:
                c += 1
        else:
            c += count_odd(elm)
    return c

def get_odd(lst: list):
    new_l = []
    for elm in lst:
        if isinstance(elm, int):
            if elm % 2 != 0:
                new_l.append(elm)
        else:
            new_l.extend(get_odd(elm))
    return new_l

def add_one(lst: list) -> None:
    i = -1
    for elm in lst:
        i += 1
        if isinstance(elm, int):
            lst[i] = lst[i] + 1
        else:
            add_one(elm)

def codes(n: int):
    l = []
    if n == 0:
        return l
    elif n==1:
        l.append('1')
        l.append('0')
        return l
    else:
        smaller = codes(n-1)
        for code in smaller:
            l.append(code + '0')
            l.append(code + '1')
        return l

def contains(lst, v):
    if lst == []:
        return False
    elif isinstance(lst[0], int):
        return lst[0] == v or contains(lst[1:], v)
    else:
        return contains(lst[0], v) or contains(lst[1:], v)

def remove_three(lst):
    l = []
    if lst == []:
        return l
    elif isinstance(lst[0], int):
        if lst[0] != 3:
            l.append(lst[0])
        return l + remove_three(lst[1:])
    else:
        l.append(remove_three(lst[0]))
        return l

def skip_sum(n):
    if n == 0:
        return 0
    return n + skip_sum(n - 2)

def rec_max(lst):
  '''(list of int, can be nested) -> int
  Return max number in possibly nested list of numbers.

  >>> rec_max([17, 21, 0])
  21
  >>> rec_max([17, [21, 24], 0])
  24

  Precondition: lst is not empty
  '''
  nums = []
  for element in lst:
    if isinstance(element, int):
      nums.append(element)
    else:
      nums.append(rec_max(element))
  return max(nums)

def count_people(L: list) -> int:
    '''
    Count the number of people in a list of people's names.
    '''
    if L == []:
        return 0
    else:
        return 1 + count_people(L[1:])

