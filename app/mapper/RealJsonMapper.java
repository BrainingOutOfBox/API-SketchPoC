package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BrainstormingFindingDTO;

public class RealJsonMapper {

    private ObjectMapper mapper;

    public RealJsonMapper() {
        this.mapper = new ObjectMapper();
    }

    public BrainstormingFindingDTO toDTO(JsonNode json) throws JsonProcessingException {
        return mapper.treeToValue(json, BrainstormingFindingDTO.class);
    }

    public JsonNode toJson(BrainstormingFindingDTO brainstormingFindingDTO){
        return mapper.valueToTree(brainstormingFindingDTO);
    }

}
