package com.infoprice.infopricechallenge.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoprice.infopricechallenge.dto.ViaCepDTO;
import com.infoprice.infopricechallenge.service.ViaCepService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultViaCepService implements ViaCepService {

    Logger logger = LoggerFactory.getLogger(DefaultViaCepService.class);

    @Override
    public Map<String, Object> findAddressByCep(final String cep) {
        Map<String, Object> result = new HashMap<>();
        final String url = "https://viacep.com.br/ws/"+cep+"/json/";
        OkHttpClient client = getClient();
        Request request = getRequest(url);
        Response response;
        String httpResponse;
        if (cep == null || cep.isEmpty()){
            logger.error("Cep cannot be null in findAddressByCep");
            result.put("operationStatus", "fail");
            return result;
        }
        try {
            response = client.newCall(request).execute();
            httpResponse = response.body().string();
            if (!response.isSuccessful()){
                logger.error("Error while trying tp retrieve Address by Cep");
                result.put("operationStatus", "fail");
                return result;
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ViaCepDTO viaCepDTO = mapper.readValue(httpResponse, ViaCepDTO.class);

            result.put("operationStatus", "success");
            result.put("data", viaCepDTO);
            return result;

        } catch (Exception e) {
            logger.error("Error occurred during findAddressByMap: "+ e.getMessage(), e);
            result.put("operationStatus", "fail");
            return result;
        }
    }


    private OkHttpClient getClient(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .build();
    }

    private Request getRequest(final String url){
        Request requestBuilder = new Request.Builder()
                .url(url)
                .get()
                .build();
        return requestBuilder;
    }

}
