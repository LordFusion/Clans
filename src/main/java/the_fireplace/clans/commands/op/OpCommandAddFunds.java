package the_fireplace.clans.commands.op;

import com.google.common.collect.Lists;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import the_fireplace.clans.Clans;
import the_fireplace.clans.clan.Clan;
import the_fireplace.clans.clan.ClanCache;
import the_fireplace.clans.commands.OpClanSubCommand;
import the_fireplace.clans.util.MinecraftColors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OpCommandAddFunds extends OpClanSubCommand {
	@Override
	public int getMinArgs() {
		return 2;
	}

	@Override
	public int getMaxArgs() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/opclan addfunds <clan> <amount>";
	}

	@Override
	public void run(@Nullable MinecraftServer server, EntityPlayerMP sender, String[] args) {
		String clan = args[0];
		Clan c = ClanCache.getClan(clan);
		if(c != null) {
			long amount;
			try {
				amount = Long.valueOf(args[1]);
				if(amount < 0)
					amount = 0;
			} catch(NumberFormatException e) {
				sender.sendMessage(new TextComponentString(MinecraftColors.RED + "Improperly formatted amount."));
				return;
			}
			if(Clans.getPaymentHandler().addAmount(amount, c.getClanId()))
				sender.sendMessage(new TextComponentTranslation(MinecraftColors.GREEN + "Successfully added %s %s to %s's balance.", amount, Clans.getPaymentHandler().getCurrencyName(amount), c.getClanName()));
			else
				sender.sendMessage(new TextComponentString(MinecraftColors.RED + "Internal error: Clan account not found."));
		} else
			sender.sendMessage(new TextComponentString(MinecraftColors.RED + "Clan not found."));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return args.length == 1 ? Lists.newArrayList(ClanCache.getClanNames().keySet()) : Collections.emptyList();
	}
}
