package com.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationsInfo implements ReservationsInfoInterface {

    public List<List<ReservationInfo>> reservationsInfo = new ArrayList<>();
    private Map<Integer, List<ReservationInfo>> categoryReservationMap = new HashMap<>();

    private int gid = 0;

    ReservationsInfo() {
        return;
    }

    @Override
    public void delReservationInfoByGid(int gid) {
        categoryReservationMap.remove(gid);
        //TODO: needs remove from reservationsInfo too;
    }

    @Override
    public void delReservationInfoById(int id) {

        /*
            for (List<ReservationInfo> lreservationsInfo: reservationsInfo) {
                for ( ReservationInfo lreservationInfo: lreservationsInfo ) {
                    if (lreservationInfo.getId() == id ) {
                        return lreservationInfo;
                    }
                }
            }
            return;
        */

        return;
    }

    @Override
    public void addReservationsInfo(List<ReservationInfo> reservInfo){

        reservationsInfo.add(reservInfo);
        categoryReservationMap.put(gid++, reservInfo);
    }

    public void addReservationsInfo(List<ReservationInfo> reservInfo, int in_gid){

        reservationsInfo.add(reservInfo);
        categoryReservationMap.put(in_gid, reservInfo);
    }

    @Override
    public List<ReservationInfo> findReservationInfoByGid(int gid) {
        return this.categoryReservationMap.get(gid);
    }

    public ReservationInfo findReservationInfoById(int lid) {

        for (List<ReservationInfo> lreservationsInfo: reservationsInfo) {
            for ( ReservationInfo lreservationInfo: lreservationsInfo ) {
                if (lreservationInfo.getId() == lid ) {
                    return lreservationInfo;
                }
            }
        }

        return null;
    }


    public Boolean writer(String path) throws IOException {

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id", "gid", "dateStart", "dateEnd", "orderedRooms", "capacityRatio"));
        ) {



            for (List<ReservationInfo> lReservInfo: reservationsInfo) {

                for (ReservationInfo reservInfo: lReservInfo) {

                     String roomsId = "";

                     for (RoomInterface roomIn:  reservInfo.getRoomsInfo()) {
                         roomsId += ""+ roomIn.getId() + ";" ;
                     }

                    csvPrinter.printRecord(reservInfo.getId(), reservInfo.getGid(),
                                            reservInfo.getPeriod().getStartDate().toString(),
                                            reservInfo.getPeriod().getStopDate().toString(), roomsId, reservInfo.getCapacityRatio());
                }
            }

            csvPrinter.flush();
        }  catch (IOException e) {

            e.printStackTrace();

        }

        return true;
    }


    public void reader(String path, List<RoomInterface> rooms)   throws IOException {

        Map<Integer, List<ReservationInfo>> readerGidReservationInfoMap = new HashMap<>();

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            for (CSVRecord csvRecord : csvParser) {


                String[] roomsId = csvRecord.get("orderedRooms").split(";");

                List<RoomInterface> orderedRooms = new ArrayList<>();
                for (RoomInterface room : rooms) {
                    for (String roomId : roomsId) {
                        if (Integer.parseInt(roomId) == room.getId()) {
                            orderedRooms.add(room);
                        }
                    }
                }

                LocalDate dateStart = LocalDate.parse(csvRecord.get("dateStart"));
                LocalDate dateStop = LocalDate.parse(csvRecord.get("dateStart"));

                MyPeriod reservationDate = new MyPeriod(dateStart, dateStop);

                int gid = Integer.parseInt(csvRecord.get("gid"));
                float capacityRatio = Float.parseFloat(csvRecord.get("capacityRatio"));

                List<ReservationInfo> reservationInfoBasedOnGid = readerGidReservationInfoMap.get(gid);

                if (reservationInfoBasedOnGid == null) {

                    reservationInfoBasedOnGid = new ArrayList<>();
                    ReservationInfo addReservation = new ReservationInfo(reservationDate, orderedRooms,  capacityRatio);
                    addReservation.setGid(gid);

                    reservationInfoBasedOnGid.add(addReservation);

                    readerGidReservationInfoMap.put(gid, reservationInfoBasedOnGid);


                } else {

                    ReservationInfo addReservation = new ReservationInfo(reservationDate, orderedRooms, capacityRatio);
                    addReservation.setGid(gid);
                    reservationInfoBasedOnGid.add(addReservation);
                }

                ReservationInfo.setgGid(gid + 1);
                this.gid = gid;
                this.gid++;
            }

            for (Integer keys: readerGidReservationInfoMap.keySet()) {

                   List<ReservationInfo> reservationInfoBasedOnGid = readerGidReservationInfoMap.get(keys);
                   this.addReservationsInfo(reservationInfoBasedOnGid, keys);
            }




        } catch (IOException e) {

            e.printStackTrace();
        } finally {


        }


    }







}

