package com.kudaibergenov.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "DTO для создания/обновления задачи")
@Data
public class TaskRequest {

    @Schema(description = "Заголовок задачи", example = "Купить молоко")
    @NotBlank(message = "Заголовок не должен быть пустым")
    private String title;

    @Schema(description = "Описание задачи", example = "Не забыть скидочную карту")
    @Size(max = 500, message = "Описание должно быть не длиннее 500 символов")
    private String description;

    @Schema(description = "Завершена ли задача", example = "false")
    private boolean completed;
}
