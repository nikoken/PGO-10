import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private List<Student> students;
    private List<Equipment> equipmentList;
    private List<Reservation> reservations;
    private DiscountPolicy discountPolicy;
    private int reservationCounter = 1;

    public ReservationService(List<Student> students, List<Equipment> equipmentList, DiscountPolicy discountPolicy) {
        this.students = students;
        this.equipmentList = equipmentList;
        this.discountPolicy = discountPolicy;
        this.reservations = new ArrayList<>();
    }

    public Reservation createReservation(String studentId, String equipmentId, int days) {
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Error: student " + studentId + " not found.");
            return null;
        }

        Equipment equipment = findEquipment(equipmentId);
        if (equipment == null) {
            System.out.println("Error: equipment " + equipmentId + " not found.");
            return null;
        }

        if (!equipment.isAvailable()) {
            System.out.println("Error: equipment " + equipmentId + " is not available.");
            return null;
        }

        if (days < 1 || days > 14) {
            System.out.println("Error: number of days must be between 1 and 14.");
            return null;
        }

        String reservationId = "R" + String.format("%03d", reservationCounter++);
        Reservation reservation = new Reservation(reservationId, student, equipment, days);
        equipment.setAvailable(false);
        reservations.add(reservation);

        double cost = reservation.calculateTotalCost(discountPolicy);
        System.out.println("Reservation " + reservationId + " created.");
        System.out.println("Equipment: " + equipment.getName());
        System.out.printf("Cost: %.2f PLN%n", cost);
        System.out.println("Status: ACTIVE");

        return reservation;
    }

    public void returnEquipment(String reservationId) {
        Reservation reservation = findReservation(reservationId);
        if (reservation == null) {
            System.out.println("Error: reservation " + reservationId + " not found.");
            return;
        }

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            System.out.println("Error: reservation " + reservationId + " is not active.");
            return;
        }

        reservation.setStatus(ReservationStatus.RETURNED);
        reservation.getEquipment().setAvailable(true);

        double cost = reservation.calculateTotalCost(discountPolicy);
        int points = (int) (cost / 10);
        reservation.getStudent().addLoyaltyPoints(points);

        System.out.println("Equipment returned. The student received " + points + " loyalty points.");
    }

    public void printActiveReservations() {
        System.out.println("--- Active reservations ---");
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.ACTIVE) {
                System.out.println(r.getDisplayText());
                found = true;
            }
        }
        if (!found) System.out.println("No active reservations.");
    }

    public void printReport() {
        System.out.println("--- Completed reservations ---");
        double totalRevenue = 0;
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.RETURNED) {
                System.out.println(r.getDisplayText());
                totalRevenue += r.calculateTotalCost(discountPolicy);
            }
        }
        System.out.printf("Total revenue: %.2f PLN%n", totalRevenue);

        Student topStudent = null;
        for (Student s : students) {
            if (topStudent == null || s.getLoyaltyPoints() > topStudent.getLoyaltyPoints()) {
                topStudent = s;
            }
        }
        if (topStudent != null) {
            System.out.println("Top student: " + topStudent.getFullName() + " with " + topStudent.getLoyaltyPoints() + " points");
        }
    }

    public void printEquipmentList() {
        System.out.println("--- Equipment list ---");
        for (Equipment e : equipmentList) {
            System.out.println(e.getDisplayText());
        }
    }

    public void printStudentList() {
        System.out.println("--- Student list ---");
        for (Student s : students) {
            System.out.println("[" + s.getId() + "] " + s.getFullName() + " | Group: " + s.getGroupName() + " | Points: " + s.getLoyaltyPoints());
        }
    }

    private Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    private Equipment findEquipment(String id) {
        for (Equipment e : equipmentList) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    private Reservation findReservation(String id) {
        for (Reservation r : reservations) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }
}