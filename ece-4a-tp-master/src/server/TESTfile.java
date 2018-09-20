package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import shared.Pokemon;

import java.util.ArrayList;
import shared.Pokemon;

public class TESTfile {


    public static void main(String[] args) throws IOException {
        
        Database database = new Database("database");
        ArrayList<Pokemon> data1 = new ArrayList<Pokemon>();
        
         data1.add(new Pokemon("fefefe",45));
         data1.add(new Pokemon("afazfe",666));
         
         database.savePokemons(data1);
         
         data1.clear();
         
         data1 = database.loadPokemons();
         
         System.out.println(data1);
         
         
        
        
    }
    
}
