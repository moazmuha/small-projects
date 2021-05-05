from typing import List

def swap_min_to(L: List[int], k:int)-> None:
    """
    Takes in a list L, and swaps the minimum value L[k:] with L[k]

    >>> L = [5,8,1,3,0,1,2]
    >>> swap_min_to(L,2)
    >>> L
    [5,8,0,3,1,1,2]
    """
    current_min_index = k
    for i in range(k+1,len(L)):
        if L[i] < L[current_min_index]:
            current_min_index = i
    temp = L[current_min_index]
    L[current_min_index] = L[k]
    L[k] = temp
    
def selection_sort(L):
    for i in range(len(L)):
        swap_min_to(L,i)

def bubble_sort(L):
    end = len(L)-1
    while end != 0:
        for i in range(end):
            if L[i] > L[i+1]:
                L[i],L[i+1] = L[i+1],L[i]
        end -=1
    
def insertion_sort(L):
    for i in range(1,len(L)):
        j = i-1
        while L[j] > L[j+1] and j>=0:
            L[j], L[j+1] = L[j+1], L[j]
            j -= 1