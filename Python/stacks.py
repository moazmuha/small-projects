from typing import Generic, List, Optional, TypeVar, Any

###############################################################################
# Stacks
###############################################################################
class Stack:
    """A last-in-first-out (LIFO) stack of items.

    Stores data in a last-in, first-out order. When removing an item from the
    stack, the most recently-added item is the one that is removed.
    """
    _items: List

    def __init__(self) -> None:
        """Initialize a new empty stack."""
        self._items = []

    def is_empty(self) -> bool:
        """Return whether this stack contains no items.

        >>> s = Stack()
        >>> s.is_empty()
        True
        >>> s.push('hello')
        >>> s.is_empty()
        False
        """
        return self._items == []

    def push(self, item: Any) -> None:
        """Add a new element to the top of this stack."""

        self._items.append(item)

    def pop(self) -> Any:
        """Remove and return the element at the top of this stack.

        Raise EmptyStackError when this stack is empty.

        >>> s = Stack()
        >>> s.push('hello')
        >>> s.push('goodbye')
        >>> s.pop()
        'goodbye'
        """
        
        if self.is_empty():
            raise EmptyStackError
        else:
            return self._items.pop()

    def __repr__(self: 'Stack') -> None:
        """
        Return string representation of stack.
        """

        s = ''
        for item in self._items[::-1]:
            s += str(item) + '\n'

        return s.strip()


class EmptyStackError(Exception):
    """Exception raised when an error occurs."""
    pass

from stack import Stack, EmptyStackError

def is_balanced(s: str) -> bool:
  '''
  Return True iff s (consisting of '(' and ')' characters)
  is balanced.
  >>> is_balanced("(()()()())")
  True

  >>> is_balanced("(((())))")
  True

  >>> is_balanced("(()((())()))")
  True

  >>> is_balanced("()))")
  False

  >>> is_balanced("(()()(()")
  False
  '''

  brackets_stack = Stack()
  
  for char in s:
    
    if char == '(':
      brackets_stack.push(char)
      
    elif char == ')':
      # method 1:
      if (brackets_stack.is_empty()):
        return False
      else:
        brackets_stack.pop()

  return brackets_stack.is_empty()
