import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
//import javafx.util.Pair;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;

//class WrapperCreator<T> implements InstanceCreator {
//    public Wrapper createInstance(Type type) {
//        Wrapper wrapper = new LFUWrapper(new Object());
//        return wrapper;
//    }
//}

public class FileSystemStorage<K, T> implements ICacheStorage<K, T> {
    //private Map<K, Wrapper<T>> storage;

    private String storage;
    private int maxSize;

    FileSystemStorage(int maxSize) {
        this.storage = "F:\\cache.txt";
        this.maxSize = maxSize;
        clear();
    }

    public void add(K key, Wrapper<T> value) {
        if (!isFull()) {
            try {
                FileWriter writer = new FileWriter(storage, true);
                try {
                    Pair<K, Wrapper<T>> newPair = new Pair<K, Wrapper<T>>(key, value);
                    //String jsonStr = new Gson().toJson(newPair);
                    JSONSerializer ser = new JSONSerializer();
                    String jsonStr = ser.deepSerialize(newPair);
                    writer.write(jsonStr);
                    writer.append('\n');
                    writer.flush();
                } finally {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    public void add(K key, Wrapper<T> value) {
//        if (!isFull()) {
//            try {
//                FileOutputStream fos = new FileOutputStream(storage);
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                Pair<K, Wrapper<T>> newPair = new Pair<K, Wrapper<T>>(key, value);
//                oos.writeObject(newPair);
//                oos.flush();
//                oos.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

    public Wrapper<T> get(K key) {
        //Type pairType = new TypeToken<Pair<K, LRUWrapper<T>>>() {
        //}.getType();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(storage));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    //Pair<K, LRUWrapper<T>> newPair = new Gson().fromJson(line, pairType);

                    JSONDeserializer<Pair<K, Wrapper<T>>> der = new JSONDeserializer<Pair<K, Wrapper<T>>>();
                    Pair<K, Wrapper<T>> newPair = der.deserialize(line);

                    if (newPair.getKey().equals(key)) {
                        //System.out.println("Key::" + newPair.getKey());
                        return newPair.getValue();
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

//    public Wrapper<T> get(K key) {
//        try {
//            FileInputStream fis = new FileInputStream(storage);
//            ObjectInputStream oin = new ObjectInputStream(fis);
//
//            Pair<K, Wrapper<T>> currentPair;// = new Pair<K, Wrapper<T>>(key, value);
//
//            while (fis.available() != 0) {
//                try {
//                    currentPair = (Pair<K, Wrapper<T>>) oin.readObject();
//                    if (currentPair.getKey().equals(key))
//                    {
//                        return currentPair.getValue();
//                    }
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //fis.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    public void remove(K key) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(storage));
            StringBuffer stringBuffer = new StringBuffer();
            try {
                String line;
                while ((line = reader.readLine()) != null) {

                    JSONDeserializer<Pair<K, Wrapper<T>>> der = new JSONDeserializer<Pair<K, Wrapper<T>>>();
                    Pair<K, Wrapper<T>> newPair = der.deserialize(line);

                    if (!newPair.getKey().equals(key)) {
                        stringBuffer.append(line).append("\n");
                    }
                }
                reader.close();

                String string = stringBuffer.toString();
                char[] buffer = new char[string.length()];
                string.getChars(0, string.length(), buffer, 0);


                FileWriter writer = new FileWriter(storage, false);
                writer.write(buffer);
                writer.close();
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isFull() {
        return size() >= maxSize;
    }

    public Map<K, Wrapper<T>> getAll() {
        return null;
    }

    public void clear() {
        File myFile = new File(this.storage);
        if (myFile.exists()) {
            myFile.delete();
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        int counter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(storage));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    counter++;
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return counter;
    }

}
