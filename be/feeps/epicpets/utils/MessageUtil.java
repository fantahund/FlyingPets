package be.feeps.epicpets.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feeps on 5/04/17.
 */
public class MessageUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> listTranslate(List<String> list) {

        List<String> converted = new ArrayList<String>();

        for (String string : list) {
            converted.add(ChatColor.translateAlternateColorCodes('&', string));
        }

        return converted;
    }
}