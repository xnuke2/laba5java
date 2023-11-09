package app.model;

import app.entities.User;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Model {
    private static Model instance = new Model();

    private ArrayList<User> model;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        model = new ArrayList<>();
    }

    public void add(User user) {
        model.add(user);
    }

    public ArrayList<String> GetArrayOfNames() {
        ArrayList<String> tmp = new ArrayList<>();
        Object[] arr = model.toArray();
        for (int i=0;i<model.size();i++){
            tmp.add(((User)arr[i]).getName());
        }
        return tmp;
    }
}