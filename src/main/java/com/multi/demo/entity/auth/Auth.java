package com.multi.demo.entity.auth;


import com.multi.demo.entity.social.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@Table(schema = "public")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String email;

//    @OneToMany(mappedBy = "auth")
//    private List<Post> posts;

}
