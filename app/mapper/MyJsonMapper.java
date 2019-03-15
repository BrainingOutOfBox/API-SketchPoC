package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OrderDTO;

public class MyJsonMapper {

    private ObjectMapper mapper;

    public MyJsonMapper() {
        this.mapper = new ObjectMapper();
    }

    public OrderDTO toDTO(JsonNode json) throws JsonProcessingException {
        return mapper.treeToValue(json, OrderDTO.class);
    }

    public JsonNode toJson(OrderDTO orderDTO){
        return mapper.valueToTree(orderDTO);
    }

}
