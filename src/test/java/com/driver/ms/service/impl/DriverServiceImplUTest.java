package com.driver.ms.service.impl;

import com.driver.ms.entity.Address;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.exception.BadParamException;
import com.driver.ms.exception.ExistingDriverException;
import com.driver.ms.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static com.driver.ms.entity.Driver.builder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayName(" **** Driver services **** ")
class DriverServiceImplUTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @DisplayName("Given the list of drivers")
    @Nested
    class DisplayAllDriversTest {

        @DisplayName("When invoking the find all drivers service")
        @Nested
        class AllDriversTest {
            @Test
            @DisplayName(value = "Then display all drivers available in the list ")
            void should_return_the_list_of_drivers() {
                when(driverRepository.findAll()).thenReturn(Arrays.asList(
                        builder()
                                .id(1L)
                                .firstname("firstname1")
                                .build(),
                        builder()
                                .id(2L)
                                .firstname("firstname2")
                                .build())
                );

                List<Driver> listOfDriver = driverService.getListOfDriver();

                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(2, listOfDriver.size(), "List of user not empty"),
                        () -> assertTrue(listOfDriver.stream().anyMatch(driver -> driver.getId().equals(1L)), "The list contains the driver %s")
                );

            }

            @DisplayName("When invoking the grouping by journey service")
            @Nested
            class DriversGroupedByJourneyTest {
                @Test
                @DisplayName(value = "Then display drivers grouped by journey ")
                void should_return_the_list_of_drivers() {
                    Journey journey = Journey.builder()
                            .id(1L)
                            .nbrOfPlaces(22)
                            .build();
                    Driver expectedDriver = builder()
                            .id(1L)
                            .journey(journey)
                            .build();
                    List<Driver> drivers = Arrays.asList(
                            expectedDriver,
                            builder()
                                    .id(1L)
                                    .journey(
                                            Journey.builder()
                                                    .id(1L)
                                                    .nbrOfPlaces(22)
                                                    .build())
                                    .build(),

                            builder()
                                    .id(1L)
                                    .journey(
                                            Journey.builder()
                                                    .id(1L)
                                                    .nbrOfPlaces(22)
                                                    .build())
                                    .build()
                    );

                    when(driverRepository.findAll()).thenReturn(drivers);

                    Map<Journey, List<Driver>> groupedDriversByJourney = driverService.getGroupedDriversByJourney();
                    assertAll("Check all the following asserts",
                            () -> assertEquals(groupedDriversByJourney.size(), 3),
                            () -> assertEquals(groupedDriversByJourney.get(journey).get(0), expectedDriver));


                }
            }

            @Test
            @DisplayName(value = "Then display an empty list if there is none")
            void should_return_an_empty_list_of_drivers() {
                when(driverRepository.findAll()).thenReturn(new ArrayList<>());
                List<Driver> listOfDriver = driverService.getListOfDriver();
                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(0, listOfDriver.size(), "An empty list of user"),
                        () -> assertTrue(listOfDriver.isEmpty(), "List is empty"));
                verify(driverRepository, times(1)).findAll();

            }
        }
    }

    @DisplayName("Given find drivers by criteria service")
    @Nested
    class DisplayDriversBySearchCriteria {

        @DisplayName("When passing the firstName criteria ")
        @Nested
        class DriverByFirstName {

            @Test
            @DisplayName(value = "Display drivers based on the search criteria available in the list")
            void should_return_a_list_of_driver_based_on_a_search_criteria() {
                List<Driver> driversList = Arrays.asList(
                        builder()
                                .id(1L)
                                .firstname("firstname1")
                                .build(),
                        builder()
                                .id(2L)
                                .firstname("firstname2")
                                .build(),
                        builder()
                                .id(3L)
                                .firstname("firstname3")
                                .build(),
                        builder()
                                .id(4L)
                                .firstname("firstname4")
                                .build());
                when(driverRepository.findByFirstName(anyString())).thenReturn(driversList);
                List<Driver> listOfDriver = driverService.findByFirstName("firstname1");
                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(4, listOfDriver.size(), "An empty list of user"));
                assertThat(listOfDriver).contains(driversList.get(0));

                verify(driverRepository, times(1)).findByFirstName(anyString());
            }

            @DisplayName("When passing an invalid firstName criteria")
            @Nested
            class DriverByFirstNameInvalidParam {

                @Test
                @DisplayName(value = "Bad param exception is thrown")
                void should_throw_bad_param_exception_when_providing_invalid_param() {

                    assertAll("Verify conditions for displaying the drivers",
                            () -> assertThrows(BadParamException.class, () -> driverService.findByFirstName(null),
                                    "Please provide a valid param"));
                }
            }

            @DisplayName("When passing the phone criteria ")
            @Nested
            class DriverByPhone {

                @Test
                @DisplayName(value = "Display driver based on the phone search criteria")
                void should_return_a_driver_based_on_the_phone_search_criteria() {
                    Driver driver = builder()
                            .id(2L)
                            .firstname("firstname2")
                            .build();
                    when(driverRepository.findByAddressPhone(anyString())).thenReturn(Optional.of(driver));

                    Driver driverByPhone = driverService.findByAddressPhone("060606060");
                    assertEquals(driver, driverByPhone, "The matching driver");
                    verify(driverRepository, times(1)).findByAddressPhone(anyString());
                }
            }

            @DisplayName("When passing an invalid phone criteria ")
            @Nested
            class DriverByInvalidPhone {

                @Test
                @DisplayName(value = "Throw an exception")
                void should_throw_an_exception() {
                    assertThrows(BadParamException.class, () -> driverService.findByAddressPhone(""), "Please provide a valid search criteria");
                    verify(driverRepository, times(0)).findByAddressPhone(anyString());
                }
            }
        }

        @DisplayName("When user provides the lastName search criteria ")
        @Nested
        class DriverByPhone {

            @Test
            @DisplayName(value = "Display driver based on the lastName search")
            void should_return_a_driver_based_on_the_lastName_search_criteria() {
                Driver driver = builder()
                        .id(1L)
                        .lastname("lastname1")
                        .build();
                when(driverRepository.findByLastName(anyString())).thenReturn(Optional.of(driver));

                Driver driverByLastName = driverService.findDriverByLastName("last");
                assertEquals("lastname1", driverByLastName.getLastname(), "The driver lastname must match the same as lastname1");
                verify(driverRepository, times(1)).findByLastName(anyString());
            }
            @Test
            @DisplayName(value = "Throw invalid lastName param exception")
            void should_throw_an_exception_when_invalid_lastName() {
                assertThrows(RuntimeException.class,()->driverService.findDriverByLastName(null));
            }
        }
    }

    @Nested
    @DisplayName("Given a new driver to be added")
    class AddingNewDriver {

        @Nested
        @DisplayName("When user passes a new driver")
        class NewDriver {

            @DisplayName("Then a new driver should be created and added to the list")
            @Test
            void should_create_a_new_driver() {
                Driver driverToPersist = builder()
                        .id(1L)
                        .firstname("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();

                Driver foundDriver = builder()
                        .id(2L)
                        .build();

                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(foundDriver));
                when(driverRepository.save(any(Driver.class))).thenReturn(driverToPersist);

                Driver createdDriver = driverService.createDriver(driverToPersist);

                assertAll("Check conditions",
                        () -> assertEquals(driverToPersist, createdDriver, "A new driver has been created"));
                verify(driverRepository, times(1)).save(any(Driver.class));
                verify(driverRepository, times(1)).findById(anyLong());

            }
        }

        @DisplayName("When user passes an invalid driver")
        @Nested
        class NewInvalidDriverThrowsException {

            @DisplayName("Then throw a BadParamException exception")
            @Test
            void should_throw_exception_when_trying_to_add_an_empty_or_invalid_driver() {
                assertThrows(BadParamException.class, () -> driverService.createDriver(null),
                        "should throw a NullPointerException exception");
            }
        }

        @DisplayName("When user passes an existing driver to be saved")
        @Nested
        class NewExistingDriverThrowException {

            @DisplayName("Then throw existing driver exception")
            @Test
            void should_throw_existing_drive_exception() {
                //Given
                Driver exitingDriver = builder().id(1L).build();

                //When
                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(exitingDriver));
                assertThrows(ExistingDriverException.class, () -> driverService.createDriver(exitingDriver));
                verify(driverRepository, times(1)).findById(anyLong());

            }
        }
    }

    @Nested
    @DisplayName("Given a driver to be updated")
    class UpdateDriver {
        @Nested
        @DisplayName("When the user passes a new driver for updating the existing one")
        class DriverToBeUpdated {

            @DisplayName("Then the existing driver should be updated")
            @Test
            void should_update_a_driver() {
                Driver driver = builder()
                        .id(1L)
                        .firstname("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();
                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
                when(driverRepository.save(any(Driver.class))).thenReturn(driver);

                Driver updatedDriver = driverService.updateDriver(driver);
                assertAll("Check condition",
                        () -> assertEquals(driver, updatedDriver, "A driver has been updated"));

                verify(driverRepository, times(1)).findById(anyLong());
                verify(driverRepository, times(1)).save(any(Driver.class));
            }
        }
    }
}