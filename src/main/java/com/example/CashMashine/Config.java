package com.example.CashMashine;

import com.example.CashMashine.utils.CashMachineStarter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

@Configuration
public class Config {

    @Bean
    @Scope(value = "prototype")
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultLocale(Locale.getDefault());
        return resourceBundleMessageSource;
    }

    @Bean
    public CashMachineStarter cashMachine() {
        return new CashMachineStarter();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

}
