package xurshid_azizbek.com.example.nasiyabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import xurshid_azizbek.com.example.nasiyabackend.entity.abs.AbsEntity;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "user_settings")
public class UserSettings extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String groupLabel = "Mahalla";

    private Long goodLimit = 0L;

    private Long badLimit = 0L;
}
