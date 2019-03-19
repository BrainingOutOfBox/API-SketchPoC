package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RealModelMapperUnitTest {

    @Test
    public void brainstormingDTOToBrainstormingTest(){
        BrainstormingFindingDTO brainstormingFindingDTO = createBrainstormingFindingDTO(4);

        RealModelMapper modelMapper = new RealModelMapper();
        BrainstormingFinding brainstormingFinding = modelMapper.toBrainstormingFinding(brainstormingFindingDTO);

        assertEquals(brainstormingFindingDTO.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription(), brainstormingFinding.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription());
        assertEquals(brainstormingFindingDTO.getName(), brainstormingFinding.getName());
        assertEquals(brainstormingFindingDTO.getProblemDescription(), brainstormingFinding.getProblemDescription());
        assertEquals(brainstormingFindingDTO.getIdentifier(), brainstormingFinding.getIdentifier());
    }


    @Test
    public void brainstormingToBrainstormingDTOTest(){
        BrainstormingFinding brainstormingFinding = createBrainstormingFinding(3);

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

        JsonNode json = createBrainstormingFindingJsonNode(2);
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
        BrainstormingFinding brainstormingFinding = createBrainstormingFinding(2);

        BrainstormingFindingDTO brainstormingFindingDTO = modelMapper.toBrainstormingFindingDTO(brainstormingFinding);
        JsonNode json = jsonMapper.toJson(brainstormingFindingDTO);

        assertEquals(brainstormingFinding.getBrainsheets().get(0).getBrainwaves().get(0).getIdeas().get(0).getDescription(), json.findPath("description").asText());
        assertEquals(brainstormingFinding.getName(), json.get("name").asText());
        assertEquals(brainstormingFinding.getProblemDescription(), json.get("problemDescription").asText());
        assertEquals(brainstormingFinding.getNrOfIdeas(), json.get("nrOfIdeas").asInt());
        assertEquals(brainstormingFinding.getBaseRoundTime(), json.get("baseRoundTime").asInt());
    }


    private BrainstormingFindingDTO createBrainstormingFindingDTO(int amountOfBrainsheetDTOs){
        ArrayList<BrainsheetDTO> brainsheetDTOS = createBrainsheetsDTO(amountOfBrainsheetDTOs);

        BrainstormingFindingDTO brainstormingFindingDTO = new BrainstormingFindingDTO("DemoTestDTO", "DemoDTO", 1, 2, 0, "", brainsheetDTOS, 0, "");
        return  brainstormingFindingDTO;
    }

    private ArrayList<BrainsheetDTO> createBrainsheetsDTO(int amount){
        ArrayList<BrainsheetDTO> brainsheetDTOS = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            BrainsheetDTO brainsheetDTO = new BrainsheetDTO(i, createBrainwavesDTO(amount));
            brainsheetDTOS.add(brainsheetDTO);
        }
        return brainsheetDTOS;
    }

    private ArrayList<BrainwaveDTO> createBrainwavesDTO(int amount){
        ArrayList<BrainwaveDTO> brainwaveDTOS = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            BrainwaveDTO brainwaveDTO = new BrainwaveDTO(i, createBrainstormingIdeasDTO(amount));
            brainwaveDTOS.add(brainwaveDTO);
        }
        return brainwaveDTOS;
    }

    private ArrayList<IdeaDTO> createBrainstormingIdeasDTO(int amount){
        ArrayList<IdeaDTO> ideaDTOS = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            IdeaDTO ideaDTO = new IdeaDTO(generateRandomText(5));
            ideaDTOS.add(ideaDTO);
        }
        return ideaDTOS;
    }




    private BrainstormingFinding createBrainstormingFinding(int amountOfBrainsheets){
        ArrayList<Brainsheet> brainsheets = createBrainsheets(amountOfBrainsheets);

        BrainstormingFinding brainstormingFinding = new BrainstormingFinding("DemoTestBO", "DemoBO", 1, 2, 0, "", brainsheets, 0, "");
        return  brainstormingFinding;
    }

    private ArrayList<Brainsheet> createBrainsheets(int amount){
        ArrayList<Brainsheet> brainsheets = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Brainsheet brainsheet = new Brainsheet(i, createBrainwaves(amount));
            brainsheets.add(brainsheet);
        }
        return brainsheets;
    }

    private ArrayList<Brainwave> createBrainwaves(int amount){
        ArrayList<Brainwave> brainwaves = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Brainwave brainwave = new Brainwave(i, createBrainstormingIdeas(amount));
            brainwaves.add(brainwave);
        }
        return brainwaves;
    }

    private ArrayList<Idea> createBrainstormingIdeas(int amount){
        ArrayList<Idea> ideas = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Idea idea = new Idea(generateRandomText(5));
            ideas.add(idea);
        }
        return ideas;
    }



    private JsonNode createBrainstormingFindingJsonNode(int amountOfBrainsheetsJson){
        JsonNode finding = JsonNodeFactory.instance.objectNode();
        ((ObjectNode) finding).put("name", "DemoTestJson");
        ((ObjectNode) finding).put("problemDescription", "DemoTestJson");
        ((ObjectNode) finding).put("nrOfIdeas", amountOfBrainsheetsJson);
        ((ObjectNode) finding).put("baseRoundTime", 3);
        ((ObjectNode) finding).putArray("brainsheets").addAll(createBrainsheetJson(amountOfBrainsheetsJson));

        return finding;
    }

    private ArrayNode createBrainsheetJson(int amount){
        ArrayNode brainsheetArray = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < amount; i++){
            ObjectNode brainsheet = JsonNodeFactory.instance.objectNode();
            brainsheet.put("nrOfSheet", i);
            brainsheet.putArray("brainwaves").addAll(createBrainwaveJson(amount));
            brainsheetArray.add(brainsheet);
        }

        return brainsheetArray;
    }

    private ArrayNode createBrainwaveJson(int amount){
        ArrayNode brainwaveArray = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < amount; i++){
            ObjectNode brainwave = JsonNodeFactory.instance.objectNode();
            brainwave.put("nrOfBrainwave", i);
            brainwave.putArray("ideas").addAll(createBrainstormingIdeasJson(amount));
            brainwaveArray.add(brainwave);
        }

        return brainwaveArray;
    }

    private ArrayNode createBrainstormingIdeasJson(int amount){
        ArrayNode ideaArray = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < amount; i++){
            ObjectNode idea = JsonNodeFactory.instance.objectNode();
            idea.put("description", generateRandomText(5));
            ideaArray.add(idea);
        }

        return ideaArray;
    }



    private String generateRandomText(int length){
        Random random = new Random();
        String alphabet = "abcdefghijklmnopjrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++){
            randomString.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return randomString.toString();
    }
}
