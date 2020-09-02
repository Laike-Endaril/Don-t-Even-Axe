package com.fantasticsource.dontevenaxe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = DontEvenAxe.MODID, name = DontEvenAxe.NAME, version = DontEvenAxe.VERSION)
public class DontEvenAxe
{
    public static final String MODID = "dontevenaxe";
    public static final String NAME = "Don't Even Axe";
    public static final String VERSION = "1.12.2.003";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(DontEvenAxe.class);

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            MinecraftForge.EVENT_BUS.register(TooltipRenderer.class);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttack(LivingHurtEvent event)
    {
        DamageSource dmgSource = event.getSource();
        Entity source = dmgSource.getTrueSource();
        Entity immediate = dmgSource.getImmediateSource();

        boolean isMelee = source != null && source == immediate;
        if (!isMelee) return;

        Entity attacker = event.getSource().getImmediateSource();
        if (!(attacker instanceof EntityLivingBase)) attacker = event.getSource().getTrueSource();
        if (attacker instanceof EntityLivingBase)
        {
            event.setAmount((float) (event.getAmount() * getMultiplier(((EntityLivingBase) attacker).getHeldItemMainhand())));
        }
    }

    public static double getMultiplier(ItemStack stack)
    {
        if (stack.getItem().getHarvestLevel(stack, "axe", null, null) >= 0) return 0.5;
        return 1;
    }
}
