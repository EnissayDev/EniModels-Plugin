package fr.enissay.enimodels.plugin.utils;

import com.google.gson.Gson;
import fr.enissay.enimodels.plugin.management.ParsingManager;
import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockLocation;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockSize;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockType;
import fr.enissay.enimodels.plugin.management.component.exceptions.ParsingErrorException;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseTreeItem {
    
    public static void parseTreeView(final JSONObject jsonObject, final Component parent) throws JSONException {

        final Gson gson = new Gson();

        final JSONArray jsonArray = jsonObject.getJSONArray("children");
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject value = jsonArray.getJSONObject(i).getJSONObject("value");
            Component component = null;
            final String childName = value.getString("childName");
            final Class<? extends Component> type = ParsingManager.getTypeOfComponent(childName);

            /*
             "x": 40.0,
              "y": 0.0,
              "z": 0.0,
              "rotateX": 0.0,
              "rotateY": 0.0,
              "rotateZ": 0.0,
              "size": "LARGE",
              "type": "COAL_BLOCK"
             */
            component = gson.fromJson(value.toString(), type);
            if (value.has("blockLocation")) {
                final JSONObject blockLocation = value.getJSONObject("blockLocation");
                final double x = blockLocation.getDouble("x");
                final double y = blockLocation.getDouble("y");
                final double z = blockLocation.getDouble("z");
                final double rotateX = blockLocation.getDouble("rotateX");
                final double rotateY = blockLocation.getDouble("rotateY");
                final double rotateZ = blockLocation.getDouble("rotateZ");
                final BlockSize size = BlockSize.valueOf(blockLocation.getString("size")) == null ? BlockSize.LARGE : BlockSize.valueOf(blockLocation.getString("size"));
                final BlockType typeBlock = BlockType.valueOf(blockLocation.getString("type")) == null ? BlockType.COAL_BLOCK : BlockType.valueOf(blockLocation.getString("type"));

                component.setBlockLocation(new BlockLocation(x, y, z, rotateX, rotateY, rotateZ, size, typeBlock));
            }//else component.setBlockLocation(new BlockLocation(0,0,0,0,0,0,BlockSize.LARGE,BlockType.STONE));

            parent.getChildren().add(component);

            JSONArray entry = (JSONArray) jsonArray.getJSONObject(i).get("children");

            //CHILDREN OF COMPONENT
            for (int j = 0; j < entry.length(); j++) {
                try {
                    JSONObject jsonObjectMain = (JSONObject) entry.get(j);
                    parseSimpleComponent(jsonObjectMain, component);
                }catch (JSONException ex){
                }
            }
        }
    }

    public static void parseSimpleComponent(final JSONObject jsonObject, Component parent) throws JSONException {

        final Gson gson = new Gson();
        final JSONObject search = jsonObject.getJSONObject("value");

        Component component = null;
        final String childName = search.getString("childName");
        final Class<? extends Component> type = ParsingManager.getTypeOfComponent(childName);

        component = gson.fromJson(search.toString(), type);
        if (search.has("blockLocation")) {
            final JSONObject blockLocation = search.getJSONObject("blockLocation");
            final double x = blockLocation.getDouble("x");
            final double y = blockLocation.getDouble("y");
            final double z = blockLocation.getDouble("z");
            final double rotateX = blockLocation.getDouble("rotateX");
            final double rotateY = blockLocation.getDouble("rotateY");
            final double rotateZ = blockLocation.getDouble("rotateZ");
            final BlockSize size = BlockSize.valueOf(blockLocation.getString("size")) == null ? BlockSize.LARGE : BlockSize.valueOf(blockLocation.getString("size"));
            final BlockType typeBlock = BlockType.valueOf(blockLocation.getString("type")) == null ? BlockType.COAL_BLOCK : BlockType.valueOf(blockLocation.getString("type"));

            component.setBlockLocation(new BlockLocation(x, y, z, rotateX, rotateY, rotateZ, size, typeBlock));
        }//else component.setBlockLocation(new BlockLocation(0,0,0,0,0,0,BlockSize.LARGE,BlockType.COAL_BLOCK));
        parent.getChildren().add(component);
        JSONArray entry = (JSONArray) jsonObject.get("children");

        //CHILDREN OF COMPONENT
        for (int j = 0; j < entry.length(); j++) {
            JSONObject jsonObjectMain = (JSONObject) entry.get(j);
            parseSimpleComponent(jsonObjectMain, component);
        }
    }
}
