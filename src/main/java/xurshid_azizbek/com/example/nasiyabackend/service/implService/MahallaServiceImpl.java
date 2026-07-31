package xurshid_azizbek.com.example.nasiyabackend.service.implService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.exception.AlreadyNameException;
import xurshid_azizbek.com.example.nasiyabackend.exception.NotFoundException;
import xurshid_azizbek.com.example.nasiyabackend.mapper.MahallaMapper;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.MahallaRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.MahallaResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.MahallaRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.projection.MahallaWithCountProjection;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.MahallaService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MahallaServiceImpl implements MahallaService {

    private final MahallaRepository mahallaRepository;
    private final MahallaMapper  mahallaMapper;

    @Override
    public ApiResponse createMahalla(User user, MahallaRequest mahallaRequest) {
        String name= mahallaRequest.name().trim();

        if (mahallaRepository.existsByUserAndNameIgnoreCase(user,name)) {
            throw new AlreadyNameException("Mahalla name already exists");
        }
        Mahalla mahalla = mahallaMapper.requestToMahalla(user, name);
        mahallaRepository.save(mahalla);
        log.info("Mahalla created: id{},name:{},userid:{}",mahalla.getId(),name,user.getId());
        MahallaResponse response = mahallaMapper.mahallaToResponse(mahalla, 0L);
        return new ApiResponse("Mahalla Created",
                true,
                HttpStatus.CREATED,response);

    }

    @Override
    public ApiResponse getAllMahalla(Integer userId) {
        log.info("Fetching all mahalla userid:{}",userId);

        List<MahallaResponse> mahallaResponses =
                mahallaRepository.findAllByUserIdWithCount(userId)
                        .stream()
                        .map(mahallaMapper::projectionToResponse).toList();
        if (mahallaResponses.isEmpty()) {
            log.info("No mahalla found");
            return new ApiResponse("Hozircha mahalla yo'q",true,HttpStatus.OK,mahallaResponses);
        }
        log.info("Found {} mahallas for userId:{}",mahallaResponses.size(),userId);
        return new ApiResponse("Mahallas found",true,HttpStatus.OK,mahallaResponses);
    }

    @Override
    public ApiResponse getMahallaById(Integer userId, Integer mahallaId) {
        log.info("Fetching mahalla mahallaId:{}, userId:{}", mahallaId, userId);

        MahallaResponse mahallaResponse = mahallaRepository
                .findByIdAndUserIdWithCount(mahallaId, userId)
                .map(mahallaMapper::projectionToResponse)
                .orElseThrow(() -> {
                    log.info("Mahalla not found, mahallaId:{}, userId:{}", mahallaId, userId);
                    return new NotFoundException("Mahalla topilmadi");
                });

        log.info("Mahalla found by id:{}", mahallaId);
        return new ApiResponse("Mahalla topildi", true, HttpStatus.OK, mahallaResponse);
    }

    @Override
    public ApiResponse updateMahalla(Integer userId, Integer mahallaId, MahallaRequest mahallaRequest) {
        log.info("Updated Mahhalla mahallaId:{}, userId:{}", mahallaId, userId);
        Mahalla mahalla = mahallaRepository.findByIdAndUserIdAndIsDeletedFalse( mahallaId,userId).orElseThrow(
                () -> new NotFoundException("Mahalla topilmadi")
        );
        String newName = mahallaRequest.name().trim();
        if (!mahalla.getName().equalsIgnoreCase(newName)
        && mahallaRepository.existsByUserAndNameIgnoreCase(mahalla.getUser(),newName)) {
            throw new AlreadyNameException("Mahalla name already exists");
        }
        mahalla.setName(newName);
        mahallaRepository.save(mahalla);
        MahallaResponse response = mahallaRepository.findByIdAndUserIdWithCount(mahallaId, userId).map(mahallaMapper::projectionToResponse).orElseThrow(
                () -> new NotFoundException("Mahalla topilmadi")
        );
        log.info("Mahalla updated by id:{}", mahallaId);
        return new ApiResponse("Mahalla updated",true,HttpStatus.OK,response);
    }

    @Override
    public ApiResponse deleteMahalla(Integer userId, Integer mahallaId) {
        log.info("Deleting mahalla mahallaId:{}, userId:{}", mahallaId, userId);
        Mahalla mahalla = mahallaRepository.findByIdAndUserIdAndIsDeletedFalse(mahallaId, userId).orElseThrow(
                () -> new NotFoundException("Mahalla topilmadi")
        );
        mahalla.setIsDeleted(true);
        mahallaRepository.save(mahalla);
        log.info("Mahalla deleted by mahallaId:{} , userId:{}", mahallaId, userId);
        return new ApiResponse("Mahalla deleted",true,HttpStatus.OK,null);
    }


}
