package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.BrainsheetDTO;
import dto.BrainstormingFindingDTO;
import dto.BrainwaveDTO;
import dto.IdeaDTO;
import entity.Brainsheet;
import entity.BrainstormingFinding;
import entity.Brainwave;
import entity.Idea;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RealModelMapperUnitTest {

    @Test
    public void brainstormingDTOToBrainstormingTest(){
        BrainstormingFindingDTO brainstormingFindingDTO = createBrainstormFindingDTO();

        RealModelMapper modelMapper = new RealModelMapper();
        BrainstormingFinding brainstormingFinding = modelMapper.toBrainstormingFinding(brainstormingFindingDTO);

        assertEquals(brainstormingFindingDTO.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription(), brainstormingFinding.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription());
        assertEquals(brainstormingFindingDTO.getName(), brainstormingFinding.getName());
        assertEquals(brainstormingFindingDTO.getProblemDescription(), brainstormingFinding.getProblemDescription());
        assertEquals(brainstormingFindingDTO.getIdentifier(), brainstormingFinding.getIdentifier());
    }


    @Test
    public void brainstormingToBrainstormingDTOTest(){
        BrainstormingFinding brainstormingFinding = createBrainstormingFinding();

        RealModelMapper modelMapper = new RealModelMapper();
        BrainstormingFindingDTO brainstormingFindingDTO = modelMapper.toBrainstormingFindingDTO(brainstormingFinding);

        assertEquals(brainstormingFinding.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription(), brainstormingFindingDTO.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription());
        assertEquals(brainstormingFinding.getName(), brainstormingFindingDTO.getName());
        assertEquals(brainstormingFinding.getProblemDescription(), brainstormingFindingDTO.getProblemDescription());
        assertEquals(brainstormingFinding.getIdentifier(), brainstormingFindingDTO.getIdentifier());
    }

    @Test
    public void jsonNodeToBrainstormingFindingTest() throws JsonProcessingException {
        RealJsonMapper jsonMapper = new RealJsonMapper();
        RealModelMapper modelMapper = new RealModelMapper();

        JsonNode json = createJsonNode();
        BrainstormingFindingDTO brainstormingFindingDTO = jsonMapper.toDTO(json);
        BrainstormingFinding brainstormingFinding = modelMapper.toBrainstormingFinding(brainstormingFindingDTO);

        assertEquals(brainstormingFinding.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription(), json.findPath("description").asText());
        assertEquals(brainstormingFinding.getName(), json.get("name").asText());
        assertEquals(brainstormingFinding.getProblemDescription(), json.get("problemDescription").asText());
        assertEquals(brainstormingFinding.getNrOfIdeas(), json.get("nrOfIdeas").asInt());
        assertEquals(brainstormingFinding.getBaseRoundTime(), json.get("baseRoundTime").asInt());
    }

    @Test
    public void brainstormingFindingToJsonNodeTest(){
        RealJsonMapper jsonMapper = new RealJsonMapper();
        RealModelMapper modelMapper = new RealModelMapper();
        BrainstormingFinding brainstormingFinding = createBrainstormingFinding();

        BrainstormingFindingDTO brainstormingFindingDTO = modelMapper.toBrainstormingFindingDTO(brainstormingFinding);
        JsonNode json = jsonMapper.toJson(brainstormingFindingDTO);

        assertEquals(brainstormingFinding.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription(), json.findPath("description").asText());
        assertEquals(brainstormingFinding.getName(), json.get("name").asText());
        assertEquals(brainstormingFinding.getProblemDescription(), json.get("problemDescription").asText());
        assertEquals(brainstormingFinding.getNrOfIdeas(), json.get("nrOfIdeas").asInt());
        assertEquals(brainstormingFinding.getBaseRoundTime(), json.get("baseRoundTime").asInt());
    }


    private BrainstormingFindingDTO createBrainstormFindingDTO(){
        IdeaDTO ideaDTO = new IdeaDTO("DemoTestDTO");
        ArrayList<IdeaDTO> ideaDTOS = new ArrayList<>();
        ideaDTOS.add(ideaDTO);


        BrainwaveDTO brainwaveDTO = new BrainwaveDTO(0, ideaDTOS);
        ArrayList<BrainwaveDTO> brainwaveDTOS = new ArrayList<>();
        brainwaveDTOS.add(brainwaveDTO);

        BrainsheetDTO brainsheetDTO = new BrainsheetDTO(0, brainwaveDTOS);
        ArrayList<BrainsheetDTO> brainsheetDTOS = new ArrayList<>();
        brainsheetDTOS.add(brainsheetDTO);

        BrainstormingFindingDTO brainstormingFindingDTO = new BrainstormingFindingDTO("DemoTestDTO", "DemoDTO", 1, 2, 0, "", brainsheetDTOS, 0, "");
        return  brainstormingFindingDTO;
    }

    private BrainstormingFinding createBrainstormingFinding(){
        Idea idea = new Idea("DemoTestBO");
        ArrayList<Idea> ideas = new ArrayList<>();
        ideas.add(idea);


        Brainwave brainwave = new Brainwave(0, ideas);
        ArrayList<Brainwave> brainwaves = new ArrayList<>();
        brainwaves.add(brainwave);

        Brainsheet brainsheet = new Brainsheet(0, brainwaves);
        ArrayList<Brainsheet> brainsheets = new ArrayList<>();
        brainsheets.add(brainsheet);

        BrainstormingFinding brainstormingFinding = new BrainstormingFinding("DemoTestBO", "DemoBO", 1, 2, 0, "", brainsheets, 0, "");
        return  brainstormingFinding;
    }

    private JsonNode createJsonNode(){
        JsonNode idea = JsonNodeFactory.instance.objectNode();
        ((ObjectNode) idea).put("description", "DemoTestJson");

        JsonNode brainwave = JsonNodeFactory.instance.objectNode();
        ((ObjectNode) brainwave).put("nrOfBrainwave", 0);
        ((ObjectNode) brainwave).putArray("ideas").add(idea);

        JsonNode brainsheet = JsonNodeFactory.instance.objectNode();
        ((ObjectNode) brainsheet).put("nrOfSheet", 0);
        ((ObjectNode) brainsheet).putArray("brainwaves").add(brainwave);

        JsonNode finding = JsonNodeFactory.instance.objectNode();
        ((ObjectNode) finding).put("name", "DemoTestJson");
        ((ObjectNode) finding).put("problemDescription", "DemoTestJson");
        ((ObjectNode) finding).put("nrOfIdeas", 1);
        ((ObjectNode) finding).put("baseRoundTime", 3);
        ((ObjectNode) finding).putArray("brainsheets").add(brainsheet);

        return finding;
    }
}
