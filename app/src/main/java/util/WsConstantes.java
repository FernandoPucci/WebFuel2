package util;

/**
 * Created by fernando-pucci on 03/06/16.
 */
public class WsConstantes {

    private static final String HOST = "192.168.200.103";
    private static final String HTTPS_PORT = "8443";
    private static final String HTTP_PORT = "8089";

    public static final String HTTPS_SERVICE_URL = "https://"+HOST+":"+HTTPS_PORT+"/WebFuelServices/api/";
    public static final String HTTP_SERVICE_URL = "http://"+HOST+":"+HTTP_PORT+"/WebFuelServices/api/";

    //Services

    //Login
    private static final String LOGIN_SERVICES = "login/"
            ;
    public static final String LOGIN_SERVICES_SET_DADOS_USUARIO = LOGIN_SERVICES + "setDadosUsuario";


    //Utils
    private static final String UTIL_SERVICES = "utils/";

    public static final String UTIL_SERVICES_LISTAR_ESTADOS = UTIL_SERVICES + "listAllEstados";
    public static final String UTIL_SERVICES_LISTAR_CIDADES = UTIL_SERVICES + "listAllCidades";
    public static final String UTIL_SERVICES_LISTAR_CIDADES_POR_ESTADO = UTIL_SERVICES + "listAllCidadesByIdEstado";

    //param
    public static final String PARAM_LISTAR_CIDADES_POR_ESTADO_ID_ESTADO =  "idEstado";



}
