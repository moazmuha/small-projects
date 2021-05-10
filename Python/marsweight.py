"""
Objectives:
1. Varaibles and Varaible Types
2. Assigning vs Using Variables 
3. Casting from one varaible type to another

What we already learnt:
Constants, using input(), print()
"""

# Mars weight multiple
MARS_MULTIPLE = 0.378

def main():
    """
    Prompt the user for their weight. Print out their weight on Mars.
    """
    # Ask the user for their weight
    earth_weight_str =  input('What is your weight: ')

    # Convert their weight to a float
    # '120.7'
    earth_weight = float(earth_weight_str)

    # Calculate their weight on Mars
    mars_weight = earth_weight * MARS_MULTIPLE

    # Print out what their weight on Mars is
    print("Your weight on Mars is " + str(mars_weight))



if __name__ == "__main__":
    main()
