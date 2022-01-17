package com.example.soap.EndPoint;


import com.example.soap.service.CountryService;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.example.soap.repository.CountryRepository;

@Endpoint
public class CountryEndpoint {

    private static final String NAMESPACE_URI="http://spring.io/guides/gs-producing-web-service";

    private CountryService countryService;

    @Autowired
    public CountryEndpoint(CountryService countryService){
        this.countryService=countryService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request){
        GetCountryResponse response=new GetCountryResponse();
        response.setCountry(countryService.findCountry(request.getName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCountryRequest")
    @ResponsePayload
    public GetAllCountryResponse getAllCountry(@RequestPayload GetAllCountryRequest request){
        GetAllCountryResponse response=new GetAllCountryResponse();
        response.getCountry().addAll(countryService.getAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveCountryRequest")
    @ResponsePayload
    public SaveCountryResponse saveCountry(@RequestPayload SaveCountryRequest request){
        SaveCountryResponse response=new SaveCountryResponse();
        Country savedCountry= countryService.saveCountry(request.getCountry());
        response.setCountry(savedCountry);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCountryRequest")
    @ResponsePayload
    public DeleteCountryResponse deleteCountry(@RequestPayload DeleteCountryRequest request){
        DeleteCountryResponse response=new DeleteCountryResponse();
        Country deletedCountry= countryService.deleteCountry(request.getName());
        if(deletedCountry!=null)
            response.setStatus(deletedCountry.getName()+" silinmiştir");
        else
            response.setStatus("silme işlemi başarısız");
        return response;
    }
}
