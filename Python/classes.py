class Point:
    def __init__(self,x,y):
        self.x = x
        self.y = y

    def __str__(self):
        return "({},{})".format(self.x, self.y)

    def __repr__(self):
        return "Point({},{})".format(self.x, self.y)

    def translate_x(self,x):
        self.x += x

    def translate_y(self,y):
        self.x += y

    def translate(self,x,y):
        self.translate_x(x)
        self.translate_y(y)

    def __eq__(self,p):
        return self.x == p.x and self.y == p.y

    def __add__(self,p):
        return Point(self.x + p.x, self.y + p.y)

    def __sub__(self,p):
        return Point(self.x - p.x, self.y - p.y)

    def distance_from(self,p):
        return ((self.x-p.x)**2 + (self.y-p.y)**2)**0.5

    def distance_from_origin(self):
        return self.distance_from(Point(0,0))

class Segment:
    def __init__(self, p1, p2):
        self.start = p1
        self.end = p2

    def translate_x(self,x):
        self.start.translate_x(x)
        self.end.translate_x(x)

    def translate_y(self,y):
        self.start.translate_y(y)
        self.end.translate_y(y)

    def length(self):
        return self.start.distance_from(self.end)

class Circle():
    def __init__(self,radius,center):
        self.radius = radius
        self.center = center 

    def __eq__(self, c):
        return self.radius == c.radius and self.center == c.center 

    def __str__(self):
        s = "(x-{})**2 + (y-{})**2 = {}**2"
        return s.format(self.center.x,self.center.y,self.radius)

    def intersects(self,c):
        return self.radius + c.radius >= self.center.distance_from(c.center)
        
    def is_inside(self, c):
        return c.radius >= c.center.distance_from(self.center) + self.radius

