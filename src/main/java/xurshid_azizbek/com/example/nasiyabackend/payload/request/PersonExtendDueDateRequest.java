package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PersonExtendDueDateRequest(
        @NotNull @Min(1) Integer days
) {}