package com.cydeo.controller;

import com.cydeo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerUnitTest {
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;
    @Mock
    private View mockView;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setSingleView(mockView).build();
    }

    @Test
    void shouldReturnErrorViewWhenDescriptionIsBlank() throws Exception {

        when(categoryService.isCategoryDescriptionUnique(any())).thenReturn(false);

        mockMvc.perform(post("/categories/create")
        ).andExpect(status().isOk())
                .andExpect(view().name("/category/category-create"));
    }

    @Test
    void shouldReturnErrorViewWhenDescriptionIsInvalid() throws Exception {

        when(categoryService.isCategoryDescriptionUnique(any())).thenReturn(false);

        mockMvc.perform(post("/categories/create")
                        .param("description","a")
                ).andExpect(status().isOk())
                .andExpect(view().name("/category/category-create"));
    }

    @Test
    void shouldReturnErrorViewWhenWhenCategoryDescriptionExist() throws Exception {

        when(categoryService.isCategoryDescriptionUnique(any())).thenReturn(true);

        mockMvc.perform(post("/categories/create")
                        .param("description","aV")
                ).andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("newCategory"))
                .andExpect(view().name("/category/category-create"));
    }

    @Test
    void shouldReturnSuccessViewWhenThereIsNoError() throws Exception {

        when(categoryService.isCategoryDescriptionUnique(any())).thenReturn(false);

        mockMvc.perform(post("/categories/create")
                        .param("description","aV")
                ).andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors("newCategory"))
                .andExpect(view().name("redirect:/categories/list"));
    }
}