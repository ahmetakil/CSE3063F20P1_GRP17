package GRP17.IOController;


import java.io.*;

public class CacheManager {

    private String cachedFilePath;

    public CacheManager(String cacheFile) {

        this.cachedFilePath = cacheFile;
    }

    public Cache readCache(){


        try{
            FileInputStream file = new FileInputStream(cachedFilePath);
            ObjectInputStream in = new ObjectInputStream(file);

            Cache cache = (Cache) in.readObject();

            in.close();
            file.close();

            return cache;

        }catch(Exception e){
            return new Cache();
        }

    }

    public void saveCache(Cache cache){

       try{
           FileOutputStream file = new FileOutputStream(cachedFilePath);
           ObjectOutputStream out = new ObjectOutputStream(file);

           out.writeObject(cache);

           out.close();
           file.close();
       }catch(Exception e){
           System.out.println("saveCache error: "+ e);
           return;
       }
    }



}
