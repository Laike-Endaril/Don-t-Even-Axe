package com.fantasticsource.dontevenaxe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = DontEvenAxe.MODID, name = DontEvenAxe.NAME, version = DontEvenAxe.VERSION)
public class DontEvenAxe
{
    public static final String MODID = "dontevenaxe";
    public static final String NAME = "Don't Even Axe";
    public static final String VERSION = "1.12.2.001";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(DontEvenAxe.class);
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event)
    {
        Entity attacker = event.getSource().getImmediateSource();
        if (!(attacker instanceof EntityLivingBase)) attacker = event.getSource().getTrueSource();
        if (attacker instanceof EntityLivingBase)
        {
            ItemStack itemStack = ((EntityLivingBase) attacker).getHeldItemMainhand();
            for (String s : itemStack.getItem().getToolClasses(itemStack))
            {
                if (s.equals("axe")) event.setAmount(event.getAmount() * 0.5f);
            }
        }
    }
}
