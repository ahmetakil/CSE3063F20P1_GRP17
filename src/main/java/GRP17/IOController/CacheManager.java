package GRP17.IOController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class CacheManager {

    private String cachedFilePath;
    private Gson gson;

    public CacheManager(String cacheFile) {

        this.cachedFilePath = cacheFile;
        this.gson = new Gson();
    }

    public Cache readCache(){


        try (Reader reader = new FileReader(cachedFilePath)) {

            Cache cache = gson.fromJson(reader,Cache.class );

            if(cache == null){
                return new Cache();
            }

            return cache;

        } catch (IOException e) {

            return new Cache();
        }

    }

    public void saveCache(Cache cache){

        try (Writer writer = new FileWriter(cachedFilePath)) {

            Gson gson = new GsonBuilder().create();

            gson.toJson(cache, writer);


        } catch (IOException e) {
            return;
        }
    }



}
