package com.example.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class CountryNotFoundException extends RuntimeException{
    public CountryNotFoundException(String message) {
        super(message);
    }
}
