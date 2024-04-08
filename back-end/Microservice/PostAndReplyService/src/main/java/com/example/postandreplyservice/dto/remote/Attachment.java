package com.example.postandreplyservice.dto.remote;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Attachment {
    String url;
    String type;
}
