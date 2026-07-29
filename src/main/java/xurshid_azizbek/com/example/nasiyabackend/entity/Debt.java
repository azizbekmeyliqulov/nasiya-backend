package xurshid_azizbek.com.example.nasiyabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import xurshid_azizbek.com.example.nasiyabackend.entity.abs.AbsEntity;
import xurshid_azizbek.com.example.nasiyabackend.entity.converter.TakenByConverter;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.DebtKind;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.DebtStatus;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.TakenBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "debt")
public class Debt extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DebtKind kind; // DEBT, CARRY

    @Column(nullable = false)
    private Long amount;

    @Convert(converter = TakenByConverter.class)
    private TakenBy takenBy;

    private String takenByName = "";

    @Column(columnDefinition = "TEXT", nullable = false)
    private String products; // JSON string: ["non","suv"]

    private String note = "";

    @Column(nullable = false)
    private boolean settled = false;

    private LocalDateTime settledAt;
}