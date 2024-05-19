package medium.clientregistration.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientDTO(

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("document_type")
        String documentType,

        String document
) { }
