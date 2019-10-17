//package com.house.taskstracker.authentication.domain;
//
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import java.util.UUID;
//
//@Data
//@Entity
//@Table(name = "authority")
//public class Authority {
//
//    @Id
//    private UUID id;
//
//    @Column(name = "name", length = 50)
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private AuthorityName name;
//}