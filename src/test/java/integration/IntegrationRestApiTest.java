package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.sivak.Main;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@Transactional
@ActiveProfiles("test")
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class IntegrationRestApiTest {

    private static final String MANAGER_ID = "00000000-0000-0000-0000-000000000001";
    private static final String CAR_ID = "00000000-0000-0000-0000-000000002001";

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("car_dealership_test")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void registerDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES::getDriverClassName);
    }

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void cars_query_returns_seed_data() throws Exception {
        //Assert
        mockMvc.perform(get("/api/cars")
                        .param("brandName", "Toyota")
                        .param("modelName", "Camry")
                        .with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Camry")))
                .andExpect(content().string(containsString("Toyota")));
    }

    @Test
    void car_configurator_endpoint_creates_car_from_components() throws Exception {
        //Arrange
        String request = """
                {
                  "bodyId": "00000000-0000-0000-0000-000000000201",
                  "brandId": "00000000-0000-0000-0000-000000000301",
                  "colorId": "00000000-0000-0000-0000-000000000401",
                  "driveId": "00000000-0000-0000-0000-000000000501",
                  "engineId": "00000000-0000-0000-0000-000000000601",
                  "fuelId": "00000000-0000-0000-0000-000000000701",
                  "modelId": "00000000-0000-0000-0000-000000000101",
                  "transmissionId": "00000000-0000-0000-0000-000000000801",
                  "steeringId": "00000000-0000-0000-0000-000000000901",
                  "wheelId": "00000000-0000-0000-0000-000000001001",
                  "interiorId": "00000000-0000-0000-0000-000000001101"
                }
                """;

        //Act
        MvcResult createResult = mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .with(jwtFor("manager1", "MANAGER")))
                .andExpect(status().isOk())
                .andReturn();

        UUID carId = extractId(createResult.getResponse().getContentAsString());

        //Assert
        mockMvc.perform(get("/api/cars/{id}", carId).with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(carId.toString())));
    }

    @Test
    void custom_order_create_and_get_by_id() throws Exception {
        //Arrange
        String request = """
                {
                  "managerId": "%s",
                  "carId": "%s"
                }
                """.formatted(MANAGER_ID, CAR_ID);

        //Act
        MvcResult createResult = mockMvc.perform(post("/api/orders/custom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andReturn();

        UUID orderId = extractId(createResult.getResponse().getContentAsString());

        //Assert
        mockMvc.perform(get("/api/orders/custom/{id}", orderId).with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(orderId.toString())))
                .andExpect(content().string(containsString("CreatedState")));
    }

    @Test
    void custom_order_complete_without_transitions_returns_bad_request() throws Exception {
        //Arrange
        String request = """
                {
                  "managerId": "%s",
                  "carId": "%s"
                }
                """.formatted(MANAGER_ID, CAR_ID);

        //Act
        MvcResult createResult = mockMvc.perform(post("/api/orders/custom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andReturn();

        UUID orderId = extractId(createResult.getResponse().getContentAsString());

        //Assert
        mockMvc.perform(post("/api/orders/custom/{id}/complete", orderId)
                        .with(jwtFor("manager1", "MANAGER")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid custom order transition")));
    }

    @Test
    void test_drive_request_create_and_delete() throws Exception {
        //Arrange
        String request = """
                {
                  "carId": "%s",
                  "scheduledTime": "2026-04-03"
                }
                """.formatted(CAR_ID);

        //Act
        MvcResult createResult = mockMvc.perform(post("/api/test-drive-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andReturn();

        UUID requestId = extractId(createResult.getResponse().getContentAsString());

        //Assert
        mockMvc.perform(get("/api/test-drive-requests/{id}", requestId).with(jwtFor("user1", "USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(requestId.toString())))
                .andExpect(content().string(containsString("2026-04-03")));

        mockMvc.perform(delete("/api/test-drive-requests/{id}", requestId).with(jwtFor("user1", "USER")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/test-drive-requests/{id}", requestId).with(jwtFor("user1", "USER")))
                .andExpect(status().isForbidden());
    }

    private RequestPostProcessor jwtFor(String username, String... roles) {
        SimpleGrantedAuthority[] authorities = Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toArray(SimpleGrantedAuthority[]::new);

        return jwt()
                .jwt(jwt -> jwt.subject(username).claim("preferred_username", username))
                .authorities(authorities);
    }

    private UUID extractId(String rawJson) {
        Matcher matcher = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}").matcher(rawJson);
        if (matcher.find()) {
            return UUID.fromString(matcher.group());
        }
        throw new IllegalArgumentException("Cannot parse id from response: " + rawJson);
    }
}
