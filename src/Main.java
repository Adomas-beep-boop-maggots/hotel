//TIP To <b>Run</b>  code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Reservation {
    LocalDate startDate;
    LocalDate endDate;
    Reservation(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    private static boolean doOverlap(Reservation reservation1, Reservation reservation2) {
        return  !reservation1.startDate.isAfter(reservation2.endDate) &&
                !reservation2.startDate.isAfter(reservation1.endDate);
        /* s1 > e2 &&
           s2 > e1
           --------s1=====e1-------
           ----s2=====e2----------- TRUE
           -s1====e1---------------
           ---------------s2====e2- FALSE */
    }
    public static boolean isReserved(List<Reservation> reservations, Reservation newReservation) {
        for (Reservation existingReservation : reservations) {
            if (doOverlap(existingReservation, newReservation)) {
                return true;
            }
        }
        return false;
    }
}

class Person {
    String email;
    Reservation reservation;
}

class Hotel {
    private static class Floor {
        private static class Room {
            int numberAddress;
            int capacity;
            List<Reservation> reservations = new ArrayList<>();
            Room(int capacity, int numberAddress) {
                this.numberAddress = numberAddress;
                this.capacity = capacity;
            }
            private void reserve(LocalDate startDate, LocalDate endDate) {
                        this.reservations.add(new Reservation(startDate, endDate));
            }
            public boolean isRoomReserved(Reservation newReservation) {
                return Reservation.isReserved(reservations, newReservation);
            }
        }
        private static class ServiceRoom {
            String type;
            ServiceRoom(String type) {
                this.type = type;
            }
        }
        private List<Room> rooms;
        private List<ServiceRoom> serviceRooms;

        private Floor() {
            this.rooms = new ArrayList<>();
            this.serviceRooms = new ArrayList<>();
        }

        private void addRoom(int capacity, int numberAddress) {
            Room newRoom = new Room(capacity, numberAddress);
            rooms.add(newRoom);
        }
        private void addSericeRoom(String type) {
            ServiceRoom newServiceRoom = new ServiceRoom(type);
            serviceRooms.add(newServiceRoom);
        }
    }


    private List<Floor> floors;

    public Hotel() {
        this.floors = new ArrayList<>();
    }

    Floor.Room getRoomByAddress(int numberAddress) {
//      TODO: parse numberAddress to return room
//      this is very resource inefficient
        for (Floor floor : floors) {
            for (Floor.Room room : floor.rooms) {
                if (room.numberAddress == numberAddress) {
                    return room;
                }
            }
        }
        return null;
    }

    void reserveRoom(int numberAddress, String startDate, String endDate) {
        getRoomByAddress(numberAddress).reserve(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    public void addFloors(int numOfFloors) {
        for (int i = 0; i < numOfFloors; i++) {
            floors.add(new Floor());
        }
    }

    public void removeFloor(int index) {
        if (index > 0 && index <= floors.size()) {
            this.floors.remove(index - 1);
        } else {
            System.out.println("Invalid floor number.");
        }
    }

    public void addRoom(int floorNum, int capacity) {
        if (floorNum > 0 && floorNum <= floors.size()) {
            Floor floor = floors.get(floorNum - 1);
            floor.addRoom(capacity, Integer.parseInt(String.valueOf(floorNum) + String.format("%02d", floor.rooms.size())));
        } else {
            System.out.println("Invalid floor number.");
        }
    }

    public void addRoomsBulkCapacity(int floorNum, int numberOfRooms, int capacity) {
        if (floorNum <= floors.size() && numberOfRooms > 0 && floorNum > 0) {
            Floor floor = floors.get(floorNum - 1);
            for (int i = 0; i < numberOfRooms; i++) {
//                floor.addRoom(capacity, Integer.parseInt(String.valueOf(floorNum) + String.format("%03d", floor.rooms.size())));
                addRoom(floorNum, capacity);
            }
        } else if (numberOfRooms <= 0) {
            System.out.println("Invalid number of rooms.");
            System.out.println("Enter Positive number.");
        } else {
            System.out.println("Invalid floor number.");
            System.out.println("Enter Positive number.");
        }
    }

    public void addServiceRoom(int floorNum, String type) {
        if (floorNum > 0 && floorNum <= floors.size()) {
            Floor floor = floors.get(floorNum - 1);
            floor.addSericeRoom(type);
        } else {
            System.out.println("Invalid floor number.");
        }
    }

    public void printHotel() {
        for (Floor floor : floors) {
            System.out.print("Floor " + (floors.indexOf(floor) + 1));
            if (!floor.rooms.isEmpty()) {
                System.out.print(" (" + floor.rooms.size() + " Rooms)");
            }
            if (!floor.serviceRooms.isEmpty()) {
                System.out.print(" (" + floor.serviceRooms.size() + " Service Rooms)");
            }
            System.out.println();
            for (Floor.Room room : floor.rooms) {
                System.out.println("    Room capacity: " + room.capacity + " | Room Adress: " + room.numberAddress);
            }

            for (Floor.ServiceRoom serviceRoom : floor.serviceRooms) {
                System.out.println("    Service room: " + serviceRoom.type);
            }               
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addFloors(3);

        hotel.addRoomsBulkCapacity(2,6,2);
        hotel.addRoomsBulkCapacity(3,4,3);

        hotel.addServiceRoom(1, "Bar");
        hotel.addServiceRoom(1, "Reception");
        hotel.addServiceRoom(1, "Parking");

        hotel.printHotel();
    }
}