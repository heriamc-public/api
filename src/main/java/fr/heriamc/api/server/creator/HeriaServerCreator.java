package fr.heriamc.api.server.creator;

import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.api.server.HeriaServerManager;
import fr.heriamc.api.server.HeriaServerStatus;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.utils.HeriaFileUtils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;

public class HeriaServerCreator {

    private final HeriaServerManager serverManager;

    private static final Random RANDOM = new Random();

    private final Path mainFile = Paths.get("/home");

    private final Path proxyFile = Paths.get(this.mainFile + "/PROXY");

    private final Path templatesFile = Paths.get(this.mainFile + "/TEMPLATES");
    private final Path basicTemplatesFile = Paths.get(this.templatesFile + "/ALL");

    private final Path serversFile = Paths.get(this.mainFile + "/SERVERS");

    public HeriaServerCreator(HeriaServerManager serverManager) {
        this.serverManager = serverManager;
    }

    public String createServer(HeriaServerType serverType, @Nullable UUID hostId) {
        System.out.println("Creating server with type: " + serverType + ", hostId: " + hostId);
        int port = this.nextFreePort();
        System.out.println("Next free port found: " + port);

        String name = serverType.getName() + "-" + this.foundId(serverType);
        System.out.println("Generated server name: " + name);

        this.serverManager.put(new HeriaServer(name, serverType, HeriaServerStatus.STARTING, hostId, port, System.currentTimeMillis(), Collections.emptyList()));
        System.out.println("Server added to serverManager: " + name);

        ExecutorService service = Executors.newFixedThreadPool(5);
        System.out.println("ExecutorService created.");

        Future<?> scheduledFuture = service.submit(() -> {
            System.out.println("Starting server task submitted for: " + name);
            try {
                this.startServer(serverType, port, name, hostId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("Scheduled future for starting server: " + scheduledFuture);
        return name;
    }

    private void startServer(HeriaServerType serverType, int port, String name, UUID uuid) throws IOException {
        System.out.println("Starting server " + name + " with type: " + serverType + ", port: " + port + ", UUID: " + uuid);

        String folder = this.serversFile + "/" + name;
        System.out.println("Server folder path: " + folder);

        String template = this.templatesFile + "/" + serverType.getName().toUpperCase(Locale.ROOT);
        System.out.println("Template folder path: " + template);

        Files.createDirectories(Paths.get(folder));
        System.out.println("Directories created for server at: " + folder);

        HeriaFileUtils.copyDirSafely(folder, this.basicTemplatesFile.toString());
        System.out.println("Basic templates copied to server folder.");

        HeriaFileUtils.copyDirSafely(folder, template);
        System.out.println("Server-specific templates copied to server folder.");

        HeriaFileUtils.copyFileRecursive(Paths.get(this.proxyFile + "/plugins/HeriaAPI-1.0.0.jar").toFile(),
                Paths.get(folder + "/plugins/HeriaAPI-1.0.0.jar").toFile());
        System.out.println("Proxy plugin copied to server plugins folder.");

        Path propertiesFile = Paths.get(folder + "/server.properties");
        System.out.println("Server properties file path: " + propertiesFile);

        List<String> lines = Files.readAllLines(propertiesFile);
        System.out.println("Properties file lines read.");

        lines.set(34, "server-port=" + port);
        Files.write(propertiesFile, lines, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Server port set in properties file.");

        File path = new File(folder + "/server.properties");
        Properties props = new Properties();
        props.load(Files.newInputStream(path.toPath()));
        System.out.println("Properties loaded from server.properties");

        props.put("motd", name);
        props.store(Files.newOutputStream(path.toPath()), "Minecraft server properties");
        System.out.println("MOTD set and properties stored.");

        File startFile = new File(folder, "start.sh");
        System.out.println("Start script file path: " + startFile);

        if (startFile.exists()) {
            System.out.println("Start file exists, reading content.");
            System.out.println(1);
            String s = FileUtils.readFileToString(startFile);
            System.out.println(2);
            s = s.replaceAll("%servername%", name);
            System.out.println(3);
            FileUtils.writeStringToFile(startFile, s);
            System.out.println(4);
            System.out.println("Start file modified with server name.");
        } else {
            System.out.println("Start file does not exist, creating a new one.");
            String scriptContent = "nice -n 4 screen -dmS " + name + " java -Djava.awt.headless=true -jar -Xms1G -Xmx4G server.jar";
            try (Writer output = new BufferedWriter(new FileWriter(folder + "/start.sh"))) {
                output.write(scriptContent);
                System.out.println("Start script written with content: " + scriptContent);
            }
        }

        if (startFile.exists()) {
            startFile.setExecutable(true);
            System.out.println("Start script set to executable.");
        }

        File targetDirectory = new File(folder);
        new ProcessBuilder("./start.sh").directory(targetDirectory).start();
        System.out.println("ProcessBuilder started with start.sh in folder: " + folder);
    }

    public void deleteServer(String serverName) {
        Runtime runtime = Runtime.getRuntime();

        String folder = this.serversFile + "/" + serverName;

        try {
            Process process = runtime.exec("screen -X -S " + serverName + " kill");
            process.waitFor();
            File file = new File(folder);
            HeriaFileUtils.deleteDir(file);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private int foundId(HeriaServerType serverType) {
        int id = serverType.equals(HeriaServerType.HUB) ? 1 : RANDOM.nextInt(500);
        while (this.serverManager.get(serverType.getName() + "-" + id) != null)
            id++;
        return id;
    }

    private int nextFreePort() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(0);
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serverSocket.getLocalPort();
    }
}
