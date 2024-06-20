CREATE TABLE IF NOT EXISTS students (
    studentId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    kana VARCHAR(50) NOT NULL,
    nickname VARCHAR(50),
    mail VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age int,
    sex VARCHAR(10),
    remark text,
    is_deleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses(
courseId INT PRIMARY KEY AUTO_INCREMENT,
studentId INT NOT NULL,
course_name VARCHAR(50) NOT NULL,
starting_Date TIMESTAMP,
scheduled_End_Date TIMESTAMP
);

CREATE TABLE application_status(
statusId INT PRIMARY KEY AUTO_INCREMENT,
courseId INT NOT NULL,
status VARCHAR(5) NOT NULL,
registration_date TIMESTAMP
);