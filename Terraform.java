import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

class Terraform {
    public static void main(String[] args) {
        String problemFile = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(problemFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(problemFile + "terraform.txt"))) {

                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}