package be.feeps.epicpets.animations;

import be.feeps.epicpets.Main;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feeps on 17/06/2017.
 */
public class EpicAniFiles {
    public static ArrayList<EpicAniFiles> listAnimations = new ArrayList<>();
    private List<String> lines;
    private Path path;

    public EpicAniFiles(Path path){
        this.path = path;
        try {
            this.readLines();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readLines() throws IOException{
        lines = Files.readAllLines(this.path);

    }

    public static void loadAnimations(){
        Path directoryPath = Paths.get(Main.getI().getDataFolder().getAbsolutePath()+"/Animations/");

        if (Files.isDirectory(directoryPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, "*.epicpetsani")) {
                stream.forEach((path) ->{
                    listAnimations.add(new EpicAniFiles(path));
                });
            } catch (IOException e) { e.printStackTrace();	}
        }
    }

    public Path getPath() {
        return this.path;
    }

    public List<String> getLines() {
        return this.lines;
    }
}
