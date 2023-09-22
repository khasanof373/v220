package uz.javlon.v220.service.mapper;

import org.mapstruct.*;
import uz.javlon.v220.domain.OcppTag;
import uz.javlon.v220.domain.User;
import uz.javlon.v220.domain.Vehicle;
import uz.javlon.v220.service.dto.OcppTagDTO;
import uz.javlon.v220.service.dto.UserDTO;
import uz.javlon.v220.service.dto.VehicleDTO;

/**
 * Mapper for the entity {@link Vehicle} and its DTO {@link VehicleDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {
    @Mapping(target = "tag", source = "tag", qualifiedByName = "ocppTagIdTag")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    VehicleDTO toDto(Vehicle s);

    @Named("ocppTagIdTag")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idTag", source = "idTag")
    OcppTagDTO toDtoOcppTagIdTag(OcppTag ocppTag);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
