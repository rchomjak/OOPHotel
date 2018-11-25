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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reservations implements ReservationsInterface {

    List<ReservationInterface> reservations = new ArrayList<>();
    int lastReservationId = 0;

    @Override
    public void deleteReservation(int id) {

        ReservationInterface reserv = findReservation(id);
        if (reserv != null && !reserv.getOrderState().equals(OrderState.Deleted)) {
            reserv.setOrderState(OrderState.Deleted);
        } else {
            throw new IllegalArgumentException("There is no reservation with id: " + id);
        }
    }

    @Override
    public void addReservation(Reservation in_reservation) {

        for (ReservationInterface reservation: reservations) {
            if (reservation.getId() == in_reservation.getId()) {
                throw new IllegalArgumentException("There is already exists client with same id: " + reservation.getId());
            }
        }

        reservations.add(in_reservation);
    }

    @Override
    public ReservationInterface findReservation(int id) {

        return reservations.stream().filter(x -> x.getId() == id).findAny().orElse(null);
    }

    public List<ReservationInterface> reservationsInMyPeriod(MyPeriodInterface inMyPeriod) {

        return reservations.stream().filter(x->x.isReservedForMyPeriod(inMyPeriod)).collect(Collectors.toList());
    }

    public List<ReservationInterface> getAllReservations() {
        return reservations;
    }



    public Boolean writer(String path) throws IOException {


        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id", "clientID", "ReservationInfoId", "price", "discount", "orderState"));
        ) {


            for (ReservationInterface lreservationInterface: reservations) {
                   Reservation lreservation =  (Reservation) lreservationInterface;

                   csvPrinter.printRecord(lreservation.getId(), lreservation.getClient().getId(),
                           lreservation.getReservationInfo().getId(),
                           lreservation.getPrice(), lreservation.getDiscount(),lreservation.getOrderState());

            }

            csvPrinter.flush();
        }  catch (IOException e) {

            e.printStackTrace();

        }

        return true;
    }


    public void reader(String path, Clients clients, ReservationsInfo reservationsInfo)   throws IOException {


        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());


            for (CSVRecord csvRecord : csvParser) {

                int id = Integer.parseInt(csvRecord.get("id"));
                String clientId = csvRecord.get("clientID"); //Its string
                int reservationInfoId = Integer.parseInt(csvRecord.get("ReservationInfoId"));
                float price = Float.parseFloat(csvRecord.get("price"));
                float discount = Float.parseFloat(csvRecord.get("discount"));
                String parsedOrderState = csvRecord.get("orderState");

                OrderState orderState = OrderState.valueOf(parsedOrderState);

                Client client = clients.findClient(clientId);
                if (client == null) {
                    throw new IllegalArgumentException("During de-marshalling client with id: " + clientId + " was not found");
                }

                ReservationInfo reservationInfo = reservationsInfo.findReservationInfoById(reservationInfoId);
                if (reservationInfo == null) {
                    throw new IllegalArgumentException("During de-marshalling reservationInfo with id: " + reservationInfo + " was not found");
                }

                Reservation addReservation = new Reservation(id, client, reservationInfo, price, discount);
                addReservation.setOrderState(orderState);

                this.addReservation(addReservation);

            }





        } catch (IOException e) {

            e.printStackTrace();
        } finally {


        }


    }


}
