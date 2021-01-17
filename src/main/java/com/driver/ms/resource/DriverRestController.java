package com.driver.ms.resource;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = DriverConstant.TABLE_NAME, description = "Driver's APIs")
@RestController
@RequestMapping(DriverConstant.BASE_URL)
public class DriverRestController {

    private DriverService driverService;

    @Autowired
    public DriverRestController(final DriverService driverService) {
        this.driverService = driverService;
    }

    @Operation(method = "Get", description = "Fetch the list of driver", tags = "Driver")
    @ApiResponse(responseCode = "200",
            description = "List of driver",
            content = @Content(schema = @Schema(implementation = Driver.class)))
    @GetMapping(DriverConstant.DRIVERS)
    public ResponseEntity<List<Driver>> getDrivers() {
        List<Driver> listOfDriver = driverService.getListOfDriver();
        if (!CollectionUtils.isEmpty(listOfDriver)) {
            return new ResponseEntity<>(listOfDriver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(method = "Get", description = "Fetch the list of drivers based on the search criteria")
    @ApiResponse(responseCode = "200",
            description = "Return a list of drivers based on the filter criteria",
            content = @Content(schema = @Schema(implementation = Driver.class)))
    @GetMapping(DriverConstant.USER + DriverConstant.FIND_BY_FIRSTNAME)
    public ResponseEntity<List<Driver>> getDriversByFirstName(@RequestParam("firstname") String firstname) {
        if (firstname.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Driver> drivers = driverService.findByFirstName(firstname);
        if (CollectionUtils.isEmpty(drivers)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping(path = DriverConstant.FIND_DRIVER_BY_ID)
    public ResponseEntity<Driver> getDriverById(@PathVariable(name = DriverConstant.ID) Long id) {
        return new ResponseEntity<>(driverService.findDriverById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = DriverConstant.DELETE_DRIVER_BY_ID)
    public ResponseEntity<Driver> deleteDriver(@PathVariable(name = DriverConstant.ID) Long id) {
        return new ResponseEntity<>(driverService.deleteDriverById(id), HttpStatus.OK);
    }

    @PostMapping(path = DriverConstant.DRIVER + DriverConstant.CREATE)
    public ResponseEntity<DriverDto> createDriver(@RequestBody DriverDto driverDto) {
        return new ResponseEntity<>(driverService.createDriver(driverDto), HttpStatus.CREATED);
    }

    @PutMapping(path = DriverConstant.DRIVER + DriverConstant.UPDATE_DRIVER)
    public ResponseEntity<Driver> updateDriver(@RequestBody DriverDto driverDto) {
        return new ResponseEntity<>(driverService.updateDriver(driverDto), HttpStatus.OK);
    }

    @GetMapping(path = DriverConstant.USER + DriverConstant.BASIC_AUTH)
    public ResponseEntity<Boolean> getAuth() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
