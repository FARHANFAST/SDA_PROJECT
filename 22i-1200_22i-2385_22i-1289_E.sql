USE demodb;

CREATE TABLE Customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    contact VARCHAR(15),
    address VARCHAR(255),
    password VARCHAR(255)
);

INSERT INTO Customer (name, email, contact, address, password)
VALUES 
    ('Alice Johnson', 'alice.johnson@example.com', '1234567890', '123 Main St, Springfield', 'password123'),
    ('Bob Smith', 'bob.smith@example.com', '9876543210', '456 Elm St, Metropolis', 'qwerty456'),
    ('Carol Lee', 'carol.lee@example.com', '5432167890', '789 Oak St, Gotham', 'securepass789'),
    ('David Brown', 'david.brown@example.com', '6789054321', '101 Pine St, Star City', 'david123'),
    ('Eve Davis', 'eve.davis@example.com', '1230984567', '202 Maple St, Central City', 'eve2024');


SELECT* FROM Customer;

CREATE TABLE PaymentAccounts (
    AccountNumber VARCHAR(20) PRIMARY KEY,
    Password VARCHAR(50) NOT NULL,
    Balance DOUBLE NOT NULL,
    CustomerEmail VARCHAR(255)
);

SELECT* FROM PaymentAccounts;

CREATE TABLE Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100),
    email VARCHAR(100),
    contact VARCHAR(15),
    address VARCHAR(255),
    password VARCHAR(255)
);
INSERT INTO Admin (name, email, contact, address, password)
VALUES 
    ('John', 'john@example.com', '1112223333', '100 Admin Blvd, Springfield', 'adminpass123'),
    ('Jane', 'jane@example.com', '4445556666', '200 Admin St, Metropolis', 'secureadmin456'),
    ('Mike', 'mike@example.com', '7778889999', '300 Admin Ave, Gotham', 'admin789'),
    ('Emma', 'emma@example.com', '1231231234', '400 Admin Rd, Star City', 'passwordadmin'),
    ('Liam', 'liam@example.com', '3213214321', '500 Admin Ct, Central City', 'liamsecure');


SELECT* FROM Admin;

CREATE TABLE Hotels (
    HotelID INT AUTO_INCREMENT PRIMARY KEY,
    AdminID INT NOT NULL,
    HotelName VARCHAR(255) NOT NULL,
    Location VARCHAR(255) NOT NULL,
    ContactNumber VARCHAR(15),
    ManagerName VARCHAR(255),
    EstablishedDate DATE,
    FOREIGN KEY (AdminID) REFERENCES Admin(id) ON DELETE CASCADE
);


SELECT* FROM Hotels;

CREATE TABLE RoomBookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    RoomType VARCHAR(50) NOT NULL,
    Quantity INT NOT NULL,
    BookingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    BookingDays INT NOT NULL,
    Location VARCHAR(50),
    BookingStatus VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (CustomerID) REFERENCES Customer(id) ON DELETE CASCADE,
    StayDuration INT,
    CheckOut BOOLEAN DEFAULT FALSE
);

SELECT* FROM RoomBookings;




CREATE TABLE RoomAvailability (
    Location VARCHAR(50) NOT NULL,
    RoomType VARCHAR(50) NOT NULL,
    AvailableRooms INT NOT NULL,
    PRIMARY KEY (Location, RoomType)
);
INSERT INTO RoomAvailability (Location, RoomType, AvailableRooms)
VALUES 
('Islamabad', 'Single Room', 100),
('Islamabad', 'Double Room', 100),
('Islamabad', 'Delux Room', 100),
('Islamabad', 'Suit Room', 100),
('Lahore', 'Single Room', 100),
('Lahore', 'Double Room', 100),
('Lahore', 'Delux Room', 100),
('Lahore', 'Suit Room', 100),
('Karachi', 'Single Room', 100),
('Karachi', 'Double Room', 100),
('Karachi', 'Delux Room', 100),
('Karachi', 'Suit Room', 100);

SELECT* FROM RoomAvailability;

CREATE TABLE RoomPrice (
    RoomType VARCHAR(50) PRIMARY KEY,
    RatePerDay DECIMAL(10, 2) NOT NULL
);
INSERT INTO RoomPrice (RoomType, RatePerDay) VALUES
('Single Room', 1000),
('Double Room', 2000),
('Delux Room', 3000),
('Suit Room', 5000);

CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentStatus VARCHAR(20) DEFAULT 'Pending',
    PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (BookingID) REFERENCES RoomBookings(BookingID),
    AccountNumber VARCHAR(20),
    FOREIGN KEY (AccountNumber) REFERENCES PaymentAccounts(AccountNumber)
);

SELECT* FROM Payments;

CREATE TABLE Feedback (
    FeedbackID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT,
    CustomerID INT,
    FeedbackText TEXT,
    Rating INT,
    FOREIGN KEY (BookingID) REFERENCES RoomBookings(BookingID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(id)
);

SELECT* FROM Feedback










