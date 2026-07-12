package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private ItemRequestService requestService;
    @Autowired private ObjectMapper mapper;

    @Test
    void create() throws Exception {
        when(requestService.create(anyLong(), any())).thenReturn(new ItemRequestDto());
        mvc.perform(post("/requests").header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new ItemRequestDto())))
                .andExpect(status().isOk());
    }

    @Test
    void getUserRequests() throws Exception {
        when(requestService.getUserRequests(anyLong())).thenReturn(List.of());
        mvc.perform(get("/requests").header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        when(requestService.getAllRequests(anyLong(), anyInt(), anyInt())).thenReturn(List.of());
        mvc.perform(get("/requests/all").header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        when(requestService.getById(anyLong(), anyLong())).thenReturn(new ItemRequestDto());
        mvc.perform(get("/requests/1").header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }
}