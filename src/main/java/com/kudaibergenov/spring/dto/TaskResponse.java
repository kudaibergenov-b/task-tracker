package com.kudaibergenov.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "DTO ответа после получения/создания/обновления задачи")
@Data
@Builder
public class TaskResponse {

    private Long id;

    @Schema(description = "Заголовок задачи", example = "Купить молоко")
    private String title;

    @Schema(description = "Описание задачи", example = "Не забыть скидочную карту")
    private String description;

    @Schema(description = "Завершена ли задача", example = "false")
    private boolean completed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}