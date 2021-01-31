package com.driver.ms.resource;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = DriverConstant.TABLE_NAME, description = "Driver's APIs")
@RestController
@RequestMapping(DriverConstant.BASE_URL)
public class DriverRestController {

    private DriverService driverService;

    private Environment environment;

    public DriverRestController(final DriverService driverService, Environment environment) {
        this.driverService = driverService;
        this.environment = environment;
    }

    @Operation(method = "Get", description = "Fetch the list of driver", tags = "Driver")
    @ApiResponse(responseCode = "200",
            description = "List of driver",
            content = @Content(schema = @Schema(implementation = Driver.class)))
    @GetMapping(DriverConstant.DRIVERS)
    public ResponseEntity<List<Driver>> getDrivers() throws JsonProcessingException {
        List<Driver> listOfDriver = driverService.getListOfDriver();
        if (!CollectionUtils.isEmpty(listOfDriver)) {
            log.info("List of drivers API call {} ", new ObjectMapper().writeValueAsString(listOfDriver));
            return new ResponseEntity<>(listOfDriver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(method = "Get", description = "Fetch the list of drivers based on the search criteria")
    @ApiResponse(responseCode = "200",
            description = "Return a list of drivers based on the filter criteria",
            content = @Content(schema = @Schema(implementation = Driver.class)))
    @GetMapping(DriverConstant.USER + DriverConstant.FIND_BY_FIRSTNAME)
    public ResponseEntity<List<Driver>> getDriversByFirstName(@RequestParam("firstname") String firstname) throws JsonProcessingException {
        if (firstname.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Driver> drivers = driverService.findByFirstName(firstname);
        if (CollectionUtils.isEmpty(drivers)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("List of drivers by firstName API call {} ", new ObjectMapper().writeValueAsString(drivers));
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping(path = DriverConstant.FIND_DRIVER_BY_ID)
    public ResponseEntity<Driver> getDriverById(@PathVariable(name = DriverConstant.ID) Long id) throws JsonProcessingException, InterruptedException {
        log.info("Get driver by id {} API ", new ObjectMapper().writeValueAsString(id));
        return new ResponseEntity<>(driverService.findDriverById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = DriverConstant.DELETE_DRIVER_BY_ID)
    public ResponseEntity<Driver> deleteDriver(@PathVariable(name = DriverConstant.ID) Long id) throws JsonProcessingException {
        log.info("Delete driver by id {} API ", new ObjectMapper().writeValueAsString(id));
        return new ResponseEntity<>(driverService.deleteDriverById(id), HttpStatus.OK);
    }

    @PostMapping(path = DriverConstant.DRIVER + DriverConstant.CREATE)
    public ResponseEntity<DriverDto> createDriver(@RequestBody DriverDto driverDto) throws JsonProcessingException {
        log.info("Create driver {} API ", new ObjectMapper().writeValueAsString(driverDto));
        return new ResponseEntity<>(driverService.createDriver(driverDto), HttpStatus.CREATED);
    }

    @PutMapping(path = DriverConstant.DRIVER + DriverConstant.UPDATE_DRIVER)
    public ResponseEntity<Driver> updateDriver(@RequestBody DriverDto driverDto) throws JsonProcessingException {
        log.info("Update driver {} API ", new ObjectMapper().writeValueAsString(driverDto));
        return new ResponseEntity<>(driverService.updateDriver(driverDto), HttpStatus.OK);
    }

    @GetMapping(path = DriverConstant.USER + DriverConstant.BASIC_AUTH)
    public ResponseEntity<Boolean> getAuth() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("env-name")
    @HystrixCommand(fallbackMethod = "findDriverByIdFallBackMethod")
    public String getDriverByIdFallBack() throws InterruptedException {
        return "test";
    }

    public String findDriverByIdFallBackMethod() {
        return "fallback";
    }
}
