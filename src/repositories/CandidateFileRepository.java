package repositories;

import domain.Candidate;
import validators.Validator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CandidateFileRepository extends FileRepository<Integer, Candidate> {

    public CandidateFileRepository(String filePath, Validator validator) {
        super(filePath, validator);
    }

    @Override
    public void readFromFile() {
        Path path = Paths.get(filePath);
        Stream<String> lines;
        try{
            lines = Files.lines(path);
            lines.forEach(line->{
                try {
                    if (line.split(";").length != 5) {
                        throw new Exception("Line is invalid !");
                    }
                    String[] fields = line.split(";");
                    super.save(new Candidate(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], fields[4]));
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("I/O error");
        }
    }

    @Override
    public void writeToFile() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            super.getAll().forEach(candidate -> {
                try{
                    bw.write(Long.toString(candidate.getId()) + ";" + candidate.getFirstName() + ";" + candidate.getLastName() + ";" + candidate.getPhoneNr() + ";" + candidate.getMail() + '\n');
                } catch (IOException e) {
                    System.out.println("I/O error");
                }
            });
        } catch (IOException e) {
            System.out.println("I/O error");
        }
    }
}
