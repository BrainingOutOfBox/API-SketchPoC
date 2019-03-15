package mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.OrderDTO;
import entity.Address;
import entity.Order;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelMapperUnitTest {

    @Test
    public void orderDTOtoOrderTest(){
        OrderDTO orderDTO = new OrderDTO("Joe", "Smith", "2233 Pike Street", "Seattle");

        MyModelMapper myModelMapper = new MyModelMapper();
        Order order = myModelMapper.toOrder(orderDTO);

        assertEquals(orderDTO.getCustomerFirstName(), order.getFirstName());
        assertEquals(orderDTO.getCustomerLastName(), order.getLastName());
        assertEquals(orderDTO.getBillingStreet(), order.getAddress().getStreet());
        assertEquals(orderDTO.getBillingCity(), order.getAddress().getCity());

    }

    @Test
    public void orderToOrderDTOTest(){
        Address address = new Address("2233 Pike Street", "Seattle");
        Order order = new Order("Joe", "Smith", address);

        MyModelMapper myModelMapper = new MyModelMapper();
        OrderDTO orderDTO = myModelMapper.toDTO(order);

        assertEquals(order.getFirstName(), orderDTO.getCustomerFirstName());
        assertEquals(order.getLastName(), orderDTO.getCustomerLastName());
        assertEquals(order.getAddress().getStreet(), orderDTO.getBillingStreet());
        assertEquals(order.getAddress().getCity(), orderDTO.getBillingCity());
    }

    @Test
    public void jsonNodeToOrderTest() throws JsonProcessingException {
        MyJsonMapper myJsonMapper = new MyJsonMapper();
        MyModelMapper myModelMapper = new MyModelMapper();

        JsonNode json = JsonNodeFactory.instance.objectNode();
        ((ObjectNode) json).put("customerFirstName", "Joe");
        ((ObjectNode) json).put("customerLastName", "Smith");
        ((ObjectNode) json).put("billingStreet", "2233 Pike Street");
        ((ObjectNode) json).put("billingCity", "Seattle");

        OrderDTO orderDTO = myJsonMapper.toDTO(json);
        Order order = myModelMapper.toOrder(orderDTO);

        assertEquals(order.getFirstName(), json.get("customerFirstName").asText());
        assertEquals(order.getLastName(), json.get("customerLastName").asText());
        assertEquals(order.getAddress().getStreet(), json.get("billingStreet").asText());
        assertEquals(order.getAddress().getCity(), json.get("billingCity").asText());
    }

    @Test
    public void orderToJsonNodeTest() throws JsonProcessingException {
        Address address = new Address("2233 Pike Street", "Seattle");
        Order order = new Order("Joe", "Smith", address);

        MyModelMapper myModelMapper = new MyModelMapper();
        MyJsonMapper myJsonMapper = new MyJsonMapper();

        OrderDTO orderDTO = myModelMapper.toDTO(order);
        JsonNode json = myJsonMapper.toJson(orderDTO);

        assertEquals(order.getFirstName(), json.get("customerFirstName").asText());
        assertEquals(order.getLastName(), json.get("customerLastName").asText());
        assertEquals(order.getAddress().getStreet(), json.get("billingStreet").asText());
        assertEquals(order.getAddress().getCity(), json.get("billingCity").asText());
    }


}
