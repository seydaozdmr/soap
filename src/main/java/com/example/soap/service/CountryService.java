package com.example.soap.service;

import com.example.soap.exception.CountryNotFoundException;
import com.example.soap.repository.CountryRepository;
import io.spring.guides.gs_producing_web_service.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll(){
        return new ArrayList<>(countryRepository.getCountries().values());
    }

    public Country findCountry(String name){
        Country country=countryRepository.getCountries().get(name);
        if(country==null)
            throw  new CountryNotFoundException(name+" istenen ülke bulunamadı");
        return country;
    }

    public Country saveCountry(Country country){
        countryRepository.getCountries().put(country.getName(),country);
        return country;
    }

    public Country deleteCountry(String name){
        return countryRepository.getCountries().remove(name);
    }


}
