package org.web.restapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "An Article must have a name")
    private String name;

    @NotNull(message = "An Article must have a kategory")
    private enum kategory;

}
