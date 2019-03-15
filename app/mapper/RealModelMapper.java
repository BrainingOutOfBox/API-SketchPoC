package mapper;

import dto.BrainstormingFindingDTO;
import entity.BrainstormingFinding;
import org.modelmapper.ModelMapper;

public class RealModelMapper {

    private ModelMapper modelMapper;

    public RealModelMapper() {
        modelMapper = new ModelMapper();
    }

    public BrainstormingFindingDTO toBrainstormingFindingDTO(BrainstormingFinding finding){
        return modelMapper.map(finding, BrainstormingFindingDTO.class);
    }

    public BrainstormingFinding toBrainstormingFinding(BrainstormingFindingDTO findingDTO){
        return modelMapper.map(findingDTO, BrainstormingFinding.class);
    }

}
