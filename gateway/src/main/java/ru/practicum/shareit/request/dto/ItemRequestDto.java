package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class ItemRequestDto {
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
}