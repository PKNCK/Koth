package io.github.aquerr.koth.command.arguments;

import io.github.aquerr.koth.Koth;
import io.github.aquerr.koth.PluginInfo;
import io.github.aquerr.koth.entity.Arena;
import io.github.aquerr.koth.entity.ArenaTeam;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class ArenaTeamArgument extends CommandElement
{
	private final Koth plugin;

	public ArenaTeamArgument(final Koth plugin, final @Nullable Text key)
	{
		super(key);
		this.plugin = plugin;
	}

	@Nullable
	@Override
	protected ArenaTeam parseValue(final CommandSource source, final CommandArgs args) throws ArgumentParseException
	{
		if(!args.hasNext())
			throw new ArgumentParseException(Text.of(PluginInfo.PLUGIN_ERROR, "Team name has not been specified!"), "", 0);

		final String arg = args.next();

		if(arg.equals(""))
			throw new ArgumentParseException(Text.of(PluginInfo.PLUGIN_ERROR, "Team name has not been specified!"), arg, 0);

		final Player player = (Player) source;
		Arena arena = this.plugin.getPlayersCreatingArena().get(player.getUniqueId());
		if (arena == null)
			arena = this.plugin.getPlayersEditingArena().get(player.getUniqueId());

		if(arena == null)
			throw new ArgumentParseException(Text.of(PluginInfo.PLUGIN_ERROR, "Player is not editing any arena!"), source.getName(), 0);

		for(final ArenaTeam arenaTeam : arena.getTeams())
		{
			if(arenaTeam.getName().equals(arg))
				return arenaTeam;
		}
		return null;
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<String> complete(final CommandSource src, final CommandArgs args, final CommandContext context)
	{
		if(!args.hasNext())
			return Collections.EMPTY_LIST;

		String charSequence = args.nextIfPresent().get();

		if(!(src instanceof Player))
			return Collections.EMPTY_LIST;

		final Player player = (Player) src;
		Arena arena = this.plugin.getPlayersCreatingArena().get(player.getUniqueId());
		if (arena == null)
			arena = this.plugin.getPlayersEditingArena().get(player.getUniqueId());

		if (arena == null)
			return Collections.EMPTY_LIST;

		final Set<ArenaTeam> teams = arena.getTeams();
		final List<String> teamsNames = new LinkedList<>();

		for(final ArenaTeam arenaTeam : teams)
		{
			teamsNames.add(arenaTeam.getName());
		}
		return teamsNames.stream().filter(x->x.contains(charSequence)).collect(Collectors.toList());
	}
}