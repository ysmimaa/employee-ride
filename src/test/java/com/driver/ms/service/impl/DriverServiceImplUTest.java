package com.driver.ms.service.impl;

import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.common.factory.DriverFactory;
import com.driver.ms.entity.Address;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.exception.BadParamException;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.GenericFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
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

    @Mock
    private DriverFactory driverFactory;

    @Mock
    private GenericFilter driverGenericFilter;

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
                    List<Driver> drivers = Arrays.asList(
                            builder()
                                    .id(1L)
                                    .journey(
                                            Journey.builder()
                                                    .id(1L)
                                                    .nbrOfPlaces(22)
                                                    .build()
                                    )
                                    .build(),
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
                            () -> assertEquals(3, groupedDriversByJourney.size()),
                            () -> assertEquals(drivers.get(0), groupedDriversByJourney.get(drivers.get(0).getJourney()).get(0)));


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
                when(driverRepository.findByFirstname(anyString())).thenReturn(driversList);
                List<Driver> listOfDriver = driverService.findByFirstName("firstname1");
                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(4, listOfDriver.size(), "An empty list of user"));
                assertThat(listOfDriver).contains(driversList.get(0));

                verify(driverRepository, times(1)).findByFirstname(anyString());
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
                when(driverRepository.findByLastname(anyString())).thenReturn(Optional.of(driver));

                Driver driverByLastName = driverService.findDriverByLastName("last");
                assertEquals("lastname1", driverByLastName.getLastname(), "The driver lastname must match the same as lastname1");
                verify(driverRepository, times(1)).findByLastname(anyString());
            }

            @Test
            @DisplayName(value = "Throw invalid lastName param exception")
            void should_throw_an_exception_when_invalid_lastName() {
                assertThrows(RuntimeException.class, () -> driverService.findDriverByLastName(null));
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
                DriverDto driverDtoToBePersisted = DriverDto.builder()
                        .firstName("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();
                Driver driverToPersist = Driver.builder()
                        .firstname("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();

                when(driverRepository.save(any(Driver.class))).thenReturn(driverToPersist);
                when(driverFactory.convertDriverDtoToDriverEntity(any(DriverDto.class))).thenReturn(driverToPersist);
                when(driverFactory.convertDriverEntityToDriverDto(any(Driver.class))).thenReturn(driverDtoToBePersisted);

                DriverDto createdDriver = driverService.createDriver(driverDtoToBePersisted);

                assertAll("Check conditions",
                        () -> assertEquals(driverDtoToBePersisted, createdDriver, "A new driver has been created"));
                verify(driverRepository, times(1)).save(any(Driver.class));

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
    }

    @Nested
    @DisplayName("Given a driver to be updated")
    class UpdateDriver {
        @Nested
        @DisplayName("When the user passes a new driver for updating the existing one")
        class DriverToBeUpdated {
            @DisplayName("Then the existing driver should be updated")
            @Test
            void should_update_a_driver() throws JsonProcessingException {
                Driver driver = builder()
                        .id(1L)
                        .firstname("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();
                DriverDto driverDtoToBeUpdated = DriverDto.builder()
                        .id(1L)
                        .firstName("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();
                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
                when(driverRepository.save(any(Driver.class))).thenReturn(driver);
                when(driverFactory.convertDriverDtoToDriverEntity(driverDtoToBeUpdated)).thenReturn(Driver.builder().build());
                when(driverFactory.convertDriverEntityToDriverDto(driver)).thenReturn(driverDtoToBeUpdated);


                DriverDto updatedDriver = driverService.updateDriver(driverDtoToBeUpdated);
                assertAll("Check condition",
                        () -> assertEquals(driverDtoToBeUpdated, updatedDriver, "A driver has been updated"));

                verify(driverRepository, times(1)).findById(anyLong());
                verify(driverRepository, times(1)).save(any(Driver.class));
                verify(driverFactory, times(1)).convertDriverDtoToDriverEntity(any(DriverDto.class));
                verify(driverFactory, times(2)).convertDriverEntityToDriverDto(any(Driver.class));
            }
        }

        @Nested
        @DisplayName("When the user passes an invalid driver to be updated")
        class DriverToBeUpdatedWithInvalidInput {
            @DisplayName("Then invalid parameter exception is thrown")
            @Test
            void should_throw_an_invalid_parameter_exception_when_passing_invalid_driver() {
                Assertions.assertThrows(BadParamException.class, () -> driverService.updateDriver(null));
            }
        }
    }

    @Nested
    @DisplayName("Given a driver to be deleted")
    class DeleteDriver {
        @Nested
        @DisplayName("When the user passes a valid id of the driver to be deleted")
        class DriverToBeDeleted {
            @DisplayName("Then the driver with the id is deleted")
            @Test
            void should_delete_a_driver_by_his_id() throws JsonProcessingException {
                Driver foundDriver = builder()
                        .id(1L)
                        .firstname("driver1")
                        .address(
                                Address.builder()
                                        .phone("062323236")
                                        .build()
                        )
                        .build();
                DriverDto driverDtoToBeReturned = DriverDto.builder().id(1L).firstName("driver1").build();

                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(foundDriver));
                when(driverFactory.convertDriverEntityToDriverDto(foundDriver)).thenReturn(driverDtoToBeReturned);

                doNothing().when(driverRepository).delete(any(Driver.class));

                DriverDto deletedDriver = driverService.deleteDriverById(foundDriver.getId());

                assertAll("Check condition",
                        () -> assertEquals(driverDtoToBeReturned, deletedDriver, "A driver has been deleted"));
                verify(driverRepository, times(1)).findById(anyLong());
                verify(driverFactory, times(1)).convertDriverEntityToDriverDto(any(Driver.class));
            }
        }

        @Nested
        @DisplayName("When the user passes an invalid id")
        class DriverToBeDeletedWithInvalidInput {
            @DisplayName("Then invalid parameter exception is thrown")
            @Test
            void should_throw_an_invalid_parameter_exception_when_passing_invalid_driver() {
                Assertions.assertThrows(BadParamException.class, () -> driverService.deleteDriverById(null));
            }
        }

        @Nested
        @DisplayName("When user provide a valid firstName")
        class findDriversWithTheSameFirstName {
            @DisplayName("Then a list of drivers with the same firstName is displayed")
            @Test
            void should_throw_an_invalid_parameter_exception_when_passing_invalid_driver() {
                List<Driver> ListOfFoundDrivers = Arrays.asList(builder().id(1L).firstname("firstName").build());

                when(driverGenericFilter.apply(any(Driver.class))).thenReturn(true);
                when(driverRepository.findAll()).thenReturn(ListOfFoundDrivers);

                List<Driver> filteredDriversWithTheSameFirstName = driverService.filterDriversWithTheSameFirstName();

                org.assertj.core.api.Assertions.assertThat(filteredDriversWithTheSameFirstName).contains(ListOfFoundDrivers.get(0));
                org.assertj.core.api.Assertions.assertThat(filteredDriversWithTheSameFirstName.get(0).getFirstname()).isEqualTo(ListOfFoundDrivers.get(0).getFirstname());
            }
        }
    }

    @DisplayName("Find driver by id")
    @Nested
    class FindDriverByIdSpec {
        @DisplayName("Given an id for the driver to be found")
        @Nested
        class FindDriverByIdValidId {
            @DisplayName("When invoking the find by id service with an invalid id")
            @Nested
            class FindDriverByIdCaseInvalidId {
                @Test
                @DisplayName(value = "Then an exception is thrown")
                void should_throw_an_exception_when_id_is_valid() throws JsonProcessingException {
                    Assertions.assertThrows(BadParamException.class, () -> driverService.findDriverById(null));
                }
            }

            @DisplayName("When invoking the find by id service with a valid id")
            @Nested
            class FindDriverByIdCaseValidId {
                @Test
                @DisplayName(value = "Then a driver with the id should be returned")
                void should_throw_an_exception_when_id_is_valid() throws JsonProcessingException {
                    Driver driverToBeFound = Driver.builder().id(1L).firstname("driver1").build();
                    DriverDto driverDtoToBeReturned = DriverDto.builder().id(1L).firstName("driver1").build();

                    when(driverRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(driverToBeFound));
                    when(driverFactory.convertDriverEntityToDriverDto(driverToBeFound)).thenReturn(driverDtoToBeReturned);

                    DriverDto foundDriver = driverService.findDriverById(1L);

                    org.assertj.core.api.Assertions.assertThat(driverDtoToBeReturned).isEqualToComparingFieldByField(foundDriver);

                    verify(driverRepository, times(1)).findById(ArgumentMatchers.anyLong());

                }

            }
        }

    }
}