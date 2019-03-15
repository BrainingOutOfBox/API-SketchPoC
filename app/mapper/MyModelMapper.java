package mapper;

import dto.OrderDTO;
import entity.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class MyModelMapper {
    private ModelMapper modelMapper;

    public MyModelMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setCustomerFirstName(source.getFirstName());
                map().setCustomerLastName(source.getLastName());
                map().setBillingStreet(source.getAddress().getStreet());
                map().setBillingCity(source.getAddress().getCity());
            }
        });

        this.modelMapper.addMappings(new PropertyMap<OrderDTO, Order>() {
            @Override
            protected void configure() {
                map().setFirstName(source.getCustomerFirstName());
                map().setLastName(source.getCustomerLastName());
                map(source.getBillingStreet(), destination.getAddress().getStreet());
                map(source.getBillingCity(), destination.getAddress().getCity());
            }
        });

    }

    public OrderDTO toDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order toOrder(OrderDTO orderDTO){
        return modelMapper.map(orderDTO, Order.class);
    }
}
