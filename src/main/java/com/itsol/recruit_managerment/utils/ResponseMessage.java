package com.itsol.recruit_managerment.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
    }
}
