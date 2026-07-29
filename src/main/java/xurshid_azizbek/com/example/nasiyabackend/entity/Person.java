package xurshid_azizbek.com.example.nasiyabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import xurshid_azizbek.com.example.nasiyabackend.entity.abs.AbsEntity;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person",uniqueConstraints =
@UniqueConstraint(columnNames = {"mahalla_id","number"}))
public class Person extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahalla_id", nullable = false)
    private Mahalla mahalla;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String firstName;

    private String lastName = "";
    private String nickname = "";
    private String phone = "";

    private LocalDate dueDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debt> debts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

}
