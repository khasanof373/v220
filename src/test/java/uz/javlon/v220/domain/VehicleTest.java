package uz.javlon.v220.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.javlon.v220.web.rest.TestUtil;

class VehicleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicle.class);
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(vehicle1.getId());
        assertThat(vehicle1).isEqualTo(vehicle2);
        vehicle2.setId(2L);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
        vehicle1.setId(null);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
    }
}
