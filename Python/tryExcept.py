class StudentGrade:

    def __init__(self, num):
        self.set_num(num)


    def set_num(self, num):
        if num < 0:
            raise NegativeGradeException
        elif num > 100:
            raise TooSmartStudentException
        else:
            self._num = num  
            self._set_letter(num) 
            
    def _set_letter(self, num):
        if (num < 50):
            self._letter = "F" 
        elif (num < 60):
            self._letter = "D"
        elif (num < 70):
            self._letter = "C"
        elif (num < 80):
            self._letter = "B"
        else:
            self._letter = "A"

    def __repr__(self):
        return "{} - {}".format(self._num, self._letter)
    
class NegativeGradeException(Exception):
    pass

class TooSmartStudentException(Exception):
    pass


try:
    s = StudentGrade(80)
    num = input("Give me a grade you want to change this to: ")    
    s.set_num(int(num))

except ValueError: # "except" catches EVERY error; but I can specify which error I want to catch by naming it
    print("You gave a bad input. I'm not going to change the student's grade.")

except TooSmartStudentException:
    print("Congratulate this student.")
    s.set_num(100)

except NegativeGradeException:
    print("Oh no.")
    s.set_num(0)

except: # this will catch any error that's not caught
    print("Something wrong happened...")
    
finally: 
    print("happy day") # this happens regardless of whether or not an error was caught