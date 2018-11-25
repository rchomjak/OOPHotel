package com.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class Hotel implements HotelInterface {

    private final int MAX_NUMBER_BEDS = 4;
    private final int MIN_NUMBER_BEDS = 1;

    private final int MAXIMUM_ROOMS_RESERVATION_INFO = 3;

    private final int ROOMS_ROTATION = 42;

    private final double CLIENT_DISCOUNT_LAST_RESERVATION_RATIO = 0.30;

    List<RoomInterface> rooms = new ArrayList<>();
    private int lastRoomId = 0;

    private static final HotelInterface hotel = new Hotel();
    private int totalBedsCapacity = 0;

    public Reservations reservations = new Reservations();
    private int lastReservationId = 0;


    private Hotel() {
        //TODO: CSV Initialization for Reservations, Rooms, ...
        //Should be in configuration file, the path of CSVs
        return;
    }

    public static HotelInterface getInstance() {
        return hotel;
    }

    @Override
    public void addRoom(int nOfBeds, LuxuryCategory luxuryCategory) {

        RoomInterface isExistRoom = findRoom(lastRoomId);

        if (isExistRoom != null) {
            throw new IllegalArgumentException("Something is really fucked up, you are creating two rooms with same id: " + lastRoomId);
        }

        if (nOfBeds > MAX_NUMBER_BEDS || nOfBeds < MIN_NUMBER_BEDS) {
            throw new IllegalArgumentException("You cannot create a room with that number of beds: " + nOfBeds);
        }

        RoomInterface newRoom = new Room(lastRoomId, nOfBeds, luxuryCategory);

        rooms.add(newRoom);
        totalBedsCapacity += nOfBeds;

        lastRoomId++;

        return;

    }

    private void addRoomCSV(RoomInterface in_room) {


        for (RoomInterface room: rooms) {

            if (room.getId() == in_room.getId()) {
                throw new IllegalArgumentException("You are adding same rooms twice Room id: " + in_room.getId());
            }
        }


        this.rooms.add(in_room);

        totalBedsCapacity += in_room.getNumberOfBeds();

        lastRoomId = in_room.getId();
        lastRoomId++;
    }

    @Override
    public RoomInterface findRoom(int id) {

        return rooms.stream().filter(x->x.getId() == id).findAny().orElse(null);

    }

    public List<RoomInterface> findRoomOnNumberOfBeds(int noBeds) {
        return rooms.stream().filter(x->x.getNumberOfBeds() == noBeds).collect(Collectors.toList());
    }


    @Override
    public void deleteRoom(int id) {

        RoomInterface delRoom = findRoom(id);
        if (delRoom != null && !delRoom.getIsDeleted()) {

            delRoom.setDeleted(true);
            totalBedsCapacity -= delRoom.getNumberOfBeds();

        } else {

            throw new IllegalArgumentException("There is no room with id: " + id);
        }

        return;
    }

    public void setAvailabilityRoom(int id, Boolean availability) {

        RoomInterface room =  findRoom(id);

        if (room != null) {
            room.setIsOpen(availability);

        } else {
            throw new IllegalArgumentException("There is no room with id: " + id);
        }
    }

    private int calculatesBedsCapacity() {
        //If we will use Availability parameters, we must rewrite this, otherwise is ok.
        int bedsCapacity = 0;

        for(RoomInterface room: rooms) {
            if (!room.getIsDeleted()) {
                bedsCapacity += room.getNumberOfBeds();
            }
        }

        this.totalBedsCapacity = bedsCapacity;
        return totalBedsCapacity;

    }

    //TODO: Needs testing and rewrite.
    @Override
    public List<ReservationInfo> findFreeRooms(MyPeriodInterface period, List<Integer> inRoomsNoBeds) {

        int incorrectRoomRequest = inRoomsNoBeds.stream().filter(x-> x < MIN_NUMBER_BEDS || x > MAX_NUMBER_BEDS).collect(Collectors.toList()).size();

        if (incorrectRoomRequest != 0) {
            throw new IllegalArgumentException("You cannot reserve room in different range than " + "(" + MIN_NUMBER_BEDS +", "+ MAX_NUMBER_BEDS +  ")");
        }

        int usedRoomsBedsCapacity;

        List<RoomInterface> idealRoomsCandidate;
        List<RoomInterface> roomsCandidate;

        List<ReservationInfo> returnedReservationInfo = new ArrayList<>();
        List<List<RoomInterface>> listOfRoomsCandidates = new ArrayList<>();

        Boolean isIdealCandidateFound = false;

        int nObedsrequest = inRoomsNoBeds.stream().mapToInt(i -> i.intValue()).sum();

        List<RoomInterface> allRoomsList = new ArrayList<>(rooms);
        List<RoomInterface> usedRoomsList = new ArrayList<>(this.findUsedRooms(period));

        usedRoomsBedsCapacity = usedRoomsList.stream().mapToInt(i->i.getNumberOfBeds()).sum();

        List<RoomInterface> sortedRooms = new ArrayList<>();
        boolean matched = false;

        //Remove all rooms with same ID - why java sucks?
        for (RoomInterface allRoom: allRoomsList) {

            matched = false;
            for (RoomInterface usedRoom: usedRoomsList) {
                if (usedRoom.getId() == allRoom.getId()) {
                    matched = true;
                }
            }
            if (!matched) {
                sortedRooms.add(allRoom);
            }
        }

        sortedRooms.sort(Comparator.comparingInt(RoomInterface::getNumberOfBeds));

        idealRoomsCandidate =  findIdealCandidate(inRoomsNoBeds, sortedRooms);
        List<Integer> listFoundNoBeds =  idealRoomsCandidate.stream().map(RoomInterface::getNumberOfBeds).collect(Collectors.toList());

        Integer missingBeds = nObedsrequest -  listFoundNoBeds.stream().mapToInt(x->x.intValue()).sum();

        if (missingBeds < 0 ) {
            throw new IllegalArgumentException("Something is really fucked up, negative missing beds.");
        }

        System.out.println("Ideal candidates " + idealRoomsCandidate);
        isIdealCandidateFound = 0 == missingBeds;

        List<RoomInterface> roomsWithoutIdealRoomsCandidate = new ArrayList<>(sortedRooms);


        //Oh jesus, why Java sucks, or only am I  java dumb? Trying to remove from List of rooms  objects in different list. (based on id)
        for (RoomInterface idealCandidateRoom: idealRoomsCandidate) {
            for (RoomInterface sortedRoom : sortedRooms) {
                if ( sortedRoom.getId() == idealCandidateRoom.getId()) {
                    roomsWithoutIdealRoomsCandidate.remove(idealCandidateRoom);
                }
            }
        }

        if (!isIdealCandidateFound) {
            for (int i = 0; i < ROOMS_ROTATION; i++ ) {
                //I am shuffling it, to find different candidates, because it can be hard to find second semi-ideal candidate (NP problem!)
                // So, I am lazy to create greedy algorithm, so I did shuffle to change some probability, that it can be found.

                Collections.shuffle(roomsWithoutIdealRoomsCandidate);
                roomsCandidate = findCandidate(missingBeds, roomsWithoutIdealRoomsCandidate);

                if (!listOfRoomsCandidates.contains(roomsCandidate) && !roomsCandidate.isEmpty()) {
                    listOfRoomsCandidates.add(roomsCandidate);
                    System.out.println(roomsCandidate.stream().map(RoomInterface::getNumberOfBeds).collect(Collectors.toList()));
                }
            }
        }

        if (isIdealCandidateFound) {

            //create ideal ReservationInfo
            System.out.println("Ideal candidate found");
            System.out.println(idealRoomsCandidate.stream().map(RoomInterface::getNumberOfBeds).collect(Collectors.toList()));

            ReservationInfo idealReservationInfo =  new ReservationInfo((MyPeriod) period, idealRoomsCandidate, (float) usedRoomsBedsCapacity/totalBedsCapacity);
            returnedReservationInfo.add(idealReservationInfo);

            ReservationInfo.incGid();

            return returnedReservationInfo;

        } else if (!isIdealCandidateFound && listOfRoomsCandidates.isEmpty()) {

            //return empty
            return returnedReservationInfo;

        } else {

            //return ;
            System.out.println("Merged candidate");

            List<RoomInterface> semiIdealAndCandidatesList = new ArrayList<>();

            for (int i = 0; listOfRoomsCandidates.size() > i && i < MAXIMUM_ROOMS_RESERVATION_INFO; i++) {

                if (!idealRoomsCandidate.isEmpty()) {
                    semiIdealAndCandidatesList.addAll(idealRoomsCandidate);
                }

                semiIdealAndCandidatesList.addAll(listOfRoomsCandidates.get(i));

                returnedReservationInfo.add(new ReservationInfo((MyPeriod)period, new ArrayList<>(semiIdealAndCandidatesList), (float)usedRoomsBedsCapacity/totalBedsCapacity));

                semiIdealAndCandidatesList.clear();

            }

            ReservationInfo.incGid();
        }
        return returnedReservationInfo;

    }

    private List<RoomInterface> findIdealCandidate(List<Integer> inRoomsNoBeds, List<RoomInterface> sortedRooms) {


        List<Integer> needsReserve = new ArrayList<>(inRoomsNoBeds);
        List<Integer> existsReserve = new ArrayList<>();

        List<RoomInterface> idealRoomsCandidate = new ArrayList<>();


        needsReserve.sort(Comparator.comparingInt(Integer::intValue));

        List<Integer> idOfRooms = new ArrayList<>();

        FOUND:
        for (Integer reserNoBeds: needsReserve) {


            for (RoomInterface room: sortedRooms) {

                if (existsReserve.size() == inRoomsNoBeds.size()) {
                    break FOUND;

                }

                if (reserNoBeds.equals(room.getNumberOfBeds()) ) {
                    if (!idOfRooms.contains(room.getId())) {
                        idealRoomsCandidate.add(room);
                        existsReserve.add(reserNoBeds);
                        idOfRooms.add(room.getId());
                        break;
                    }
                }

            }
        }

        return idealRoomsCandidate;
    }



    private List<RoomInterface> findCandidate(Integer inRoomsNoBeds, List<RoomInterface> sortedRooms) {

        List<RoomInterface> roomsCandidate = new ArrayList<>();
        int currentNoBeds = inRoomsNoBeds;

        List<Integer> idOfRooms = new ArrayList<>();

        for (RoomInterface room: sortedRooms) {

            if ((currentNoBeds - room.getNumberOfBeds()) > -MAX_NUMBER_BEDS) {

                if (!idOfRooms.contains(room.getId())) {
                    roomsCandidate.add(room);
                    currentNoBeds = currentNoBeds - room.getNumberOfBeds();

                    idOfRooms.add(room.getId());

                    continue;
                }
            }
        }


        if (currentNoBeds <= 0 ) {
            return roomsCandidate;

        } else {
            return new ArrayList<>();
        }

    }


    private List<RoomInterface> findUsedRooms(MyPeriodInterface period) {

        List<RoomInterface> usedRooms = new ArrayList<>();
        List<ReservationInterface> reserved =  reservations.reservationsInMyPeriod(period);

        List<ReservationInterface> activeReservations = (reserved.stream().filter(x-> x.getOrderState() != OrderState.Canceled)
                                                        .filter(x-> x.getOrderState() != OrderState.Deleted).collect(Collectors.toList()));

        for (ReservationInterface reservation : activeReservations) {
            usedRooms.addAll(reservation.getReservationInfo().getRoomsInfo());
        }

        return usedRooms;
    }


    @Override
    public Boolean makeReservation(ClientInterface client, ReservationInfo request) {


        //if in (last) of reservations client ordered and pay for reservation, he can pay less.
        int sizeOfReservationList = reservations.getAllReservations().size();
        int startSublistIndexReservation = sizeOfReservationList*(int) CLIENT_DISCOUNT_LAST_RESERVATION_RATIO;

        List<ReservationInterface> discountReservations = reservations.getAllReservations().subList(startSublistIndexReservation, sizeOfReservationList);
        long clientDiscountCount = discountReservations.stream().filter(x->x.getClient().equals(client)).filter(x->x.getOrderState().equals(OrderState.Accepted)).count();

        float clientDiscountRatio = clientDiscountCount / (!discountReservations.isEmpty()? discountReservations.size(): Integer.MAX_VALUE);

        //Maximum is 0.69
        double ratioDiscount = Math.log(clientDiscountRatio + 1);

        float price;

        if (clientDiscountRatio > 0.1) {
            price = (float)request.getRoomsInfo().stream().mapToDouble(x -> x.getBasePrice()).sum() * (float) ratioDiscount;

        } else {

            price = (float) request.getRoomsInfo().stream().mapToDouble(x -> x.getBasePrice()).sum();
        }

        price =  price * (-DAYS.between(request.getPeriod().getStopDate(), request.getPeriod().getStartDate()));
        ReservationInterface reservation = new Reservation(lastReservationId++, client, request, price, (float) ratioDiscount);


        reservations.addReservation((Reservation) reservation);

        return true;
    }

    public ReservationsInterface getReservations() {
        return this.reservations;
    }


    public void readRooms(String path) {

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            for (CSVRecord csvRecord : csvParser) {

                RoomInterface addRoom = new Room(Integer.parseInt(csvRecord.get("id")), Integer.parseInt(csvRecord.get("numberOfBeds")), LuxuryCategory.valueOf(csvRecord.get("luxuryCategory")));

                addRoom.setDeleted(Boolean.parseBoolean(csvRecord.get("numberOfBeds")));
                this.addRoomCSV(addRoom);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }


    }

    public void writeRooms(String path) {

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id", "numberOfBeds", "isDeleted", "luxuryCategory", "price"));
        ) {


            for (RoomInterface room: rooms) {
                csvPrinter.printRecord(room.getId(), room.getNumberOfBeds(), room.getIsDeleted(), room.getLuxuryCategory(), room.getBasePrice());

            }


            csvPrinter.flush();
        }  catch (IOException e) {

            e.printStackTrace();

        }

    }

}
