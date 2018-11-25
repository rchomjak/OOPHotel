package com.company;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Clients implements ClientsInterface{

    List<Client> clients = new ArrayList<>();


    private static Clients client = new Clients();



    private Clients() {

    }




    public static Clients getInstance() {
        return client;
    }


    @Override
    public Client findClient(String id) {

        return clients.stream().filter(x->x.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public void deleteClient(String id) {

        Client client =  findClient(id);

        if (client != null && !client.isDeleted()) {

            client.setDeleted(true);

        } else {

            throw new IllegalArgumentException("There is no client with id: " + id);
        }
    }

    @Override
    public void addClient(Client in_client) {

        for (Client client: clients) {

            if (client.getId().equals(in_client.getId())) {
                throw new IllegalArgumentException("There is already exists client with same id: " + client.getId());
            }

        }

        clients.add(in_client);

    }

    public Boolean writer(String path) throws IOException {

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id", "noFinishedReservations", "isDeleted", "isSuperUser"));
        ) {


            for (Client lClient: clients) {
                csvPrinter.printRecord(lClient.getId(), lClient.getNoFinishedReservations(), lClient.isDeleted(), lClient.isDeleted());

            }


            csvPrinter.flush();
        }  catch (IOException e) {

            e.printStackTrace();

        }


        return true;
    }

    public void reader(String path)   throws IOException {

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            for (CSVRecord csvRecord : csvParser) {



                Client addClient = new Client(csvRecord.get("id"), Boolean.parseBoolean(csvRecord.get("isSuperUser")));

                addClient.setDeleted(Boolean.parseBoolean(csvRecord.get("isDeleted")));
                addClient.setNoFinishedReservations(Integer.parseInt(csvRecord.get("noFinishedReservations")));

                this.addClient(addClient);

            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {


        }


    }


}
