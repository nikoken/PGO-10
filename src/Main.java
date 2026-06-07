import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("S001", "Anna Kowalska", "12c", 120));
        students.add(new Student("S002", "Marek Nowak", "12c", 40));
        students.add(new Student("S003", "Julia Zielinska", "13a", 0));

        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new LaptopSet("E001", "Lenovo ThinkPad Lab", 80, 32, true));
        equipmentList.add(new LaptopSet("E002", "Dell XPS Demo", 100, 16, false));
        equipmentList.add(new CameraKit("E003", "Sony Content Kit", 90, 3, true));
        equipmentList.add(new CameraKit("E004", "Canon Interview Kit", 70, 1, true));

        DiscountPolicy discountPolicy = new LoyaltyDiscountPolicy();
        ReservationService service = new ReservationService(students, equipmentList, discountPolicy);

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n1. Display students");
            System.out.println("2. Display equipment");
            System.out.println("3. Create reservation");
            System.out.println("4. Return equipment");
            System.out.println("5. Show active reservations");
            System.out.println("6. Show report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1 -> service.printStudentList();
                case 2 -> service.printEquipmentList();
                case 3 -> {
                    System.out.print("Enter student id: ");
                    String studentId = scanner.nextLine().trim();
                    System.out.print("Enter equipment id: ");
                    String equipmentId = scanner.nextLine().trim();
                    System.out.print("Enter number of days: ");
                    try {
                        int days = Integer.parseInt(scanner.nextLine().trim());
                        service.createReservation(studentId, equipmentId, days);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of days.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter reservation id: ");
                    String reservationId = scanner.nextLine().trim();
                    service.returnEquipment(reservationId);
                }
                case 5 -> service.printActiveReservations();
                case 6 -> service.printReport();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}