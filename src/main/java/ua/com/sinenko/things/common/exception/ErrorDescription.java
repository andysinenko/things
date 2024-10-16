package ua.com.sinenko.things.common.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorDescription {
    private String title;
    private int status;
    private String detail;
    private String timestamp;
}
