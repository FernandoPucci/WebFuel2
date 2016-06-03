package util;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by fernando-pucci on 03/06/16.
 */
public class JsonUtils {


    public static JSONObject converterBundleToJSON(Bundle bundle) throws JSONException {

        JSONObject json = null;

        if (bundle == null) {

            throw new JSONException("ERRO: converterBundleToJSON, Bundle  nulo");

        } else {

            json = new JSONObject();

        }

        Set<String> keys = bundle.keySet();

        for (String key : keys) {

            json.put(key, bundle.get(key)); //see edit below
            //json.put(key, JSONObject.wrap((bundle.get(key));

        }

        return json;

    }

    public static Bundle converterJsonToBundle(JSONObject jsonObject) throws JSONException {

        Bundle bundle = null;

        if (jsonObject == null) {

            throw new JSONException("ERRO: converterJsonToBundle, JSONObject  nulo");

        } else {

            bundle = new Bundle();

        }


        Iterator iter = jsonObject.keys();

        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = jsonObject.getString(key);
            bundle.putString(key, value);
        }

        return bundle;
    }


}
