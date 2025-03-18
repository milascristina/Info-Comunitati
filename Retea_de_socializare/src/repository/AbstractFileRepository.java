package repository;

import domain.Entity;
import domain.validators.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepository<ID,E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private String fileName;

    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }
    private void loadData() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line= reader.readLine())!=null){
                System.out.println(line);
                List<String> data= Arrays.asList(line.split(";"));
                E entity=extractEntity(data);
                super.save(entity);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public abstract E extractEntity(List<String> atributes);

    protected abstract String createEntityasString(E entity);

    @Override
    public E save(E entity) {
        E result=super.save(entity);
        if(result==null){
            writeToFile(entity);
        }
        return result;
    }
    private void writeToFile(E entity) {
        try(BufferedWriter writer= new BufferedWriter(new FileWriter(fileName,true))){
            writer.write(createEntityasString(entity));
            writer.newLine();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
