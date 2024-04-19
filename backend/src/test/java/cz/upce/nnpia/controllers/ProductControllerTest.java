package cz.upce.nnpia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.dtos.response.ProductResponse;
import cz.upce.nnpia.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProductController.class})
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;


    private ProductRequest productRequest;
    private ProductResponse productResponse;
    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest("product", 10.0, 10);
        productResponse = new ProductResponse(1L, "product", 10.0, 10);
    }

    @Test
    void createProduct() throws Exception{
        given(productService.create(productRequest)).willReturn(productResponse);

        ResultActions response = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(productResponse)));
    }
}