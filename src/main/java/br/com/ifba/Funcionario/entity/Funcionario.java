package br.com.ifba.Funcionario.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name="Funcionario")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Funcionario extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "salario", nullable = false)
    private double salario;
}
