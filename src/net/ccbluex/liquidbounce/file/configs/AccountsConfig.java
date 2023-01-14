package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.Gson;
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
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;

public class AccountsConfig extends FileConfig {

    private final List accounts = new ArrayList();
    public final List altManagerMinecraftAccounts = new ArrayList();

    public AccountsConfig(File file) {
        super(file);
    }

    protected void loadConfig() throws IOException {
        this.clearAccounts();

        try {
            JsonElement ex = (new JsonParser()).parse(new BufferedReader(new FileReader(this.getFile())));

            if (ex instanceof JsonNull) {
                return;
            }

            Iterator accountList1 = ex.getAsJsonArray().iterator();

            while (accountList1.hasNext()) {
                JsonElement accountElement1 = (JsonElement) accountList1.next();
                JsonObject account1 = accountElement1.getAsJsonObject();
                JsonElement information1 = account1.get("name");
                JsonElement password = account1.get("password");
                JsonElement inGameName = account1.get("inGameName");

                if (inGameName != null && !inGameName.isJsonNull()) {
                    if (inGameName.isJsonNull() && password.isJsonNull()) {
                        this.addAccount(information1.getAsString());
                    } else {
                        this.addAccount(information1.getAsString(), account1.get("password").getAsString(), account1.get("inGameName").getAsString());
                    }
                } else {
                    this.addAccount(information1.getAsString(), password.getAsString());
                }
            }
        } catch (IllegalStateException | JsonSyntaxException jsonsyntaxexception) {
            ClientUtils.getLogger().info("[FileManager] Try to load old Accounts config...");
            List accountList = (List) (new Gson()).fromJson(new BufferedReader(new FileReader(this.getFile())), List.class);

            if (accountList == null) {
                return;
            }

            this.accounts.clear();
            Iterator accountElement = accountList.iterator();

            while (accountElement.hasNext()) {
                String account = (String) accountElement.next();
                String[] information = account.split(":");

                if (information.length >= 3) {
                    this.accounts.add(new MinecraftAccount(information[0], information[1], information[2]));
                } else if (information.length == 2) {
                    this.accounts.add(new MinecraftAccount(information[0], information[1]));
                } else {
                    this.accounts.add(new MinecraftAccount(information[0]));
                }
            }

            ClientUtils.getLogger().info("[FileManager] Loaded old Accounts config...");
            this.saveConfig();
            ClientUtils.getLogger().info("[FileManager] Saved Accounts to new config...");
        }

    }

    protected void saveConfig() throws IOException {
        JsonArray jsonArray = new JsonArray();
        Iterator printWriter = this.accounts.iterator();

        while (printWriter.hasNext()) {
            MinecraftAccount minecraftAccount = (MinecraftAccount) printWriter.next();
            JsonObject friendObject = new JsonObject();

            friendObject.addProperty("name", minecraftAccount.getName());
            friendObject.addProperty("password", minecraftAccount.getPassword());
            friendObject.addProperty("inGameName", minecraftAccount.getAccountName());
            jsonArray.add(friendObject);
        }

        PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.getFile()));

        printWriter1.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter1.close();
    }

    public void addAccount(String name) {
        if (!this.accountExists(name)) {
            this.accounts.add(new MinecraftAccount(name));
        }
    }

    public void addAccount(String name, String password) {
        if (!this.accountExists(name)) {
            this.accounts.add(new MinecraftAccount(name, password));
        }
    }

    public void addAccount(String name, String password, String inGameName) {
        if (!this.accountExists(name)) {
            this.accounts.add(new MinecraftAccount(name, password, inGameName));
        }
    }

    public void removeAccount(int selectedSlot) {
        this.accounts.remove(selectedSlot);
    }

    public void removeAccount(MinecraftAccount account) {
        this.accounts.remove(account);
    }

    public boolean accountExists(String name) {
        Iterator iterator = this.accounts.iterator();

        MinecraftAccount minecraftAccount;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            minecraftAccount = (MinecraftAccount) iterator.next();
        } while (!minecraftAccount.getName().equals(name));

        return true;
    }

    public void clearAccounts() {
        this.accounts.clear();
    }

    public List getAccounts() {
        return this.accounts;
    }
}
