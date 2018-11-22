package com.company;

import java.util.ArrayList;
import java.util.List;

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

            client.setDeleted();

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
}
