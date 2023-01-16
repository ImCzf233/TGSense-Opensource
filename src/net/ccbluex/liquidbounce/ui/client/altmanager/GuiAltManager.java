package net.ccbluex.liquidbounce.ui.client.altmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thealtening.AltService;
import com.thealtening.AltService.EnumAltService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import me.Skid.ui.Client.LBplusMainMenu;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiAdd;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiChangeName;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDirectLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiSessionLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiMCLeaks;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.vitox.mcleaks.MCLeaks;

public class GuiAltManager extends WrappedGuiScreen {

    public static final AltService altService = new AltService();
    private static final Map GENERATORS = new HashMap();
    public String status = "§7Idle...";
    private IGuiButton loginButton;
    private IGuiButton randomButton;
    private GuiAltManager.GuiList altsList;
    private IGuiTextField searchField;

    public static void loadGenerators() {
        try {
            JsonElement throwable = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/generators.json"));

            if (throwable.isJsonObject()) {
                JsonObject jsonObject = throwable.getAsJsonObject();

                jsonObject.entrySet().forEach(accept<invokedynamic>());
            }
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to load enabled generators.", throwable);
        }

    }

    public static String login(MinecraftAccount minecraftAccount) {
        if (minecraftAccount == null) {
            return "";
        } else {
            if (GuiAltManager.altService.getCurrentService() != EnumAltService.MOJANG) {
                try {
                    GuiAltManager.altService.switchService(EnumAltService.MOJANG);
                } catch (IllegalAccessException | NoSuchFieldException nosuchfieldexception) {
                    ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", nosuchfieldexception);
                }
            }

            if (minecraftAccount.isCracked()) {
                LoginUtils.loginCracked(minecraftAccount.getName());
                MCLeaks.remove();
                return "§cYour name is now §8" + minecraftAccount.getName() + "§c.";
            } else {
                LoginUtils.LoginResult result = LoginUtils.login(minecraftAccount.getName(), minecraftAccount.getPassword());

                if (result == LoginUtils.LoginResult.LOGGED) {
                    MCLeaks.remove();
                    String userName = GuiAltManager.mc.getSession().getUsername();

                    minecraftAccount.setAccountName(userName);
                    LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
                    return "§cYour name is now §f§l" + userName + "§c.";
                } else {
                    return result == LoginUtils.LoginResult.WRONG_PASSWORD ? "§cWrong password." : (result == LoginUtils.LoginResult.NO_CONTACT ? "§cCannot contact authentication server." : (result == LoginUtils.LoginResult.INVALID_ACCOUNT_DATA ? "§cInvalid username or password." : (result == LoginUtils.LoginResult.MIGRATED ? "§cAccount migrated." : "")));
                }
            }
        }
    }

