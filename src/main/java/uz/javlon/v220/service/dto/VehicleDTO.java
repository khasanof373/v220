package uz.javlon.v220.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import uz.javlon.v220.domain.enumeration.Status;

/**
 * A DTO for the {@link uz.javlon.v220.domain.Vehicle} entity.
 */
@Schema(description = "Транспортное средство")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleDTO implements Serializable {

    private Long id;

    @NotNull
    private Long modelId;

    @NotNull
    private Long manufactureId;

    private String imageUrl;

    @NotNull
    private Status status;

    @NotNull
    private String carNumber;

    private OcppTagDTO tag;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Long manufactureId) {
        this.manufactureId = manufactureId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public OcppTagDTO getTag() {
        return tag;
    }

    public void setTag(OcppTagDTO tag) {
        this.tag = tag;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleDTO)) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", modelId=" + getModelId() +
            ", manufactureId=" + getManufactureId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", carNumber='" + getCarNumber() + "'" +
            ", tag=" + getTag() +
            ", user=" + getUser() +
            "}";
    }
}
