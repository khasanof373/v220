package uz.javlon.v220.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.javlon.v220.repository.VehicleRepository;
import uz.javlon.v220.service.VehicleService;
import uz.javlon.v220.service.dto.VehicleDTO;
import uz.javlon.v220.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.javlon.v220.domain.Vehicle}.
 */
@RestController
@RequestMapping("/api")
public class VehicleResource {

    private final Logger log = LoggerFactory.getLogger(VehicleResource.class);

    private static final String ENTITY_NAME = "vehicle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleService vehicleService;

    private final VehicleRepository vehicleRepository;

    public VehicleResource(VehicleService vehicleService, VehicleRepository vehicleRepository) {
        this.vehicleService = vehicleService;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * {@code POST  /vehicles} : Create a new vehicle.
     *
     * @param vehicleDTO the vehicleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleDTO, or with status {@code 400 (Bad Request)} if the vehicle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", vehicleDTO);
        if (vehicleDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity
            .created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicles/:id} : Updates an existing vehicle.
     *
     * @param id the id of the vehicleDTO to save.
     * @param vehicleDTO the vehicleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleDTO vehicleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Vehicle : {}, {}", id, vehicleDTO);
        if (vehicleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleDTO result = vehicleService.update(vehicleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicles/:id} : Partial updates given fields of an existing vehicle, field will ignore if it is null
     *
     * @param id the id of the vehicleDTO to save.
     * @param vehicleDTO the vehicleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VehicleDTO> partialUpdateVehicle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleDTO vehicleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vehicle partially : {}, {}", id, vehicleDTO);
        if (vehicleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleDTO> result = vehicleService.partialUpdate(vehicleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicles} : get all the vehicles.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping("/vehicles")
    public List<VehicleDTO> getAllVehicles(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Vehicles");
        return vehicleService.findAll();
    }

    /**
     * {@code GET  /vehicles/:id} : get the "id" vehicle.
     *
     * @param id the id of the vehicleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        log.debug("REST request to get Vehicle : {}", id);
        Optional<VehicleDTO> vehicleDTO = vehicleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleDTO);
    }

    /**
     * {@code DELETE  /vehicles/:id} : delete the "id" vehicle.
     *
     * @param id the id of the vehicleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle : {}", id);
        vehicleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
