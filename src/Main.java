//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;
import java.util.List;

class RoomParameters {
    private int floorNum;
    private int capacity;
    public RoomParameters setFloorNum(int floorNum) {
        this.floorNum = floorNum;
        return this;
    }
    public RoomParameters setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFloorNum() {
        return floorNum;
    }
}

class ServiceRoomParameters {
    private int floorNum;
    private int parkingCapacity;
    private String type;
    public ServiceRoomParameters setFloorNum(int floorNum) {
        this.floorNum = floorNum;
        return this;
    }

    public ServiceRoomParameters setParkingCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        return this;
    }

    public ServiceRoomParameters setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

    public int getParkingCapacity() {
        return parkingCapacity;
    }

    public int getFloorNum() {
        return floorNum;
    }
}

class Hotel {
    private static class Floor {
        private static class Room {
            int roomNumber;
            int capacity;
            Room(int capacity, int roomNumber) {
                this.roomNumber = roomNumber;
                this.capacity = capacity;
            }
            @Override
            public String toString() {
                return "    Room capacity: " + capacity + 
                    " | Room Number: " + roomNumber;
            }
        }
        private static class ServiceRoom {
            String type;
            int parkingCapacity;
            ServiceRoom(String type, int parkingCapacity) {
                this.type = type;
                this.parkingCapacity = parkingCapacity;
            }
            @Override
            public String toString() {
                return "    Service room parking capacity: " 
                    + parkingCapacity + " | Type: "+ type;
            }
        }
        private List<Room> rooms;
        private List<ServiceRoom> serviceRooms;

        private Floor() {
            this.rooms = new ArrayList<>();
            this.serviceRooms = new ArrayList<>();
        }

        private void addRoom(int capacity, int roomNumber) {
            Room newRoom = new Room(capacity, roomNumber);
            rooms.add(newRoom);
        }
        private void addSericeRoom(String type, int parkingCapacity) {
            ServiceRoom newServiceRoom = 
                new ServiceRoom(type, parkingCapacity);
            serviceRooms.add(newServiceRoom);
        }
    }
    private List<Floor> floors;

    public Hotel() {
        this.floors = new ArrayList<>();
    }

    public void buildFloors(int numOfFloors) {
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
            floor.addRoom(capacity, Integer.parseInt(String.valueOf(floorNum) +
                        String.format("%02d", floor.rooms.size())));
        } else {
            System.out.println("Invalid floor number.");
        }
    }
    public void addRoom(RoomParameters roomParameters) {
        addRoom(roomParameters.getFloorNum(), roomParameters.getCapacity());
    }

    public void addRoomsBulkCapacity(
            int floorNum, int capacity, int numberOfRooms) {
        if (floorNum <= floors.size() && numberOfRooms > 0 && floorNum > 0) {
            Floor floor = floors.get(floorNum - 1);
            for (int i = 0; i < numberOfRooms; i++) {
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
    public void addRoomsBulkCapacity(
            RoomParameters roomParameters, 
            int numberOfRooms) {
        addRoomsBulkCapacity(roomParameters.getFloorNum(),
                roomParameters.getCapacity(), numberOfRooms);
    }

    public void addServiceRoom(
            int floorNum, String type, 
            int parkingCapacity) {
        if (floorNum > 0 && floorNum <= floors.size()) {
            Floor floor = floors.get(floorNum - 1);
            floor.addSericeRoom(type, parkingCapacity);
        } else {
            System.out.println("Invalid floor number.");
        }
    }

    public void addServiceRoom(ServiceRoomParameters serviceRoomParameters) {
        addServiceRoom(serviceRoomParameters.getFloorNum(),
                serviceRoomParameters.getType(),
                serviceRoomParameters.getParkingCapacity());
    }

    public void printHotel() {
        for (Floor floor : floors) {
            System.out.print("Floor " + (floors.indexOf(floor) + 1));
            if (floor.rooms.size() > 0) {
                System.out.print(" (" + floor.rooms.size() + " Rooms)");
            }
            if (floor.serviceRooms.size() > 0) {
                System.out.print(" (" + floor.serviceRooms.size() +
                        " Service Rooms)");
            }
            for (Floor.Room room : floor.rooms) {
                System.out.println(room);
            }

            for (Floor.ServiceRoom serviceRoom : floor.serviceRooms) {
                System.out.println(serviceRoom);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.buildFloors(3);



        hotel.addRoomsBulkCapacity(new RoomParameters().
                setCapacity(6).setFloorNum(2), 6);
        hotel.addRoomsBulkCapacity(new RoomParameters()
                .setCapacity(6).setFloorNum(3), 4);
        hotel.addRoom(new RoomParameters().setCapacity(2).setFloorNum(6));

        hotel.addServiceRoom(1, "Bar", 0);
        hotel.addServiceRoom(1, "Reception", 0);
        hotel.addServiceRoom(1, "Parking", 21);
        hotel.addServiceRoom(new ServiceRoomParameters()
                .setType("Beach").setFloorNum(1).setParkingCapacity(32));

        hotel.printHotel();
    }
}
