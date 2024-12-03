package com.geffer.spring_store.ServiceImpl;

import com.geffer.spring_store.DTO.ClientDTO;
import com.geffer.spring_store.DTO.OrderDTO;
import com.geffer.spring_store.Entity.Client;
import com.geffer.spring_store.Entity.Order;
import com.geffer.spring_store.Repository.ClientRepo;
import com.geffer.spring_store.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepo;

    @Lazy
    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public List<ClientDTO> findAll() {
        return clientRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ClientDTO findById(Long id) {
        Optional<Client> clientOptional = clientRepo.findById(id);
        return clientOptional.map(this::convertToDTO).orElse(null);
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client savedClient = clientRepo.save(client);
        return convertToDTO(savedClient);
    }

    @Override
    public void delete(Long id) {
        clientRepo.deleteById(id);
    }

    public Client findClient(Long id) {
        return clientRepo.findById(id).orElse(null);
    }

    public List<Order> toOrder(List<OrderDTO> orderDTOS){
        List<Order> orders = new ArrayList();
        for (OrderDTO dto : orderDTOS) {
            Order order = orderService.convertToEntity(dto);
            orders.add(order);
        }
        return orders;
    }

    public ClientDTO convertToDTO(Client client) {
        if (client == null) return null;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setName(client.getName());
        clientDTO.setTel_number(client.getTel_number());
        List<OrderDTO> orderDTOs = client.getOrders().stream().map(orderService::convertToDTO).collect(Collectors.toList());
        clientDTO.setOrders(orderDTOs);
        return clientDTO;
    }
    

    public Client convertToEntity(ClientDTO clientDTO){
        if(clientDTO == null){
            return null;
        }
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setAddress(clientDTO.getAddress());
        client.setTel_number(clientDTO.getTel_number());
        if (clientDTO.getOrders() != null) {
            client.setOrders(toOrder(clientDTO.getOrders()));   
        } else {
            List<Order> orders = new ArrayList<>();
            client.setOrders(orders);
        }
        return client;
    }
}
