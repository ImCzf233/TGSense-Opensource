package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;

public class FriendsConfig extends FileConfig {

    private final List friends = new ArrayList();

    public FriendsConfig(File file) {
        super(file);
    }

    protected void loadConfig() throws IOException {
        this.clearFriends();

        try {
            JsonElement ex = (new JsonParser()).parse(new BufferedReader(new FileReader(this.getFile())));

            if (ex instanceof JsonNull) {
                return;
            }

            Iterator bufferedReader1 = ex.getAsJsonArray().iterator();

            while (bufferedReader1.hasNext()) {
                JsonElement line1 = (JsonElement) bufferedReader1.next();
                JsonObject data1 = line1.getAsJsonObject();

                this.addFriend(data1.get("playerName").getAsString(), data1.get("alias").getAsString());
            }
        } catch (IllegalStateException | JsonSyntaxException jsonsyntaxexception) {
            ClientUtils.getLogger().info("[FileManager] Try to load old Friends config...");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getFile()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains("{") && !line.contains("}")) {
                    line = line.replace(" ", "").replace("\"", "").replace(",", "");
                    if (line.contains(":")) {
                        String[] data = line.split(":");

                        this.addFriend(data[0], data[1]);
                    } else {
                        this.addFriend(line);
                    }
                }
            }

            bufferedReader.close();
            ClientUtils.getLogger().info("[FileManager] Loaded old Friends config...");
            this.saveConfig();
            ClientUtils.getLogger().info("[FileManager] Saved Friends to new config...");
        }

    }

    protected void saveConfig() throws IOException {
        JsonArray jsonArray = new JsonArray();
        Iterator printWriter = this.getFriends().iterator();

        while (printWriter.hasNext()) {
            FriendsConfig.Friend friend = (FriendsConfig.Friend) printWriter.next();
            JsonObject friendObject = new JsonObject();

            friendObject.addProperty("playerName", friend.getPlayerName());
            friendObject.addProperty("alias", friend.getAlias());
            jsonArray.add(friendObject);
        }

        PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.getFile()));

        printWriter1.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter1.close();
    }

    public boolean addFriend(String playerName) {
        return this.addFriend(playerName, playerName);
    }

    public boolean addFriend(String playerName, String alias) {
        if (this.isFriend(playerName)) {
            return false;
        } else {
            this.friends.add(new FriendsConfig.Friend(playerName, alias));
            return true;
        }
    }

    public boolean removeFriend(String playerName) {
        if (!this.isFriend(playerName)) {
            return false;
        } else {
            this.friends.removeIf(test<invokedynamic>(playerName));
            return true;
        }
    }

    public boolean isFriend(String playerName) {
        Iterator iterator = this.friends.iterator();

        FriendsConfig.Friend friend;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            friend = (FriendsConfig.Friend) iterator.next();
        } while (!friend.getPlayerName().equals(playerName));

        return true;
    }

    public void clearFriends() {
        this.friends.clear();
    }

    public List getFriends() {
        return this.friends;
    }

    private static boolean lambda$removeFriend$0(String playerName, FriendsConfig.Friend friend) {
        return friend.getPlayerName().equals(playerName);
    }

    public class Friend {

        private final String playerName;
        private final String alias;

        Friend(String playerName, String alias) {
            this.playerName = playerName;
            this.alias = alias;
        }

        public String getPlayerName() {
            return this.playerName;
        }

        public String getAlias() {
            return this.alias;
        }
    }
}
