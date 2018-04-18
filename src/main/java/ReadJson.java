import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;



class ReadJson {

    //used only to test stuff out so that loading goes a little bit faster
    public static ArrayList<YelpData> readFromJson(int numBusinesses, String filename){
        ArrayList<YelpData> businesses = new ArrayList<YelpData>();

        //to prevent accidentally typing massive number into function
        if (numBusinesses>= 174566) {
            numBusinesses = 174566;
        }

        try{

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = "";

            for (int i = 0; i< numBusinesses; i++){
                line = br.readLine();
                org.json.JSONObject jobj = new org.json.JSONObject(line);

                YelpData yd = new YelpData(jobj.get("name").toString(), jobj.get("business_id").toString(),
                        jobj.get("city").toString(), jobj.getDouble("latitude"), jobj.getDouble("longitude"));

                //add categories for each business
                List <String> categories = new ArrayList<String>();
                JSONArray jarr = new JSONArray(jobj.getJSONArray("categories").toString());
                for (int j =0; j<jarr.length(); j++) {
                    categories.add(jarr.get(j).toString());
                }
                yd.categories.addAll(categories);
                System.out.println("loading item " + i);
                businesses.add(yd);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return businesses;
    }

}
