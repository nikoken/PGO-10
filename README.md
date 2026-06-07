# PGO-10 - Equipment Reservation System

## What is this?
A Java console application for MediaLab, a university lab where students can borrow equipment like laptops and camera kits. The program supports checking availability, creating reservations, returning equipment and generating reports.

---

## Aim
Practice object-oriented programming concepts including classes, objects, encapsulation, inheritance, polymorphism, composition, collections and interfaces in a real-world scenario.

---

## How it works
After startup the program loads sample data and shows a menu. The user can display students and equipment, create reservations, return equipment and view reports.

### Classes

- `Main` - starts the program, creates sample data and handles the menu
- `Student` - represents a student with id, name, group and loyalty points
- `Equipment` - abstract base class for all equipment types
- `LaptopSet` - concrete equipment type, extends Equipment, adds RAM and docking station logic
- `CameraKit` - concrete equipment type, extends Equipment, adds lens count and tripod logic
- `Reservation` - connects a student, equipment, number of days and status
- `ReservationService` - handles all business logic: creating reservations, returning equipment and reports
- `LoyaltyDiscountPolicy` - implements DiscountPolicy, applies 10% discount for students with 100+ points

### Interfaces

- `Displayable` - implemented by Equipment and Reservation, returns a readable text line for console display
- `DiscountPolicy` - implemented by LoyaltyDiscountPolicy, calculates discount based on student loyalty points

### Enum

- `ReservationStatus` - ACTIVE, RETURNED, CANCELLED

---

## Polymorphism example
`calculateDailyPrice()` is defined in the abstract `Equipment` class and overridden in both `LaptopSet` and `CameraKit`. When the program calculates a reservation cost it calls this method on an `Equipment` reference without knowing the actual type, so the correct price is calculated automatically.

---

## How to run
```bash
javac src/*.java -d out
java -cp out Main
```