    public void initGui() {
        int textFieldWidth = Math.max(this.representedScreen.getWidth() / 8, 70);

        this.searchField = GuiAltManager.classProvider.createGuiTextField(2, Fonts.font40, this.representedScreen.getWidth() - textFieldWidth - 10, 10, textFieldWidth, 20);
        this.searchField.setMaxStringLength(Integer.MAX_VALUE);
        this.altsList = new GuiAltManager.GuiList(this.representedScreen);
        this.altsList.represented.registerScrollButtons(7, 8);
        int index = -1;

        for (int j = 0; j < LiquidBounce.fileManager.accountsConfig.getAccounts().size(); ++j) {
            MinecraftAccount minecraftAccount = (MinecraftAccount) LiquidBounce.fileManager.accountsConfig.getAccounts().get(j);

            if (minecraftAccount != null && ((minecraftAccount.getPassword() == null || minecraftAccount.getPassword().isEmpty()) && minecraftAccount.getName() != null && minecraftAccount.getName().equals(GuiAltManager.mc.getSession().getUsername()) || minecraftAccount.getAccountName() != null && minecraftAccount.getAccountName().equals(GuiAltManager.mc.getSession().getUsername()))) {
                index = j;
                break;
            }
        }

        this.altsList.elementClicked(index, false, 0, 0);
        this.altsList.represented.scrollBy(index * this.altsList.represented.getSlotHeight());
        byte b0 = 22;

        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(1, this.representedScreen.getWidth() - 80, b0 + 24, 70, 20, "Add"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(2, this.representedScreen.getWidth() - 80, b0 + 48, 70, 20, "Remove"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(7, this.representedScreen.getWidth() - 80, b0 + 72, 70, 20, "Import"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(12, this.representedScreen.getWidth() - 80, b0 + 96, 70, 20, "Export"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(8, this.representedScreen.getWidth() - 80, b0 + 120, 70, 20, "Copy"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(0, this.representedScreen.getWidth() - 80, this.representedScreen.getHeight() - 65, 70, 20, "Back"));
        this.representedScreen.getButtonList().add(this.loginButton = GuiAltManager.classProvider.createGuiButton(3, 5, b0 + 24, 90, 20, "Login"));
        this.representedScreen.getButtonList().add(this.randomButton = GuiAltManager.classProvider.createGuiButton(4, 5, b0 + 48, 90, 20, "Random"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(6, 5, b0 + 72, 90, 20, "Direct Login"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(88, 5, b0 + 96, 90, 20, "Change Name"));
        if (((Boolean) GuiAltManager.GENERATORS.getOrDefault("mcleaks", Boolean.valueOf(true))).booleanValue()) {
            this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(5, 5, b0 + 120 + 5, 90, 20, "MCLeaks"));
        }

        if (((Boolean) GuiAltManager.GENERATORS.getOrDefault("thealtening", Boolean.valueOf(true))).booleanValue()) {
            this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(9, 5, b0 + 144 + 5, 90, 20, "TheAltening"));
        }

        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(10, 5, b0 + 168 + 5, 90, 20, "Session Login"));
        this.representedScreen.getButtonList().add(GuiAltManager.classProvider.createGuiButton(11, 5, b0 + 192 + 10, 90, 20, "Cape"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.representedScreen.drawBackground(0);
        this.altsList.represented.drawScreen(mouseX, mouseY, partialTicks);
        Fonts.font40.drawCenteredString("AltManager", (float) this.representedScreen.getWidth() / 2.0F, 6.0F, 16777215);
        Fonts.font35.drawCenteredString(this.searchField.getText().isEmpty() ? LiquidBounce.fileManager.accountsConfig.getAccounts().size() + " Alts" : this.altsList.accounts.size() + " Search Results", (float) this.representedScreen.getWidth() / 2.0F, 18.0F, 16777215);
        Fonts.font35.drawCenteredString(this.status, (float) this.representedScreen.getWidth() / 2.0F, 32.0F, 16777215);
        Fonts.font35.drawStringWithShadow("§7User: §a" + (MCLeaks.isAltActive() ? MCLeaks.getSession().getUsername() : GuiAltManager.mc.getSession().getUsername()), 6, 6, 16777215);
        Fonts.font35.drawStringWithShadow("§7Type: §a" + (GuiAltManager.altService.getCurrentService() == EnumAltService.THEALTENING ? "TheAltening" : (MCLeaks.isAltActive() ? "MCLeaks" : (UserUtils.INSTANCE.isValidTokenOffline(GuiAltManager.mc.getSession().getToken()) ? "Premium" : "Cracked"))), 6, 15, 16777215);
        this.searchField.drawTextBox();
        if (this.searchField.getText().isEmpty() && !this.searchField.isFocused()) {
            Fonts.font40.drawStringWithShadow("§7Search...", this.searchField.getXPosition() + 4, 17, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(IGuiButton button) throws IOException {
        if (button.getEnabled()) {
            switch (button.getId()) {
            case 0:
                GuiAltManager.mc2.displayGuiScreen(new LBplusMainMenu());
                break;

            case 1:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiAdd(this)));
                break;

            case 2:
                if (this.altsList.getSelectedSlot() != -1 && this.altsList.getSelectedSlot() < this.altsList.getSize()) {
                    LiquidBounce.fileManager.accountsConfig.removeAccount((MinecraftAccount) this.altsList.accounts.get(this.altsList.getSelectedSlot()));
                    LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
                    this.status = "§aThe account has been removed.";
                    this.altsList.updateAccounts(this.searchField.getText());
                } else {
                    this.status = "§cSelect an account.";
                }
                break;

            case 3:
                if (this.altsList.getSelectedSlot() != -1 && this.altsList.getSelectedSlot() < this.altsList.getSize()) {
                    this.loginButton.setEnabled(false);
                    this.randomButton.setEnabled(false);
                    Thread randomInteger1 = new Thread(run<invokedynamic>(this), "AltLogin");

                    randomInteger1.start();
                } else {
                    this.status = "§cSelect an account.";
                }
                break;

            case 4:
                if (this.altsList.accounts.size() <= 0) {
                    this.status = "§cThe list is empty.";
                    return;
                }

                int randomInteger = (new Random()).nextInt(this.altsList.accounts.size());

                if (randomInteger < this.altsList.getSize()) {
                    this.altsList.selectedSlot = randomInteger;
                }

                this.loginButton.setEnabled(false);
                this.randomButton.setEnabled(false);
                Thread thread = new Thread(run<invokedynamic>(this, randomInteger), "AltLogin");

                thread.start();
                break;

            case 5:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiMCLeaks(this)));
                break;

            case 6:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiDirectLogin(this)));
                break;

            case 7:
                File file = MiscUtils.openFileChooser();

                if (file == null) {
                    return;
                }

                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] selectedFile2 = line.split(":", 2);

                    if (!LiquidBounce.fileManager.accountsConfig.accountExists(selectedFile2[0])) {
                        if (selectedFile2.length > 1) {
                            LiquidBounce.fileManager.accountsConfig.addAccount(selectedFile2[0], selectedFile2[1]);
                        } else {
                            LiquidBounce.fileManager.accountsConfig.addAccount(selectedFile2[0]);
                        }
                    }
                }

                fileReader.close();
                bufferedReader.close();
                this.altsList.updateAccounts(this.searchField.getText());
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
                this.status = "§aThe accounts were imported successfully.";
                break;

            case 8:
                if (this.altsList.getSelectedSlot() != -1 && this.altsList.getSelectedSlot() < this.altsList.getSize()) {
                    MinecraftAccount selectedFile1 = (MinecraftAccount) this.altsList.accounts.get(this.altsList.getSelectedSlot());

                    if (selectedFile1 != null) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(selectedFile1.getName() + ":" + selectedFile1.getPassword()), (ClipboardOwner) null);
                        this.status = "§aCopied account into your clipboard.";
                    }
                } else {
                    this.status = "§cSelect an account.";
                }
                break;

            case 9:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiTheAltening(this)));
                break;

            case 10:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiSessionLogin(this)));
                break;

            case 11:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiDonatorCape(this)));
                break;

            case 12:
                if (LiquidBounce.fileManager.accountsConfig.getAccounts().size() == 0) {
                    this.status = "§cThe list is empty.";
                    return;
                }

                File selectedFile = MiscUtils.saveFileChooser();

                if (selectedFile == null || selectedFile.isDirectory()) {
                    return;
                }

                try {
                    if (!selectedFile.exists()) {
                        selectedFile.createNewFile();
                    }

                    FileWriter e = new FileWriter(selectedFile);
                    Iterator iterator = LiquidBounce.fileManager.accountsConfig.getAccounts().iterator();

                    while (iterator.hasNext()) {
                        MinecraftAccount account = (MinecraftAccount) iterator.next();

                        if (account.isCracked()) {
                            e.write(account.getName() + "\r\n");
                        } else {
                            e.write(account.getName() + ":" + account.getPassword() + "\r\n");
                        }
                    }

                    e.flush();
                    e.close();
                    JOptionPane.showMessageDialog((Component) null, "Exported successfully!", "AltManager", 1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    MiscUtils.showErrorPopup("Error", "Exception class: " + exception.getClass().getName() + "\nMessage: " + exception.getMessage());
                }
                break;

            case 88:
                GuiAltManager.mc.displayGuiScreen(GuiAltManager.classProvider.wrapGuiScreen(new GuiChangeName(this)));
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.searchField.isFocused()) {
            this.searchField.textboxKeyTyped(typedChar, keyCode);
            this.altsList.updateAccounts(this.searchField.getText());
        }

        int i;

        switch (keyCode) {
        case 1:
            GuiAltManager.mc2.displayGuiScreen(new LBplusMainMenu());
            return;

        case 28:
            this.altsList.elementClicked(this.altsList.getSelectedSlot(), true, 0, 0);
            break;

        case 200:
            i = this.altsList.getSelectedSlot() - 1;
            if (i < 0) {
                i = 0;
            }

            this.altsList.elementClicked(i, false, 0, 0);
            break;

        case 201:
            this.altsList.represented.scrollBy(-this.representedScreen.getHeight() + 100);
            return;

        case 208:
            i = this.altsList.getSelectedSlot() + 1;
            if (i >= this.altsList.getSize()) {
                i = this.altsList.getSize() - 1;
            }

            this.altsList.elementClicked(i, false, 0, 0);
            break;

        case 209:
            this.altsList.represented.scrollBy(this.representedScreen.getHeight() - 100);
        }

        this.representedScreen.superKeyTyped(typedChar, keyCode);
    }

    public void handleMouseInput() throws IOException {
        this.representedScreen.superHandleMouseInput();
        this.altsList.represented.handleMouseInput();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
        this.representedScreen.superMouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        this.searchField.updateCursorCounter();
    }

    private void lambda$actionPerformed$2(int randomInteger) {
        MinecraftAccount minecraftAccount = (MinecraftAccount) this.altsList.accounts.get(randomInteger);

        this.status = "§aLogging in...";
        this.status = login(minecraftAccount);
        this.loginButton.setEnabled(true);
        this.randomButton.setEnabled(true);
    }

    private void lambda$actionPerformed$1() {
        MinecraftAccount minecraftAccount = (MinecraftAccount) this.altsList.accounts.get(this.altsList.getSelectedSlot());

        this.status = "§aLogging in...";
        this.status = login(minecraftAccount);
        this.loginButton.setEnabled(true);
        this.randomButton.setEnabled(true);
    }

    private static void lambda$loadGenerators$0(Entry stringJsonElementEntry) {
        Boolean obool = (Boolean) GuiAltManager.GENERATORS.put(stringJsonElementEntry.getKey(), Boolean.valueOf(((JsonElement) stringJsonElementEntry.getValue()).getAsBoolean()));
    }

    private class GuiList extends WrappedGuiSlot {

        private List accounts;
        private int selectedSlot;

        GuiList(IGuiScreen prevGui) {
            super(MinecraftInstance.mc, prevGui.getWidth(), prevGui.getHeight(), 40, prevGui.getHeight() - 40, 30);
            this.updateAccounts((String) null);
        }

        private void updateAccounts(String search) {
            if (search != null && !search.isEmpty()) {
                search = search.toLowerCase();
                this.accounts = new ArrayList();
                Iterator iterator = LiquidBounce.fileManager.accountsConfig.getAccounts().iterator();

                while (iterator.hasNext()) {
                    MinecraftAccount account = (MinecraftAccount) iterator.next();

                    if (account.getName() != null && account.getName().toLowerCase().contains(search) || account.getAccountName() != null && account.getAccountName().toLowerCase().contains(search)) {
                        this.accounts.add(account);
                    }
                }

            } else {
                this.accounts = LiquidBounce.fileManager.accountsConfig.getAccounts();
            }
        }

        public boolean isSelected(int id) {
            return this.selectedSlot == id;
        }

        int getSelectedSlot() {
            if (this.selectedSlot > this.accounts.size()) {
                this.selectedSlot = -1;
            }

            return this.selectedSlot;
        }

        public void setSelectedSlot(int selectedSlot) {
            this.selectedSlot = selectedSlot;
        }

        public int getSize() {
            return this.accounts.size();
        }

        public void elementClicked(int i, boolean doubleClick, int j, int k) {
            this.selectedSlot = i;
            if (doubleClick) {
                if (GuiAltManager.this.altsList.getSelectedSlot() != -1 && GuiAltManager.this.altsList.getSelectedSlot() < GuiAltManager.this.altsList.getSize() && GuiAltManager.this.loginButton.getEnabled()) {
                    GuiAltManager.this.loginButton.setEnabled(false);
                    GuiAltManager.this.randomButton.setEnabled(false);
                    (new Thread(() -> {
                        MinecraftAccount minecraftAccount = (MinecraftAccount) this.accounts.get(GuiAltManager.this.altsList.getSelectedSlot());

                        GuiAltManager.this.status = "§aLogging in...";
                        GuiAltManager.this.status = "§c" + GuiAltManager.login(minecraftAccount);
                        GuiAltManager.this.loginButton.setEnabled(true);
                        GuiAltManager.this.randomButton.setEnabled(true);
                    }, "AltManagerLogin")).start();
                } else {
                    GuiAltManager.this.status = "§cSelect an account.";
                }
            }

        }

        public void drawSlot(int id, int x, int y, int i, int j, int k) {
            MinecraftAccount minecraftAccount = (MinecraftAccount) this.accounts.get(id);

            Fonts.font40.drawCenteredString(minecraftAccount.getAccountName() == null ? minecraftAccount.getName() : minecraftAccount.getAccountName(), (float) (GuiAltManager.this.representedScreen.getWidth() / 2), (float) (y + 2), Color.WHITE.getRGB(), true);
            Fonts.font40.drawCenteredString(minecraftAccount.isCracked() ? "Cracked" : (minecraftAccount.getAccountName() == null ? "Premium" : minecraftAccount.getName()), (float) (GuiAltManager.this.representedScreen.getWidth() / 2), (float) (y + 15), minecraftAccount.isCracked() ? Color.GRAY.getRGB() : (minecraftAccount.getAccountName() == null ? Color.GREEN.getRGB() : Color.LIGHT_GRAY.getRGB()), true);
        }

        public void drawBackground() {}
    }
}
