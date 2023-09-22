package uz.javlon.v220.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import uz.javlon.v220.domain.enumeration.Status;

/**
 * Транспортное средство
 */
@Entity
@Table(name = "vehicle")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "model_id", nullable = false)
    private Long modelId;

    @NotNull
    @Column(name = "manufacture_id", nullable = false)
    private Long manufactureId;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @NotNull
    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parentTag" }, allowSetters = true)
    private OcppTag tag;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return this.modelId;
    }

    public Vehicle modelId(Long modelId) {
        this.setModelId(modelId);
        return this;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getManufactureId() {
        return this.manufactureId;
    }

    public Vehicle manufactureId(Long manufactureId) {
        this.setManufactureId(manufactureId);
        return this;
    }

    public void setManufactureId(Long manufactureId) {
        this.manufactureId = manufactureId;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Vehicle imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Status getStatus() {
        return this.status;
    }

    public Vehicle status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public Vehicle carNumber(String carNumber) {
        this.setCarNumber(carNumber);
        return this;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public OcppTag getTag() {
        return this.tag;
    }

    public void setTag(OcppTag ocppTag) {
        this.tag = ocppTag;
    }

    public Vehicle tag(OcppTag ocppTag) {
        this.setTag(ocppTag);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", modelId=" + getModelId() +
            ", manufactureId=" + getManufactureId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", carNumber='" + getCarNumber() + "'" +
            "}";
    }
}
