package ws;

import android.util.Log;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import util.Util;
import util.WsConstantes;

/**
 * Created by fernando-pucci on 03/06/16.
 */
public class LoginWS {

    public String enviarDadosUsuario(JSONObject dados) throws Exception{

        ResponseEntity<String> loginResponse = null;

        if(dados==null){

            throw new Exception("enviarDadosUsuario - Objeto JSON Inválido.");

        }

        try {

            //final String url = WsConstantes.HTTPS_SERVICE_URL+WsConstantes.LOGIN_SERVICES_SET_DADOS_USUARIO ;
            final String url = WsConstantes.HTTP_SERVICE_URL + WsConstantes.LOGIN_SERVICES_SET_DADOS_USUARIO;

            RestTemplate restTemplate = new RestTemplate(true);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(dados.toString(), headers);

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // send request and parse result
            loginResponse = restTemplate
                    .exchange(url, HttpMethod.POST, entity, String.class);

            //   Log.w(Util.LOG_WARN, loginResponse.toString());

        } catch (Exception e) {

            Log.e(Util.LOG_ERR, e.getMessage(), e);
        }

        return loginResponse.toString();
    }

}
