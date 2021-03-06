
CREATE TABLE IF NOT EXISTS Campus( 
    CampusID INT PRIMARY KEY, 
    Name VARCHAR(100) NOT NULL,
    Address VARCHAR(100) NOT NULL UNIQUE
);

Create TABLE IF NOT EXISTS Person(
    UtorID INT PRIMARY KEY,
    MainCampus INT NOT NULl,
    SIN numeric(10) UNIQUE,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    CONSTRAINT PERSONCAMPUS FOREIGN KEY (MainCampus) REFERENCES Campus(CampusID)
);

CREATE TYPE stat AS ENUM('full-time', 'part-time');
CREATE TYPE title AS ENUM('Faculty member', 'Staff','Librarian');
CREATE TABLE IF NOT EXISTS Employee(
    EID INT PRIMARY KEY,
    UtorID INT NOT NULL UNIQUE,
    PersonnelNum numeric(10) NOT NULL UNIQUE,
    Status stat NOT NULL,
    Role  title NOT NULL,
    CONSTRAINT EMPLOYEEUTORID_FK FOREIGN KEY (UtorID) REFERENCES Person(UtorID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Student(
    StudentID INT PRIMARY KEY,
    UtorID INT NOT NULL UNIQUE,
    EmergencyNum numeric(10) NOT NULL,
    EmergencyContact VARCHAR(100) NOT NULL,
    Status stat NOT NULL,
    CONSTRAINT STUDENTUTORID FOREIGN KEY (UtorID) REFERENCES Person(UtorID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Faculty( 
    PhoneNumber VARCHAR(30) PRIMARY KEY, 
    Name VARCHAR(100) NOT NULL,
    Campus INT NOT NULL,
    Dean INT NOT NULL,
    CONSTRAINT FACULTYCAMPUS_FK FOREIGN KEY (Campus) REFERENCES Campus(CampusID),
    CONSTRAINT FACULTYDEAN_FK FOREIGN KEY (Dean) REFERENCES Employee(EID)
);

CREATE TABLE IF NOT EXISTS Department(
    DepartmentID INT PRIMARY KEY,
    PhoneNumber VARCHAR(30) NOT NULL,
    Chair INT NOT NULL,
    Faculty VARCHAR(100) NOT NULL,
    Campus INT NOT NULL,
    CONSTRAINT DEPARTMENTCHAIR_FK FOREIGN KEY (Chair) REFERENCES Employee(EID),
    CONSTRAINT DEPARTMENTFACULTY_FK FOREIGN KEY (Faculty) REFERENCES Faculty(PhoneNumber),
    CONSTRAINT DEPARTMENTCAMPU_FK FOREIGN KEY (Campus) REFERENCES Campus(CampusID)
);

-- Job is a mini job description
CREATE TABLE IF NOT EXISTS WorksFor(
    Employee INT NOT NULL,
    Department INT NOT NULL,
    Job VARCHAR(100) NOT NULL,
    CONSTRAINT WORKSEMPLOYEE_FK FOREIGN KEY (Employee) REFERENCES Employee(EID) ON DELETE CASCADE,
    CONSTRAINT WORKSDEPARTMENT_FK FOREIGN KEY (Department) REFERENCES Department(DepartmentID) ON DELETE CASCADE,
    PRIMARY KEY(Employee, Department)
);

CREATE TABLE IF NOT EXISTS Professor(
    PID INT PRIMARY KEY,
    UtorID INT NOT NULL UNIQUE,
    Type VARCHAR(20) NOT NULL,
    Rank VARCHAR(20) NOT NULL,
    CONSTRAINT PROFID_FK FOREIGN KEY (PID) REFERENCES Employee(EID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Course(
    CourseCode VARCHAR(10) NOT NULL,
    Department INT NOT NULL,
    Session VARCHAR(20) NOT NULL,
    Coordinator INT NOT NULL,
    CONSTRAINT COURSEDEPARTMENT_FK FOREIGN KEY (Department) REFERENCES Department(DepartmentID) ON DELETE CASCADE,
    CONSTRAINT COURSECOORDINATOR_FK FOREIGN KEY (Coordinator) REFERENCES Professor(PID),
    PRIMARY KEY(CourseCode,Department,Session)
);

CREATE TABLE IF NOT EXISTS LectureSection(
    Course VARCHAR(10) NOT NULL,
    Department INT NOT NULL,
    Session VARCHAR(20) NOT NULL,
    StartTime TIME(0) NOT NULL,
    EndTime TIME(0) NOT NULL,
    Days VARCHAR(30) NOT NULL,
    Lecturer INT NOT NULL,
    CONSTRAINT LECTURECOURSE_FK FOREIGN KEY (Course, Department, Session) REFERENCES Course(CourseCode, Department, Session) ON DELETE CASCADE,
    CONSTRAINT LECTURELECTURER_FK FOREIGN KEY (Lecturer) REFERENCES Professor(PID) ON DELETE CASCADE,
    PRIMARY KEY(Course, Department, Session, StartTime, EndTime)
);

CREATE TABLE IF NOT EXISTS TeachingAssistant(
    TAID INT PRIMARY KEY,
    UtorID INT NOT NULL UNIQUE,
    StudentID INT UNIQUE,
    ContractHours INT NOT NULL,
    CONSTRAINT TAID_FK FOREIGN KEY (TAID) REFERENCES Employee(EID) ON DELETE CASCADE, 
    CONSTRAINT TASTUDENTID_FK FOREIGN KEY (StudentID) REFERENCES Student(StudentID) ON DELETE SET NULL
);

-- A TA may support multiple courses and each one has a set number of hours from ContractHours
CREATE TABLE IF NOT EXISTS Supports(
    TA INT NOT NULL,
    Hours INT NOT NULL, 
    Course VARCHAR(10) NOT NULL,
    Department INT NOT NULL,
    Session VARCHAR(20) NOT NULL,
    CONSTRAINT SUPPORTSCOURSE_FK FOREIGN KEY (Course, Department, Session) REFERENCES Course(CourseCode, Department, Session) ON DELETE CASCADE,
    CONSTRAINT SUPPORTSEID_FK FOREIGN KEY (TA) REFERENCES TeachingAssistant(TAID) ON DELETE CASCADE,
    PRIMARY KEY(TA, Course, Department, Session)
);

CREATE TABLE IF NOT EXISTS EnrolledIn(
    Student INT NOT NULL,
    Course VARCHAR(10) NOT NULL,
    Department INT NOT NULL,
    Session VARCHAR(20) NOT NULL,
    CONSTRAINT ENROLLEDCOURSE_FK FOREIGN KEY (Course, Department, Session) REFERENCES Course(CourseCode, Department, Session) ON DELETE CASCADE,
    CONSTRAINT ENROLLEDSTUDENT_FK FOREIGN KEY (Student) REFERENCES Student(StudentID) ON DELETE CASCADE,
    PRIMARY KEY(Student, Course, Department, Session)
);

CREATE TYPE grade AS ENUM('A+', 'A', 'A-','B+', 'B', 'B-', 'C+', 'C', 'C-', 'D+', 'D', 'D-', 'F');
CREATE TABLE IF NOT EXISTS Grades(
    Professor INT NOT NULL,
    Student INT NOT NULL,
    Course VARCHAR(10) NOT NULL,
    Department INT NOT NULL,
    Session VARCHAR(20) NOT NULL,
    LetterGrade grade NOT NULL,
    NumGrade INT NOT NULL CHECK (NumGrade <= 100 AND NumGrade >= 0),
    CONSTRAINT ENROLLEDCOURSE_FK FOREIGN KEY (Course, Department, Session) REFERENCES Course(CourseCode, Department, Session),
    PRIMARY KEY(Student, Course, Department, Session, NumGrade)
);