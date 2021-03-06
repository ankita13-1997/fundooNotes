package com.microusers.noteservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMQBody implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String subject;
    private String body;
}
