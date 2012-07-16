/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import display.SceneManager;
import display.editor.WebServer;
import display.scene.Scene;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Jakob Wenzel
 */
public class JsonScene {

    public static void addMethods(JsonProvider provider) {
        final ObjectMapper mapper = new ObjectMapper();


        //Get a list of available scene types
        provider.addMethod(new JsonMethod() {

            @Override
            public String getName() {
                return "scene/properties";
            }

            @Override
            public void call(Map<String, String> params, DataOutputStream output, SceneManager manager) throws IOException {
                output.writeBytes(WebServer.httpHeader(200, "application/json"));
                boolean result = false;
                String paramStr = params.get("id");
                if (paramStr!=null) {
                    int id = new Integer(paramStr);
                    Scene scene = manager.getScene(id);
                    if (scene!=null) {
                        mapper.writeValue(output,mapper.generateJsonSchema(scene.getClass()));
                        mapper.writeValue(output, scene);
                    }
                }
                if (result==false) {
                    output.writeBytes("false");
                }
            }
        });
    }
}