package fr.heriamc.bukkit.command;

import fr.heriamc.api.user.rank.HeriaRank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HeriaCommand {

    String name();

    HeriaRank power() default HeriaRank.ADMIN;

    String[] aliases() default {};

    String description() default "description par défaut";

    String usage() default "";

    boolean showInHelp() default true;

    boolean inGameOnly() default false;
}
