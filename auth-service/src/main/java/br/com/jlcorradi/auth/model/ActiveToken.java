package br.com.jlcorradi.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "active_token")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ActiveToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EcommerceUser user;

    private String token;
    private Date expirationDate;
    private boolean active;
}
