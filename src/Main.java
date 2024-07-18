//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;
import java.util.List;

class Hotel {
    private static class Floor {
        private static class Room {
            int capacity;
            Room(int capacity) {
                this.capacity = capacity;
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

        private void addRoom(int capacity) {
            Room newRoom = new Room(capacity);
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
            floor.addRoom(capacity);
        } else {
            System.out.println("Invalid floor number.");
        }
    }

    public void addRoomsBulkCapacity(int floorNum, int numberOfRooms, int capacity) {
        if (floorNum <= floors.size() && numberOfRooms > 0 && floorNum > 0) {
            Floor floor = floors.get(floorNum - 1);
            for (int i = 0; i < numberOfRooms; i++) {
                floor.addRoom(capacity);
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
            if (floor.rooms.size() > 0) {
                System.out.print(" (" + floor.rooms.size() + " Rooms)");
            }
            if (floor.serviceRooms.size() > 0) {
                System.out.print(" (" + floor.serviceRooms.size() + " Service Rooms)");
            }
            System.out.println();
            for (Floor.Room room : floor.rooms) {
                System.out.println("    Room capacity: " + room.capacity);
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