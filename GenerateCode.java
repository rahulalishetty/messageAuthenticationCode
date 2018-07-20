import java.io.File;

public class GenerateCode {
    public static void main(String[] args)  {
        try {
            String filepath = "/home/zemoso/big_data/word_count.txt";
            File file = new File(filepath);
            String messageAuthCode = MacUtil.generateHash(file);
            System.out.println("obtained message authentication code:\n"+messageAuthCode);
        }catch (HashGenerationException e){
            e.printStackTrace();
        }

    }
}