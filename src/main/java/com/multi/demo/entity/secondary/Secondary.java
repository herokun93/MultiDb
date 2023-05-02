package com.multi.demo.entity.secondary;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@Table(name = "secondary", schema = "public")
public class Secondary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String comment;
}
